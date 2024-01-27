package socialapp.chatservice.common.context;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenContextHolder {
    private final static ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    public static void set(String token) {
        tokenHolder.set(token);
    }

    public static String get() {
        return tokenHolder.get();
    }

    public static void clear() {
        tokenHolder.remove();
    }

    public static boolean isEmpty() {
        return tokenHolder.get() == null;
    }
}
