package socialapp.authservice.common.exception;

public class ResetPasswordTokenExpired extends RuntimeException{
    public ResetPasswordTokenExpired(){
        super("reset password token expired");
    }
}
