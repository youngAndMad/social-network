package socialapp.authservice.common.utils;

public class AppConstants {
    public static final Integer OTP_ORIGIN = 100_000;
    public static final Integer OTP_BOUND = 999_999;
    public static final String RESET_PASSWORD_ENDPOINT = "/api/v1/auth/reset-password?token=";
    public static final String KEY_PAIR_ALGORITHM = "RSA";
    public static final Integer KEY_PAIR_SIZE = 2048;

}

