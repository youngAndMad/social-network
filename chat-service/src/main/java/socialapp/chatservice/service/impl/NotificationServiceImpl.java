package socialapp.chatservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import socialapp.chatservice.model.dto.notification.Notification;
import socialapp.chatservice.model.entity.AppUser;
import socialapp.chatservice.service.NotificationService;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static socialapp.chatservice.common.AppConstants.USER_NOTIFICATIONS;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveNotification(Notification notification, AppUser appUser) {
        redisTemplate.opsForZSet()
                .add(USER_NOTIFICATIONS.formatted(appUser.getEmail()), notification, notification.getNotificationTime().toEpochSecond(ZoneOffset.UTC));
    }

    @Override
    public List<Notification> checkNotification(AppUser appUser) {
        var notifications = redisTemplate.opsForZSet()
                .range(USER_NOTIFICATIONS.formatted(appUser.getEmail()), 0, -1);
        if (notifications == null) {
            return Collections.emptyList();
        }

        return notifications.stream().map(Notification.class::cast).toList();
    }
}
