package br.com.escuderodev.parking.services;

import br.com.escuderodev.parking.models.email.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger((EmailService.class));

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendMail(EmailDetails emailDetails) {
    LOGGER.info("Enviando e-mail sem anexo!");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(emailDetails.getRecipient());
        mailMessage.setSubject(emailDetails.getSubject());
        mailMessage.setText(emailDetails.getMessageBody());

        javaMailSender.send(mailMessage);

        LOGGER.info("Email enviado com sucesso!");
        return "Email enviado com sucesso!";
    }

    public String sendMailWithAttachment(EmailDetails emailDetails) {
        LOGGER.info("Enviando e-mail com anexo!");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(emailDetails.getRecipient());
            mimeMessageHelper.setSubject(emailDetails.getSubject());
            mimeMessageHelper.setText(emailDetails.getMessageBody());

            FileSystemResource file = new FileSystemResource(new File(emailDetails.getAttachment()));
            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);

            javaMailSender.send(mimeMessage);

            LOGGER.info("Email enviado com sucesso!");
            return "Email enviado com sucesso!";
        } catch (MessagingException e) {
            LOGGER.error("Erro ao enviar email...", e);
            throw new RuntimeException(e);
        }

    }
}
