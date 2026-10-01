package com.aprendiendo.gestor_horarios_omsi.model;

import java.time.LocalTime;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Viaje {
    private String nombreUnico;
    private int numeroPerfil;
    private LocalTime horaInicio;

    public Viaje(String nombreUnico, int numeroPerfil, LocalTime horaInicio) {
        this.nombreUnico = nombreUnico;
        this.numeroPerfil = numeroPerfil;
        this.horaInicio = horaInicio;
    }

    public int getHoraInicioEnMinutos() {
        return (horaInicio.getHour() * 60) + horaInicio.getMinute();
    }

}
