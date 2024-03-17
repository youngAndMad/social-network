package socialapp.authservice.common.exception;

public class InvalidResetPasswordToken extends RuntimeException{
    public InvalidResetPasswordToken(){
        super("invalid reset password token");
    }
}
