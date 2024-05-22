package uca.example.tiposCafe;

import org.springframework.stereotype.Component;

@Component
public class Mocha implements Cafe{
    @Override
    public String preparar() {
        return "Preparando Mocha, primero se hace un espresso, luego se añade leche y finalmente se añade chocolate";
    }
}
