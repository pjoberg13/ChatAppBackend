package peter.finalprojectparallel.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import peter.finalprojectparallel.exception.QuackAppException;
import peter.finalprojectparallel.model.NotificationEmail;

@Service
@Slf4j
@AllArgsConstructor
public class MailService {

    //provided by the spring mail service dependency
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    /**
     * Method to build and send email for account verification. Message preparator essentially follows builder pattern
     * to create an email message to be sent based on the instance of NotificationEmail that is passed in.
     * We use the @Async annotation to tell Spring Boot to run this method asynchronously because building and sending an
     * email through our smtp server is takes some time and there's no need to wait for it and slow everything else down.
     * @param notificationEmail
     */
    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("accountVerification@quack.com");
            messageHelper.setTo(notificationEmail.getRecipientAddress());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email successfully sent");
        } catch (MailException e) {
            throw new QuackAppException("Exception occurred when sending verification email. Ensure email address is valid.");
        }
    }
}
