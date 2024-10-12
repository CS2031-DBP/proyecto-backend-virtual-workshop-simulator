package com.example.proyecto.email.event;

import com.example.proyecto.actividad.domail.Actividad;
import lombok.Getter;

@Getter
public class EmailEvent {

    private  final Actividad actividad;
    public EmailEvent(Actividad actividad) {
        this.actividad = actividad;

    }
}
