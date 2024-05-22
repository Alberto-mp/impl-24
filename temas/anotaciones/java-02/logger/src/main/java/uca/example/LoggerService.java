package uca.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface JefeAttribute {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface AdministradorAttribute {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface UsuarioAttribute {}


public class LoggerService {
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
}
