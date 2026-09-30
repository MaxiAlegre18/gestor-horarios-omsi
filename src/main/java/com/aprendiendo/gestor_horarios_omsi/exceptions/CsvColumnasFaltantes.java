package com.aprendiendo.gestor_horarios_omsi.exceptions;

public class CsvColumnasFaltantes extends RuntimeException {

    public CsvColumnasFaltantes(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

}
