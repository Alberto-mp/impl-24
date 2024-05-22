package uca.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uca.example.tiposCafe.Cafe;

@Component
public class MaquinaCafe {
    private Cafe cafe;

    // Inyecta la dependencia de Cafe
    @Autowired
    public MaquinaCafe(Cafe cafe) {
        this.cafe = cafe;
    }

    // Devuelve un string con la preparacion del cafe
    public String prepararCafe(int cuharadasAzucar) {
        return cafe.preparar() + " con " + cuharadasAzucar + " cucharadas de azucar";
    }
}
