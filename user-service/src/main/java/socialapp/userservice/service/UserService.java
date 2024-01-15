package socialapp.userservice.service;

import org.springframework.web.multipart.MultipartFile;
import socialapp.userservice.model.dto.IsExistsResponse;
import socialapp.userservice.model.dto.RegistrationDto;
import socialapp.userservice.model.dto.SuggestionResponse;
import socialapp.userservice.model.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    User register(RegistrationDto registrationDto);

    void delete(Long id);

    Set<SuggestionResponse> fetchSuggestions(String query);

    IsExistsResponse isExists(String email);

    void uploadAvatar(MultipartFile file, Long id);

}
