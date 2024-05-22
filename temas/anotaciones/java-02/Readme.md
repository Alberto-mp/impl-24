# Ejemplo Logger
Este ejemplo sirve para reflejar el uso de anotaciones en Java y cómo se pueden utilizar para inyectar dependencias

El ejemplo se basa en una serie de Loggers cada uno asociado a un rol, con el objetivo de visualizar y ocultar la información a la hora de Obtener la información de los atributos de una clase.
Hay tres tipos de roles:
- **Jefe**: puede ver todos los atributos
- **Administrador**: puede ver los atributos marcados como administrador o usuario
- **Usuario**: solo puede ver los atributos marcados como usuario


## Explicación de [Logger.java](./logger/src/main/java/uca/example/Logger.java)
``` java
public interface Logger{
    String log(Object obj) throws IllegalArgumentException, IllegalAccessException;
}

class JefeLogger implements Logger{
    public String log(Object obj)  throws IllegalArgumentException, IllegalAccessException {
        return "[Jefe] " + LoggerService.getInfo(obj, "Jefe");
    }
};

class AdministradorLogger implements Logger{/*  */}
class UsuarioLogger implements Logger{/*  */}
```
Interfaz Logger e impementación de cada uno de los logs según el rol (Para este ejemplo el logger devuelve un string que se envía por HTTP).

El logger devuelve el rol y los atributos del objeto pasado, con `LoggerService` según el rol del logger.

## Explicación de [LoggerService.java](./logger/src/main/java/uca/example/LoggerService.java)

``` Java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface JefeAttribute {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface AdministradorAttribute {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface UsuarioAttribute {}
```
- `@Retention(RetentionPolicy.RUNTIME)`: Especifica que las anotaciones estarán disponibles en tiempo de ejecución.
- `@Target(ElementType.FIELD)`: Especifica que las anotaciones solo pueden aplicarse a campos.
- Se definen tres anotaciones según el rol: 
  - `JefeAttribute`
  - `AdministradorAttribute`
  - `UsuarioAttribute`


``` java
// Devuelve true si el atributo se puede ver con el rol dado
private static boolean puedeVer(String rol, Field field) {
    // Atributo de Usuario
    return field.isAnnotationPresent(UsuarioAttribute.class)
        // Atributo de Administrador
        || field.isAnnotationPresent(AdministradorAttribute.class) 
        && (rol.equals("Administrador") || rol.equals("Jefe"))
        // Atributo de Jefe
        || field.isAnnotationPresent(JefeAttribute.class) && rol.equals("Jefe");
}
```
Función para comprobar si un atributo es visible según el rol proporcionado. Comprueba la etiqueta del atribuo y el rol del logger.

- **Jefe**: puede ver todos los atributos
- **Administrador**: puede ver los atributos marcados como administrador o usuario
- **Usuario**: solo puede ver los atributos marcados como usuario


``` java 
// Devuelve un string con la información del objeto obj según el rol
public static String getInfo(Object obj, String rol) throws IllegalArgumentException, IllegalAccessException {
    StringBuilder info = new StringBuilder();
    // Obtiene los atributos del objeto
    Field[] fields = obj.getClass().getDeclaredFields();
    
    for (Field field : fields) {
        field.setAccessible(true);  // Permite acceder a atributos privados
        info.append(field.getName()).append(": "); // Nombre del atributo
        // Si el atributo se puede ver con el rol dado, se muestra su valor
        if (puedeVer(rol, field)) {
            // Obtener el valor del atributo
            info.append(field.get(obj)).append(", ");
        } else {
            info.append("*****, ");
        }
    }
    return info.toString();
}
```
Función para obtener la información de los atributos del objeto pasado.
Se ocupa de obtener todos los atributos de la clase junto con sus anotaciones. Después comprueba si es visible al información según el rol del logger. Si no es visible muestra "*****"

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface InjectLogger {
    String rol();
}
```
Anotación para inyectar el logger correspondiente según el rol especificado.

Función anónima que recibe un stream de carrera y devuelve un nuevo stream donde los nombres de los circuitos están en mayúsculas.

``` java
public class LoggerInjector {
    // Inyecta los loggers en los campos anotados con @InjectLogger
    public static void injectLoggers(Object obj) {
        // Obtiene la clase del objeto
        Class<?> clazz = obj.getClass();
        // Obtiene los campos de la clase
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            // Si el campo tiene la anotación @InjectLogger
            if (field.isAnnotationPresent(InjectLogger.class)) {
                // Obtiene la anotación @InjectLogger
                InjectLogger injectLogger = field.getAnnotation(InjectLogger.class);
                String rol = injectLogger.rol();    // Obtiene el rol del logger
                Logger logger;
                
                switch (rol) { // Asigna el logger según el rol
                    case "Jefe":
                        logger = new JefeLogger();
                        break;
                    case "Administrador":
                        logger = new AdministradorLogger();
                        break;
                    case "Usuario":
                        logger = new UsuarioLogger();
                        break;
                    default:
                        throw new IllegalArgumentException("Rol no válido");
                }

                field.setAccessible(true); // Permite acceder a campos privados
                try {
                    field.set(obj, logger);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```
Clase para inyectar los loggers en los campos que hayan sido anotados con `@InjectLogger`.
Primero se obtiene la clase del objeto con `obj.getClass()`.
Luego los campos de la clase con `clazz.getDeclaredFields()`.
Seguido se comprueba si el campo tiene la anotación `@InjectLogger`.
Si tiene la anotación se obtiene el rol especificado en la anotación `injectLogger.rol()`.
Finalmente crea el logger correspondiente al rol e intenta inyectar el logger `field.set(obj, logger)`.


## Explicación de [Main.java](./logger/src/main/java/uca/example/Main.java)
Este código crea un RestController con `SpringWeb`, que está escuchando en el puerto 6060. Cuando accede, se muestran los resultados de aplicar las anotaciones a una clase `Trabajador` y a otra `Empresa` y aplicar los loggers


## Pruebas
Para las pruebas se utiliza [LoggerTest.java](./logger/src/test/java/uca/example/LoggerTest.java).
Las pruebas se realizan con `JUnit`

### Ejecutar los test
```
mvn test
```

## Construir y desplegar
### Crear el paquete jar
Dentro de `./java-02/logger/`
``` shell
mvn package
```
### Crear la imagen de docker
Dentro de `./java-02/`
```shell
docker build -t logger_app . 
```
### Desplegar el contenedor con la aplicación
Dentro de `./java-02/`
```shell
terraform init
terraform apply
```
Una vez se haya iniciado correctamente, la web estará dispoible en:
`localhost:6060`

### Detener el contenedor
Dentro de `./java-02/`
```shell
terraform destroy
```
