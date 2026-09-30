package com.aprendiendo.gestor_horarios_omsi.exceptions;

public class CsvDatosIncorrectos extends RuntimeException {

    public CsvDatosIncorrectos(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

}
