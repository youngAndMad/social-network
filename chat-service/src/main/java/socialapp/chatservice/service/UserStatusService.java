package socialapp.chatservice.service;

public interface UserStatusService {

    void setUserOnline(String email);

    void setUserOffline(String email);

    boolean isUserOnline(String email);

}
