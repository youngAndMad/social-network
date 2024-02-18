package socialapp.chatservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import socialapp.chatservice.model.dto.IsOnlineResponse;
import socialapp.chatservice.model.entity.AppUser;
import socialapp.chatservice.service.UserStatusService;

import static socialapp.chatservice.common.AppConstants.USER_ONLINE_KEY;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStatusServiceImpl implements UserStatusService {

    private final RedisTemplate<String,Object> redis;

    @Override
    public void setUserOnline(AppUser appUser) {
        log.info("User {} is online", appUser);
        redis.opsForSet()
                .add(USER_ONLINE_KEY, appUser.getEmail());
    }

    @Override
    public void setUserOffline(AppUser appUser) {
        log.info("User {} is offline", appUser);
        redis.opsForSet()
                .remove(USER_ONLINE_KEY, appUser.getEmail());
    }

    @Override
    public IsOnlineResponse isUserOnline(String email) {
        return new IsOnlineResponse(Boolean.TRUE.equals(redis.opsForSet()
                .isMember(USER_ONLINE_KEY, email)));
    }
}
