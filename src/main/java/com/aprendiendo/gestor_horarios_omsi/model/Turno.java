package com.aprendiendo.gestor_horarios_omsi.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Turno {
    private String nombre;
    private String garage;
    private TipoJornada tipoJornada;

    @Builder.Default
    private List<Viaje> listaViajes = new ArrayList<>();

    public Turno(String nombre, String garage, TipoJornada tipoJornada) {
        this.nombre = nombre;
        this.garage = garage;
        this.tipoJornada = tipoJornada;
        this.listaViajes = new ArrayList<>();
    }

    public void agregarViaje(Viaje viaje) {
        listaViajes.add(viaje);
    }

}
