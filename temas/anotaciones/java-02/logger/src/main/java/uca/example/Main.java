package uca.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
    
@SpringBootApplication
@RestController
public class Main {
 @InjectLogger(rol = "Jefe")
    private static Logger jefeLogger;

    @InjectLogger(rol = "Administrador")
    private static Logger administradorLogger;

    @InjectLogger(rol = "Usuario")
    private static Logger usuarioLogger;

    @GetMapping("/")
    public String getLoggers() throws IllegalArgumentException, IllegalAccessException {
        Trabajador trabajador = new Trabajador("Juan", "Programador", "12345678A", "1234", 1000.0);
        Empresa empresa = new Empresa("UCA", "A12345678", "Cádiz");

        String resultados = "";

        resultados += jefeLogger.log(trabajador) + "\n";
        resultados += jefeLogger.log(empresa) + "\n";
        resultados += administradorLogger.log(trabajador) + "\n";
        resultados += administradorLogger.log(empresa) + "\n";
        resultados += usuarioLogger.log(trabajador) + "\n";
        resultados += usuarioLogger.log(empresa);

        return resultados;
    }

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {    
        Main main = new Main();
        LoggerInjector.injectLoggers(main);
        
        SpringApplication.run(Main.class, args);
    }
}