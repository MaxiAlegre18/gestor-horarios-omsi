package com.aprendiendo.gestor_horarios_omsi.model;

import java.time.LocalTime;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TurnoViajeFormatoCsv {

    @CsvBindByPosition(position = 0, required = true)
    private String nombre;
    @CsvBindByPosition(position = 1, required = true)
    private String garage;
    @CsvBindByPosition(position = 2, required = true)
    private int codigoJornada;
    @CsvBindByPosition(position = 3, required = true)
    private String nombreUnico;
    @CsvBindByPosition(position = 4, required = true)
    private int numeroPerfil;
    @CsvBindByPosition(position = 5, required = true)
    @CsvDate("H:mm")
    private LocalTime horaInicio;

}
