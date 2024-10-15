[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/42utwHoA)
# 💻 Plataforma para la ayuda comunitaria al estudiante.

## *CS 2031 Desarrollo Basado en Plataforma*
## Integrantes:
| Nombre                          | Código de alumno |
|---------------------------------|------------------|
| Rodolfo Daniel Garriazo Rivera  | 202220230        |
| Roger Zavaleta Alvino           | 202010438        |
| David Marcelo Quispe Velásquez  | 202120476        |
| Letizia Estefanía Torres Mariño | 202210354        |

## 2024 - 2

### Índice

1. [Introducción](#introducción)
    1. [Contexto](#contexto)
    2. [Objetivos del Proyecto](#objetivos-del-proyecto)
2. [Identificación del Problema o Necesidad](#identificación-del-problema-o-necesidad)
    1. [Descripción del Problema](#descripción-del-problema)
    2. [Justificación](#justificación)
3. [Descripción de la Solución](#descripción-de-la-solución)
    1. [Funcionalidades Implementadas](#funcionalidades-implementadas)
    2. [Tecnologías Utilizadas](#tecnologías-utilizadas)
4. [Modelo de Entidades](#modelo-de-entidades)
    1. [Diagrama de Entidades](#diagrama-de-entidades)
    2. [Descripción de Entidades](#descripción-de-entidades)
5. [Testing y Manejo de Errores](#testing-y-manejo-de-errores)
    1. [Niveles de Testing Realizados](#niveles-de-testing-realizados)
    2. [Resultados](#resultados)
    3. [Manejo de Errores](#manejo-de-errores)
6. [Medidas de Seguridad Implementadas](#medidas-de-seguridad-implementadas)
    1. [Seguridad de Datos](#seguridad-de-datos)
    2. [Prevención de Vulnerabilidades](#prevención-de-vulnerabilidades)
7. [Eventos y Asincronía](#eventos-y-asincronía)
8. [Github](#github)
    1. [Uso de Github Projects](#uso-de-github-projects)
    2. [Uso de Github Actions](#uso-de-github-actions)
9. [Conclusiones](#conclusiones)
    1. [Logros del Proyecto](#logros-del-proyecto)
    2. [Aprendizajes Clave](#aprendizajes-clave)
    3. [Trabajo Futuro](#trabajo-futuro)
10. [Apéndices](#apéndices)
    1. [Licencia](#licencia)
    2. [Referencias](#referencias)


### Introducción
#### Contexto
Durante los últimos años, En la Utec se realizan cambios constantes a las mallas y condenidos de cada curso 
a proposito de innovar debido a la rápida digitalización y eventos globales . Es así que ha surgido una 
creciente necesidad de materiales de cursos , asesorias y herramientas de aprendizaje , orientadas 
al desarrollo de las habilidades necesarias para poder aprobar de forma satisfactoria los  cursos acorde a las
necesidades de los estudiantes.

#### Objetivos del Proyecto
- Permite al estudiante averiguar qué materiales y ejercicios necesita estudiar para lograr aprobar un curso determinado.
- Creación de comunidades para un curso determinado.
- facilitar la organización de asesorías.


### Identificación del Problema o Necesidad
#### Descripción del Problema
La educación virtual permite a los estudiantes  ofrecer diferentes materiales
académicos y asesorias abarcando diversas materias , lo que también permite a los
estudiantes tener mas contenido para elegir respecto a las necesidades academicas de su persona
Además, se facilita el acceso a asesorias y recursos desde cualquier lugar con
conexión a internet, lo que elimina las limitaciones que pueden enfrentar los
estudiantes al buscar matriales de estudio. Esto es especialmente beneficioso para aquellos 
que tiene examenes parciales o finales y deseen estar preparados para ellos.


#### Justificación
La Plataforma para la ayuda comunitaria al estudiante.responde a la creciente necesidad de
soluciones de aprendizaje accesibles y prácticas para los estudiantes de utec.

### Descripción de la Solución
#### Funcionalidades Implementadas
- Plataforma web y móvil con la capacidad de ser accesible por una gran cantidad de dispositivos.
- Acceso a base de datos en google drive.
- APIs para la reproducción de videos.
- Servicio de notificaciones para aviso de nuevos materiales , ejercicios agregados y asesorías.
- Se permite la creación de eventos para organizar asesorías grupales entre estudiantes
- Sistema de autenticación con Oauth 2 para verificar que solo estudiantes de utec sean capaces de acceder.
- Capacidad de crear  formularios con ejercicios para practicar

#### Tecnologías Utilizadas
**Amazon s3:**
Utilizamos esta API para almacenar archivos de manera segura en la nube a
través de ella, esto nos permite acceder a ellos desde cualquier lugar
mediante una URL.
**WhereBy**
Esta API nos permite automatizar la creación de reuniones virtuales mediante
la generación de enlaces, facilitando el proceso y ahorrándonos el trabajo de
hacerlo de forma manual.
**TinyUrl**
Con ella podemos acortar los enlaces creados para que sean más fáciles de compartir.
**Spring Boot**
Nos permite desarrollar una API en pase a los principios REST
**Java**
Lenguaje de programacion utilizado en el proyecto
**IntelliJ**
IDE de java y otos lengajes utilizado para desarrollar el proyecto.

### Modelo de Entidades
#### Diagrama de Entidades
- Se encuentra la imagen del diagrama en la raiz del repositorio con el nombre "Diagrama_Entidades"

#### Descripción de Entidades
Se Tiene A las Siguientes Entidades:
- Actividad.
- Calificacion.
- Carrera.
- Comentario.
- Curso.
- Email.
- Material.
- Post.
- Usuario.
Cada entidad tiene una funcion en la API.
- Todas las entidades tienen un Controller, Service y Repository de acuerdo a las necesidades de cada uno.

### Testing y Manejo de Errores
#### Niveles de Testing Realizados
- Se relizo pruebas unitarias y de Integracion para cada entidad.
#### Resultados
- Los resultados fueron satisfactorios para la mayoría de las Pruebas Implementadas.
- Eciste la presencia de varios errores en los test de java como consecuencia de errores misteriosos en la programacion
de los test.
- A pesar de ello los test en el Postman Collection si se ejecutan de forma satisfactoria.

#### Manejo de Errores
- Se realizo el manejo de errores al utilizar Excepciones globlase y integradas en algunas Dependencias.
- las Excepciones se manifiestan como errores en el log para el analisis del desarrollador.
- Cada error tiene una pequeña descripccion de su causa, util para identificar su solucion.

### Medidas de Seguridad Implementadas
#### Seguridad de Datos
- Se implementó un sistema de gestión de permisos para los usuarios.
- Solo los administradores sean capaces de tener acceso a Metodos delicados de uso por el usuario para su seguridad.
- Se utiliza el principoi "Stateless" en las 2  implementaciones de Security por mejorar.

#### Prevención de Vulnerabilidades
- Se utilizaron las capacidades de  Spring Security para prevenir posibles ataques y mitigar sus impactos.
- En 1 de las 2 implementaciones se Security se encuentra presente el uso de COORS.


### Eventos y Asincronía
- Se utilizaron los eventos para el envio de Correos y el acceso a datos mediante Amazon S3.
- El uso de las siguentes APIs fueron importantes en el proyecto, ya que varias funcionalidades
- dependen de las APIs mencionadas.
- Varios de estos Eventos son asincronos paara permitir a nuestra API 

### Github
#### Uso de Github Projects
- Se Creó un issue por cada parte o grupo de funcionalidades que conforman un apartado de la API:.
- Securty
- Testing
- Entidades
- Eventos
- Deploy AWS
- Dependiendo del tipo de tarea se creó varias branch para avanzar cada tarea y luego, con el consenso del grupo, se 
realiza un pull request, solucionan conflictos y luego se hacer merge con la branch main.
- En ocasiones, previo consenso, se realiza un push directo al main para agilizar la adicion de cambios.
#### Uso de Github Actions
- Se utilizó el github actions para verificar que las partes que conponen al proyecto se encuentren presentes.

### Conclusiones
#### Logros del Proyecto
- Una implementacion completa de las entidades necesarias para la logica de la API.
- El uso de APIs externas en varias funcionalidades de la API.
- La demostracion del uso de github actions y pull request
- El Trabajo en equipo estu presente durane es desarrollo del proyecto.

#### Aprendizajes Clave
- Se aprendió a utilizar fuentes externas como referencia y guia durante el desarrollo del proyecto.
- Se investigo sobre el manejo de archivos y envio de diverso eventos por correo.
#### Trabajo Futuro
- Terminar de Implementar los Test para cada Entidad y Controller.
- Revisar el motivo por el cual tenemos problemas con las 2 implementaciones de auth creadas.
- Realizar una mejor planificacion para el futuro desarrollo de proyecto.

### Apéndices
#### Licencia
Este proyecto es distribuido bajo la licencia *GNU General Public License v3*

#### Referencias
Se utilizaron los materiales proporcionados durante las clases y asesorías del curso de
Desarrollo Basado en Plataformas 2024-2 hasta la semana 8, consultas en diversas 
páginas de internet para la resolución de problemas con nuestro código. 







