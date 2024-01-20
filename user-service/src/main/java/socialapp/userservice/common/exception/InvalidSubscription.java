package socialapp.userservice.common.exception;

public class InvalidSubscription extends RuntimeException{
    public InvalidSubscription(){
        super("between this users does not exist subscription");
    }

}
