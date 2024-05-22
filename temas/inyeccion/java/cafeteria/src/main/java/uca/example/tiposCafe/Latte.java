package uca.example.tiposCafe;

import org.springframework.stereotype.Component;

@Component
public class Latte implements Cafe{
    @Override
    public String preparar() {
        return "Preparando Latte, primero se hace un espresso y luego se añade leche";
    }
}
