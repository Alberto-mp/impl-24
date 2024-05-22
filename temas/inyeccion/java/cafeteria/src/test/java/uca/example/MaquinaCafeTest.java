package uca.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import uca.example.tiposCafe.Cafe;
import uca.example.tiposCafe.Espresso;
import uca.example.tiposCafe.Latte;
import uca.example.tiposCafe.Mocha;

public class MaquinaCafeTest {
    @Test
    public void testEspresso() {
        Cafe cafe = new Espresso();
        MaquinaCafe maquinaCafe = new MaquinaCafe(cafe);
        String esperado = cafe.preparar();
        esperado = esperado + " con 2 cucharadas de azucar";

        assertEquals(esperado, maquinaCafe.prepararCafe(2));
    }
    
    @Test
    public void testLatte() {
        Cafe cafe = new Latte();
        MaquinaCafe maquinaCafe = new MaquinaCafe(cafe);
        String esperado = cafe.preparar();
        esperado = esperado + " con 2 cucharadas de azucar";

        assertEquals(esperado, maquinaCafe.prepararCafe(2));
    }
    
    @Test
    public void testMocha() {
        Cafe cafe = new Mocha();
        MaquinaCafe maquinaCafe = new MaquinaCafe(cafe);
        String esperado = cafe.preparar();
        esperado = esperado + " con 2 cucharadas de azucar";

        assertEquals(esperado, maquinaCafe.prepararCafe(2));
    }
}
