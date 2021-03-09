# SysOne_Test
Este repositorio es la solución del desafío técnico por parte de SysOne.

# Características del proyecto:

- Proyecto Maven con integración de SpringBoot que utiliza el administrador de base de datos en memoria H2 (DBMS), y a su vez, con la suite de tests automatizados Junit 4 y Mockito. La versión de Java utilizada en este proyecto es la 1.8.
# Estructura
##### package com.sysone.devtest.controller 
 Capa que contiene toda la lógica del controlador en donde se implementan y gestionan las APIs de la aplicación. Cada API tiene su información detallada en el Javadoc.
###### Las APIs creadas en esta capa son:
- http://localhost:8080/getAutomoviles - GET API que retorna la lista de todas entidades de tipo 'Automovil' persistidas en la base datos.
- http://localhost:8080/crearAutomovil - POST API que crea una entidad de tipo 'Automovil' y la persiste en la base de datos. La API recibe un objeto Json como body de la solicitud para crear el objeto 'Automovil'.

- Un ejemplo del objeto de tipo Json que requiere el body de la solicitud:
```sh
    {
    "modelo":"Hilux",
    "placa" : "RTX-360",
    "variante" : "FAMILIAR",
    "adicionales": [
        {
        "opcional": "TC"
        } ,
        {
        "opcional": "AA"
        }
        ]
    }
```        
- Otro ejemplo (sin incluir los adicionales):
```sh
     {
      "modelo":"Hilux",
      "placa" : "RTX-360",
       "variante" : "FAMILIAR"
     }
```
- En donde los campos: 
     - "modelo" : Es el nombre del modelo del automovil que se creará. Este campo es de tipo de dato String.
     - "placa" : Es el nombre de la placa del automovil que se creará. Este campo es de tipo de dato String.
     - "variante" Es el nombre de la variante con la que se producirá el automovil. Este campo es de tipo Enum, el cual puede recibir tres valores: "SEDAN", "COUPE" o "FAMILIAR", cada uno de estos valores con un costo respectivo el cual es de tipo BigDecimal. Este campo es obligatorio.
     - "adicionales" : Es el nombre de los opcionales que pueden incluir los automoviles fabricados. Este campo es de tipo Enum, el cual puede recibir cinco valores: "TC" , "AA", "ABS" , "AB" o "CC", cada uno de estos valores tiene un costo respectivo el cual es de tipo BigDecimal. Este campo es opcional.
            
-  http://localhost:8080/modificarAutomovil/{id} - PUT API que modifica una entidad de tipo 'Automovil' y la persiste en la base de datos. La API recibe un id como variable de ruta la cual corresponde al id de la entidad persistida que se desea modificar, así como también, un objeto Json como body de la solicitud para modificar la entidad deseada 'Automovil'.
-Un ejemplo del body de la solicitud, al igual que el objeto Json que recibe la POST API:
```  
                         {
	                   "modelo":"Corsa",
	                   "placa" : "RTX-360",
	                   "variante" : "SEDAN",
                           "adicionales": [
                         {
                           "opcional": "ABS"
                          } ,
                           {
                            "opcional": "AB"
                           }
                         ]
                         }
```

  - http://localhost:8080/eliminarAutomovil/{id} - DELETE API que elimina una entidad 'Automovil' de la base de datos. Esta API recibe un id como variable de ruta la cual corresponde al id de la entidad que se desea eliminar.
                 
- http://localhost:8080/stats - GET API que retorna una lista de objetos Json la cual contiene todas las estadísticas de todos los automóviles persistidos en la base de datos. Contiene información respecto a la cantidad total de los automoviles fabricados, la cantidad de variantes totales, su proporcion, la cantidad de adicionales incluidos y su proporción.
        
##### package com.sysone.devtest.model
Capa que contiene las entidades creadas las cuales se integraran a la lógico de negocio de la aplicación, así como también, los Enum que serán utilizados para establecer los precios de cada uno de los automóviles fabricados. Las entidades creadas son:
- Automovil : entidad principal, la cual representa a los automóviles fabricados. Cada automovil esta conformado por los parámetros: 
    - id: el id correspondiente de la entidad. Tipo de dato Long.
    - modelo: el modelo del auto a fabricar. Tipo de dato String.
    - placa: la placa para identificar el automovil fabricado. Tipo de dato String.
    - fechaFabricacion: la fecha de la fabricación del automovil. Tipo de dato Date.
    - Variante: la variante seleccionada al momento de la fabricación del automóvil. Esta variante es un Enum, la cual puede tomar los valores "SEDAN", "COUPE" o "FAMILIAR", cada uno de estos valores con un costo respectivo el cual es de tipo BigDecimal. Este campo es obligatorio.
    - adicionales: una lista de adicionales incluidos al momento de la fabricación del automovil, los cuales ejercen como Enum, los cuales puede tomar los valores "TC" , "AA", "ABS" , "AB" o "CC", cada uno de estos valores tiene un costo respectivo el cual es de tipo BigDecimal. Este campo es opcional.
    - costoFabricacion: el costo que tuvo el automovil al momento de su fabricación. Este costo es calculado al invocar el método calcularCostoFabricacion(), el cual considera el costo de fabricación de la variante, y los adicionales correspondientes en caso de haber sido seleccionados.
- Adicionales: entidad relacionada con 'Automovil', esta entidad posee información respecto a los opcionales seleccionados en la fabricación de los automoviles en el caso de que se hayan incluido en la misma. Cada Adicional esta conformado por los parámetros:
     - id: el id correspondiente de la entidad. Tipo de dato Long.
    - automovil: automovil relacionado al momento de su fabriación.
    - opcional: corresponde al opcional incluido al momento de la fabricación del automovil. Los opcionales ejercen como Enum, los cuales pueden tomar los valores los cuales puede tomar los valores "TC" , "AA", "ABS" , "AB", "CC", cada uno de estos valores tiene un costo respectivo el cual es de tipo BigDecimal.
    - costo: el costo que tiene el opcional seleccionado.
- Variante: Enum el cual contiene los valores que pueden tomar las diferentes variantes que tendrá el automovil al momento de su fabricación. Puede tomar los valores "SEDAN", "COUPE" o "FAMILIAR", cada uno de estos valores con un costo respectivo el cual es de tipo BigDecimal.
- OpcionalesEnum: Enum el cual contiene los valores que pueden tomar los opcionales incluidos al momento de la fabricación del automovil. Puede tomar los valores "TC" , "AA", "ABS" , "AB" o "CC", cada uno de estos valores tiene un costo respectivo el cual es de tipo BigDecimal.
##### package com.sysone.devtest.repository:
Capa que contiene las interfaces que implementaran las entidades para establecer la conexión a la base de datos. Está conformada por:
- DaoAutomovil: interfaz que extiende de la librería Java Persistence API y establece la conexión de la entidad Automovil para realizar diferentes operaciones con la base de datos.
- DaoAdicionales: interfaz que extiende de la librería Java Persistence API y establece la conexión de la entidad Adicionales para realizar diferentes operaciones con la base de datos.
##### package com.sysone.devtest.service:
Capa que contiene los servicios proporcionados por cada entidad para realizar diferentes operaciones relacionadas a la lógica del negocio. Esta conformada por:
- AutomovilService: clase que ejerce como servicio para realizar las diferentes operaciones para el cálculo de las estadísticas relacionadas a esta entidad.
- AdicionalesService: clase que ejerce como servicio para realizar las diferentes operaciones para el cálculo de las estadísticas relacionadas a esta entidad.

Por último, el proyecto contiene la suite de los tests unitarios y de intregación en la ubicación:
##### src/test/java 
Conformada a su vez, por tres clases: 
- DevTestControllerTest : la cual contiene las pruebas realizadas para comprobar que las solicitudes de cada API implementada en el Controller se construyen correctamente.
- DevTestDatabaseTest: la cual contiene las pruebas para comprobar que las operaciones básicas relacionadas a la base de datos se están realizando correctamente. 
- DevTestServicesTest: la cual contiene las pruebas realizadas para comprobar que los métodos creados relacionados a la lógica de negocio no posean errores.
         
# ¿Cómo ejecutar la aplicación?
- Si ya se descargó el proyecto y se importó a un nuevo workspace utilizando un IDE, como por ejemplo, Eclipse, solo basta con dirigirse al paquete 'com.sysone.devtest', hacer click derecho en la clase 'DevTestApplication.java', elegir la opción 'run as' , y a continuación seleccionar 'Java Application'.
- Si se desea ejecutar la aplicacion desde la consola de Windows, basta con dirigirse al directorio principal donde se encuentra contenido el proyecto desde el cmd y ejecutar el comando: mvnw spring-boot:run. 

# ¿Con quién me comunico en caso de consultas?
- Contactarse con el dueño y administrador del proyecto, Andrés Lizarraga.
- Correo electrónico: andresalv7@gmail.com
