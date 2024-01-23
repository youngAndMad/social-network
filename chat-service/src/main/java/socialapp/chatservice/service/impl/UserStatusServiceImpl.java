package socialapp.chatservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import socialapp.chatservice.service.UserStatusService;

import static socialapp.chatservice.common.AppConstants.USER_ONLINE_KEY;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStatusServiceImpl implements UserStatusService {

    private final RedisTemplate<String,Object> redis;

    @Override
    public void setUserOnline(String email) {
        log.info("User {} is online", email);
        redis.opsForSet()
                .add(USER_ONLINE_KEY, email);
    }

    @Override
    public void setUserOffline(String email) {
        log.info("User {} is offline", email);
        redis.opsForSet()
                .remove(USER_ONLINE_KEY, email);
    }

    @Override
    public boolean isUserOnline(String email) {
        return Boolean.TRUE.equals(redis.opsForSet()
                .isMember(USER_ONLINE_KEY, email));
    }
}
