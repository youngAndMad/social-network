package socialapp.chatservice.common.exception;

public class CreatePrivateChatMemberNotExistException extends RuntimeException{
    public CreatePrivateChatMemberNotExistException(String email) {
        super("user with email %s does not exist".formatted(email));
    }
}
