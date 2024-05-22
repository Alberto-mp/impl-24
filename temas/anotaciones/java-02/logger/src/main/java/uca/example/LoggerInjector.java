package uca.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface InjectLogger {
    String rol();
}


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