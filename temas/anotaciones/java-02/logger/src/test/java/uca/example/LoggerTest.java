package uca.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LoggerTest {
    @InjectLogger(rol = "Jefe")
    private static Logger jefeLogger;

    @InjectLogger(rol = "Administrador")
    private static Logger administradorLogger;

    @InjectLogger(rol = "Usuario")
    private static Logger usuarioLogger;

    private Trabajador trabajador = new Trabajador("Juan", "Programador", "12345678A", "1234", 1000.0);

    @BeforeAll
    static void setUp() {
        LoggerTest loggerTest = new LoggerTest();
        LoggerInjector.injectLoggers(loggerTest);
    }

    @Test
    void testInjection(){
        assertNotNull(jefeLogger);
        assertNotNull(administradorLogger);
        assertNotNull(usuarioLogger);
    }

    @Test
    void testJefeLogger() throws IllegalArgumentException, IllegalAccessException {
        String expected = "[Jefe] nombre: Juan, ocupacion: Programador, dni: 12345678A, contraseña: 1234, salario: 1000.0, ";
        String actual = jefeLogger.log(trabajador);
        assertEquals(expected, actual);
    }

    @Test
    void testAdministradorLogger() throws IllegalArgumentException, IllegalAccessException {
        String expected = "[Administrador] nombre: Juan, ocupacion: Programador, dni: 12345678A, contraseña: 1234, salario: *****, ";
        String actual = administradorLogger.log(trabajador);
        assertEquals(expected, actual);
    }

    @Test
    void testUsuarioLogger() throws IllegalArgumentException, IllegalAccessException {
        String expected = "[Usuario] nombre: Juan, ocupacion: Programador, dni: *****, contraseña: *****, salario: *****, ";
        String actual = usuarioLogger.log(trabajador);
        assertEquals(expected, actual);
    }
}