package com.example.proyecto.email.event;


import com.example.proyecto.email.domain.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailListener {

    private final EmailService emailService;

    public EmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    @Async
    public void onEmailEvent(EmailEvent event) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("nombreUsuario", event.getActividad().getUsuario().getNombre());
        variables.put("nombreActividad", event.getActividad().getNombre());
        variables.put("fechaActividad", event.getActividad().getFechaActividad().toString());
        variables.put("enlaceActividad", event.getActividad().getEnlace());
        variables.put("tipoActividad", event.getActividad().getTipo().toString());

        emailService.sendHtmlMessage( variables, event.getActividad().getUsuario().getEmail());
    }
}