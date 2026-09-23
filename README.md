# PP_TP2_53351

Trabajo Práctico 2 de Paradigmas de Programación (UTN - FRM).
Legajo: 53351

## De qué se trata

Es un sistema simple para manejar eventos universitarios: se crean eventos, se les asigna una sala y actividades (charlas, talleres y cursos), se inscriben estudiantes, se emiten certificados y tickets de acceso, y se guarda todo en un archivo.

Está hecho en Java, arrancando desde `App.java`.

## Estructura del proyecto

| Package | Qué tiene |
|---|---|
| `modelo` | EventoUniversitario, Sala, Estudiante, Inscripcion |
| `modelo.actividades` | Actividad (abstracta), Charla, Taller, Curso |
| `modelo.certificacion` | Interfaz Certificable, la implementan Taller y Curso (Charla no) |
| `exepciones` | CupoExcedidoException |
| `hilos` | EnvioTicketsThread |

## Qué hace cada ejercicio

**Ejercicio 1:** si te querés inscribir a una actividad que no tiene cupo, salta la excepción `CupoExcedidoException` y se maneja con try-catch. El evento se guarda en un archivo (`evento_EV001.dat`) y después se puede volver a leer desde ahí (serialización).

**Ejercicio 2:** se pueden crear Charlas, Talleres y Cursos. Los Talleres y Cursos emiten certificado a los que participaron, las Charlas no (porque son gratis y no certifican nada).

**Ejercicio 3:** se agregaron dos métodos para filtrar actividades por tipo (usando genéricos) y para calcular el costo total de materiales de una lista de actividades (usando wildcards).

**Ejercicio 4:** las inscripciones ahora nacen "Pendiente" y hay que confirmarlas a mano. Una vez confirmada, se le puede generar un ticket de acceso (es una clase anidada dentro de Inscripcion). El envío de los tickets se hace en un hilo aparte, mientras el programa principal sigue mostrando los datos del evento.

## Cómo correrlo

Se abre el proyecto en IntelliJ y se ejecuta `App.java`. No hace falta nada más, no usa librerías externas.

Al correrlo se genera un archivo `evento_EV001.dat` en la carpeta del proyecto (es el evento guardado).

## Notas

- El costo de materiales de los Cursos lo dejé fijo en $3000, porque la consigna no aclaraba cómo calcularlo.
- En el main dejé dos casos armados: uno donde todo sale bien, y otro a propósito para que salte la excepción de cupo excedido (un taller con cupo 1 y dos inscriptos).
<img width="1823" height="2741" alt="captura_consola_TP2" src="https://github.com/user-attachments/assets/1ba51af6-8bf9-436d-980a-79cc2f355e0c" />
