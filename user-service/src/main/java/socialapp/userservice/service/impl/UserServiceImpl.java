package socialapp.userservice.service.impl;

import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import socialapp.userservice.common.client.StorageClient;
import socialapp.userservice.common.exception.EmailRegisteredYetException;
import socialapp.userservice.common.exception.EntityNotFoundException;
import socialapp.userservice.common.mapper.UserMapper;
import socialapp.userservice.model.dto.*;
import socialapp.userservice.model.entity.User;
import socialapp.userservice.repository.UserRepository;
import socialapp.userservice.service.AddressService;
import socialapp.userservice.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

import static socialapp.userservice.common.AppConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final StorageClient storageClient;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public User register(RegistrationDto registrationDto) {
        var existsByEmail = userRepository.existsByEmail(registrationDto.email());

        if (existsByEmail) {
            throw new EmailRegisteredYetException(registrationDto.email());
        }
        var address = addressService.save(registrationDto.address());
        var user = userMapper.toModel(registrationDto, address);
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Set<SuggestionResponse> fetchSuggestions(String query) {
        var fullNameCriteria = new Criteria(FIRST_NAME)
                .contains(query)
                .or(new Criteria(LAST_NAME).contains(query));

        var suggestionResult = elasticsearchOperations.search(new CriteriaQuery(fullNameCriteria), User.class, IndexCoordinates.of(USER_INDEX));

        return suggestionResult.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .map(user -> new SuggestionResponse(user.getId(), user.getFirstName(), user.getLastName()))
                .collect(Collectors.toSet());
    }

    @Override
    public IsExistsResponse isExists(String email) {
        return new IsExistsResponse(userRepository.existsByEmail(email));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, id));
    }

    @Override
    public void uploadAvatar(MultipartFile file, Long id) {
        var user = findById(id);
        var uploadedAvatar = storageClient.upload(USER_PROFILE_IMAGE, user.getId(), file);

        if (uploadedAvatar.getStatusCode().is2xxSuccessful() && uploadedAvatar.getBody() != null) {
            user.setAvatar(uploadedAvatar.getBody().url());
            userRepository.save(user);
        } else {
            log.error("storage client error, status =  {}", uploadedAvatar.getStatusCode());
        }

    }

    @Override
    public void update(UserUpdateDto userUpdateDto, Long id) {
        var user = findById(id);

        userMapper.update(userUpdateDto, user);

        userRepository.save(user);
    }

    @Override
    public Set<User> find(UserSearchCriteria criteria) {
        var fullNameCriteria = new Criteria(FIRST_NAME).contains(criteria.nameLike())
                .or(new Criteria(LAST_NAME).contains(criteria.nameLike()));

        var finalCriteria = fullNameCriteria
                .and(new Criteria(AGE).greaterThan(criteria.ageFrom()))
                .and(new Criteria(AGE).lessThan(criteria.ageTo()))
                .and(new Criteria(CITY).contains(criteria.city()))
                .and(new Criteria(COUNTRY).contains(criteria.country()));

        var searchResult = elasticsearchOperations.search(new CriteriaQuery(fullNameCriteria), User.class, IndexCoordinates.of(USER_INDEX));

        return searchResult
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toSet());
    }


}

