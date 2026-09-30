# Gestor de horarios de colectivos (OMSI 2) - Versión 1.0 de requerimientos
Se necesita una aplicación para crear y editar los turnos de una línea de autobús para el simulador OMSI 2.

Cada turno (newtour) se identifica con un nombre, el tipo de autobús utilizado y un número asociado a la jornada que realiza (por ejemplo, si opera de lunes a viernes el número es 799, si opera solo domingos y feriados el número es 960) Dentro de cada turno, hay varios viajes (addtrip) que se identifica con tres parámetros:
- Nombre único del viaje
- Número de perfil (un viaje puede tener diversas duraciones según el momento del día, por ejemplo, si el horario es normal el número 0, si el horario es nocturno el número es 1)
- Hora de inicio del viaje expresada en minutos del día (por ejemplo, las 3:14 AM está representado por el minuto 194, las 16:36 está representado por el minuto 996)

Por el momento, la aplicación debe permitirle al usuario crear un turno (ingresando nombre del turno, garage y número asociado a la jornada que realiza), dicha información, será almacenada en un archivo de texto con el siguiente formato:

```
------------------------------------

[newtour]
<Nombre del turno>
<Garage>
<Jornada>

------------------------------------
```

Luego, el usuario debe poder importar una planilla CSV con las siguientes columnas:
- Nombre del viaje
- Número de perfil
- Hora de inicio (en formato HH:MM)
La aplicación debe ser capaz de procesar el CSV y armar los viajes según la información obtenida en el CSV. Por cada viaje ingresado, el programa debe agregar en el archivo de texto anterior, el siguiente fragmento:

```
  Dep.: <Hora de salida en HH:MM:SS (se asume que SS = 00)>
[addtrip]
<Nombre del viaje>
<Número de perfil>
<Minutos>
```

Si el programa detecta alguna fila o columna erronea en el CSV, detiene el procesamiento y se avisa al usuario del error cometido. Una vez que el programa termine de procesar la información exitosamente, debe poder permitirle al usuario descargar el archivo de texto obtenido con el nombre Turno_<NombreDelTurno>.txt
