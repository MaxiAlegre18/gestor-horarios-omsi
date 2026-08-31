package com.aprendiendo.gestor_horarios_omsi.model;

public enum TipoJornada {
    HABILES(799), SABADOS(800), DOMINGOS(960);

    private final int numeroTipoJornada;

    TipoJornada(int i) {
        this.numeroTipoJornada = i;
    }

    public int getNumeroTipoJornada() {
        return this.numeroTipoJornada;
    }

}
