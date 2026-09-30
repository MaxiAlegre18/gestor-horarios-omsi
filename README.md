# Gestor de Horarios de OMSI 2

Una aplicación web para generar automáticamente archivos de configuración de horarios para OMSI 2 a partir de archivos CSV.

## Sobre el proyecto

### El Problema
OMSI 2 es un simulador de autobuses que cuenta con un editor nativo para crear recorridos y configurar horarios. Aunque planificar estas rutas resulta mucho más ágil en una planilla de Excel, el editor del juego no es capaz de leer archivos CSV, obligando al usuario a realizar configuraciones tediosas de forma manual.

### La Solución
Esta aplicación web recibe un archivo CSV con columnas predeterminadas y genera automáticamente el archivo de configuración correspondiente que OMSI necesita para definir el horario de los servicios, acortando la brecha entre Excel y el simulador.

## Tecnologías y Motivación

**Stack:** Java, Spring Boot.

Si bien existen enfoques más sencillos y directos para resolver este problema (como un simple script o una aplicación de escritorio local), opté por utilizar **Java Spring Boot**. 

El objetivo principal detrás de esta decisión es netamente educativo. Esta es mi primera aplicación con el framework, por lo que el código se encuentra en constante evolución: a medida que aprendo nuevos conceptos, actualizo y mejoro el sistema. Sé que usar Spring Boot para un problema de este tamaño es como *matar un mosquito con una bazooka*, pero resultó ser el proyecto perfecto para asentar mis bases prácticas ya que resuelve una necesidad real.

## Instalación y Uso

*W.I.P.*

## Requisitos y Especificaciones

Los requerimientos detallados y el comportamiento esperado del sistema se encuentran documentados en [REQUIREMENTS.md](REQUIREMENTS.md).
