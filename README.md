# Microservicio api-clients

La siguiente codificación corresponde al microservicio orientado al manejo de clientes.

## Estructura del proyecto

Para un mejor manejo de las clases, la implementación se realizó haciendo uso de una arquitectura en capas, donde se tiene lo siguiente:

- dao: Definición de repositorios para la interacción con la base de datos
- enums: Definición de estados en una clase
- exception: Excepciones lanzadas al no cumplir ciertas validaciones
- model: Definición de clases que mapean las entidades de la base de datos
- resource: Definición de endpoints
- service: Definición de lógica del negocio

