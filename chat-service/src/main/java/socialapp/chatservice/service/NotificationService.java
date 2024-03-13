package socialapp.chatservice.service;

import socialapp.chatservice.model.dto.notification.Notification;
import socialapp.chatservice.model.entity.AppUser;

import java.util.List;

public interface NotificationService {

    void saveNotification(Notification notification,AppUser appUser);

    List<Notification> checkNotification(AppUser appUser);

}
