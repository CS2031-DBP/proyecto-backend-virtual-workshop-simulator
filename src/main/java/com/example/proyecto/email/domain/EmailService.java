package com.example.proyecto.email.domain;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendHtmlMessage(Map<String, Object> variables, String destinatario) {

        Context context = new Context();
        context.setVariables(variables);

        String contenidoHtml = templateEngine.process("plantilla", context );

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

            helper.setTo(destinatario);
            helper.setSubject("Confirmación de actividad");
            helper.setText(contenidoHtml, true);

            mailSender.send(mensaje);

        } catch (MessagingException e) {

            throw new RuntimeException("Error al enviar el correo", e);
        }
    }

}
