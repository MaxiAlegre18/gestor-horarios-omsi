package com.aprendiendo.gestor_horarios_omsi.model;

import java.util.List;

public class Turno {
    private String nombre;
    private String garage;
    private TipoJornada tipoJornada;
    private List<Viaje> listaViajes;

    public Turno(String nombre, String garage, TipoJornada tipoJornada, List<Viaje> listaViajes) {
        this.nombre = nombre;
        this.garage = garage;
        this.tipoJornada = tipoJornada;
        this.listaViajes = listaViajes;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGarage() {
        return garage;
    }

    public TipoJornada getTipoJornada() {
        return tipoJornada;
    }

    public List<Viaje> getListaViajes() {
        return listaViajes;
    }

}
