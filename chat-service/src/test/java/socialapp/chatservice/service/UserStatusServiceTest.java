package socialapp.chatservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import socialapp.chatservice.model.dto.IsOnlineResponse;
import socialapp.chatservice.model.entity.AppUser;
import socialapp.chatservice.service.impl.UserStatusServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserStatusServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private SetOperations<String, Object> setOperations;

    @InjectMocks
    private UserStatusServiceImpl userStatusService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForSet()).thenReturn(setOperations);
    }

    @Test
    void setUserOnline() {
        AppUser appUser = new AppUser();
        appUser.setEmail("test@test.com");

        userStatusService.setUserOnline(appUser);

        verify(setOperations, times(1)).add(anyString(), eq(appUser.getEmail()));
    }

    @Test
    void setUserOffline() {
        AppUser appUser = new AppUser();
        appUser.setEmail("test@test.com");

        userStatusService.setUserOffline(appUser);

        verify(setOperations, times(1)).remove(anyString(), eq(appUser.getEmail()));
    }

    @Test
    void isUserOnline() {
        String email = "test@test.com";
        when(setOperations.isMember(anyString(), eq(email))).thenReturn(true);

        IsOnlineResponse response = userStatusService.isUserOnline(email);

        assertTrue(response.online());
    }
}