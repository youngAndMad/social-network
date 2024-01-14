package socialapp.userservice.service;

import socialapp.userservice.model.dto.RegistrationDto;
import socialapp.userservice.model.dto.SuggestionResponse;
import socialapp.userservice.model.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    User register(RegistrationDto registrationDto);

    void delete(Long id);

    Set<SuggestionResponse> fetchSuggestions(String query);
}
