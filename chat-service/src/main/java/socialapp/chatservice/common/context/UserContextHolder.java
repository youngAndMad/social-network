package socialapp.chatservice.common.context;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import socialapp.chatservice.model.entity.AppUser;

@UtilityClass
@Slf4j
public class UserContextHolder {
    private static final ThreadLocal<AppUser> userThreadLocal = new ThreadLocal<>();

    public static AppUser getCurrentUser() {
        return userThreadLocal.get();
    }

    public static void setCurrentUser(AppUser appUser) {
        log.debug("Setting user: {} to UserContextHolder", appUser);
        userThreadLocal.set(appUser);
    }

    public static void clear() {
        log.debug("Clearing user context");
        userThreadLocal.remove();
    }
}