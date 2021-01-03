package peter.finalprojectparallel.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@AllArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;

    /**
     * Takes the email message we want to send to the user as input passes that message through a thymeleaf context object,
     * along with a reference to our mailTemplate html file, to the templateEngine's process method to generate an email.
     * @param message
     * @return Returns
     */
    String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);
    }
}
