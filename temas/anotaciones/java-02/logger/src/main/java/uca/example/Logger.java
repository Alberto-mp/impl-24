package uca.example;

public interface Logger{
    String log(Object obj) throws IllegalArgumentException, IllegalAccessException;
}

class JefeLogger implements Logger {
    @Override
    public String log(Object obj)  throws IllegalArgumentException, IllegalAccessException {
        return "[Jefe] " + LoggerService.getInfo(obj, "Jefe");
    }
}

class AdministradorLogger implements Logger {
    @Override
    public String log(Object obj) throws IllegalArgumentException, IllegalAccessException {
       return "[Administrador] " + LoggerService.getInfo(obj, "Administrador");
    }
}

class UsuarioLogger implements Logger {
    @Override
    public String log(Object obj) throws IllegalArgumentException, IllegalAccessException {
       return "[Usuario] " + LoggerService.getInfo(obj, "Usuario");
    }
}
