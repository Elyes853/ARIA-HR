package com.itpeac.ariarh.middleoffice.service;

import com.itpeac.ariarh.middleoffice.configuration.ApplicationProperties;
import com.itpeac.ariarh.middleoffice.domain.Candidate;
import com.itpeac.ariarh.middleoffice.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    //private static final String BASE_URL = "baseUrl";

    private final JavaMailSender javaMailSender;

    private final SpringTemplateEngine templateEngine;

    private final ApplicationProperties applicationProperties;

    public MailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine, ApplicationProperties applicationProperties) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.applicationProperties = applicationProperties;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) throws UnsupportedEncodingException {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(new InternetAddress(applicationProperties.getEmail().getFrom(), applicationProperties.getEmail().getSender()));
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(UserDTO user, String templateName, String titleKey) {
        Context context = new Context();
        context.setVariable(USER, user);
        String content = templateEngine.process(templateName, context);
        try {
            sendEmail(user.getEmail(), titleKey, content, false, true);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
    }

    @Async
    public void sendActivationEmail(UserDTO user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "Activation");
    }

    @Async
    public void sendCreationEmail(UserDTO user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "Inscription");
    }

    @Async
    public void sendPasswordResetMail(UserDTO user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendEmailFromTemplate(Candidate candidate, String templateName, String titleKey) {
        Context context = new Context();
        context.setVariable(USER, candidate);
        String content = templateEngine.process(templateName, context);
        try {
            sendEmail(candidate.getEmail(), titleKey, content, false, true);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
    }

    private String replaceVariables(String template, Map<String, String> variables) {
        String content = template;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            content = content.replace(entry.getKey(), entry.getValue());
        }
        return content;
    }

    public String getContentFromTemplateAndReplaceVariables(Candidate candidate, String lien)
            throws IOException {

        Class<MailService> clazz = MailService.class;
        InputStream inputStream = clazz.getResourceAsStream("/templates/mail/EmailCandidature.html");
        String data = readFromInputStream(inputStream);

        Map<String, String> variables = new HashMap<>();
        variables.put("${user}", candidate.getName());
        variables.put("${lienEntretien}", lien);

        data = replaceVariables(data, variables);

        return data;
    }

    private String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }


    public Void sendEmail(Candidate candidate, String lien) {
        String templateName = "emailTemplate";
        String titleKey = "email.title";
        try {

            String content = getContentFromTemplateAndReplaceVariables(candidate, lien);
            sendEmail(candidate.getEmail(), "Invitation Entretien", content, false, true);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}

