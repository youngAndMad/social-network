package socialapp.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.QueryBuilders;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import socialapp.userservice.common.exception.EmailRegisteredYetException;
import socialapp.userservice.common.mapper.UserMapper;
import socialapp.userservice.model.dto.RegistrationDto;
import socialapp.userservice.model.dto.SuggestionResponse;
import socialapp.userservice.model.entity.User;
import socialapp.userservice.repository.UserRepository;
import socialapp.userservice.service.AddressService;
import socialapp.userservice.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final AddressService addressService;
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
        var fullNameCriteria = new Criteria("first_name")
                .contains(query)
                .or(new Criteria("last_name").contains(query));

        var suggestionResult = elasticsearchOperations.search(new CriteriaQuery(fullNameCriteria), User.class,IndexCoordinates.of("user"));

        return suggestionResult.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .map(user -> new SuggestionResponse(user.getId(), user.getFirstName(), user.getLastName()))
                .collect(Collectors.toSet());
    }


}

