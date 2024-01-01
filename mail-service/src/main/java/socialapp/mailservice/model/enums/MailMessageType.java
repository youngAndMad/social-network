package socialapp.mailservice.model.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing different types of email messages, each associated with a specific subject and template.
 * This enum provides a structured way to define various types of email messages.
 */
@AllArgsConstructor
@Getter
public enum MailMessageType {
    /**
     * Represents a newsletter email message.
     * - Subject: "Newsletter"
     * - Template: "newsletter.ftl" (FreeMarker template)
     */
    NEWSLETTER("Newsletter", "newsletter.ftl");

    /**
     * The subject of the email message.
     */
    private final String subject;

    /**
     * The template used for rendering the email content.
     * This template is in FreeMarker (ftl) format.
     */
    private final String template;
}
