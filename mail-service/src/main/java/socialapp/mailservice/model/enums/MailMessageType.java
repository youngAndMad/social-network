package socialapp.mailservice.model.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MailMessageType {
    NEWSLETTER("Newsletter", "newsletter.ftl");

    private final String subject;
    private final String template;
}
