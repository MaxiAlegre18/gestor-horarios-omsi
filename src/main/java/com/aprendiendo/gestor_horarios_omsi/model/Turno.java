package com.aprendiendo.gestor_horarios_omsi.model;

import java.util.ArrayList;
import java.util.List;

public class Turno {
    private String nombre;
    private String garage;
    private TipoJornada tipoJornada;
    private List<Viaje> listaViajes;

    public Turno(String nombre, String garage, TipoJornada tipoJornada) {
        this.nombre = nombre;
        this.garage = garage;
        this.tipoJornada = tipoJornada;
        this.listaViajes = new ArrayList<>();
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

    public void agregarViaje(Viaje viaje) {
        listaViajes.add(viaje);
    }

}
