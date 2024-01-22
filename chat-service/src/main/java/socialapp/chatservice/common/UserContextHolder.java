package socialapp.chatservice.common;

import lombok.experimental.UtilityClass;
import socialapp.chatservice.model.entity.AppUser;

@UtilityClass
public class UserContextHolder {
    private static final ThreadLocal<AppUser> userThreadLocal = new ThreadLocal<>();

    public static AppUser getCurrentUser() {
        return userThreadLocal.get();
    }

    public static void setCurrentUser(AppUser appUser) {
        userThreadLocal.set(appUser);
    }

    public static void clear() {
        userThreadLocal.remove();
    }
}