package socialapp.chatservice.service;

import socialapp.chatservice.model.dto.IsOnlineResponse;
import socialapp.chatservice.model.entity.AppUser;

public interface UserStatusService {

    void setUserOnline(AppUser appUser);

    void setUserOffline(AppUser appUser);

    IsOnlineResponse isUserOnline(String email);

}
