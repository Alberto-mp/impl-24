package uca.example.tiposCafe;

import org.springframework.stereotype.Component;

@Component
public class Espresso implements Cafe{
    @Override
    public String preparar() {
        return "Preparando Espresso";
    }
}
