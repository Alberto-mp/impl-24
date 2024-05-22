package uca.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import uca.example.tiposCafe.Cafe;
import uca.example.tiposCafe.Espresso;
import uca.example.tiposCafe.Latte;
import uca.example.tiposCafe.Mocha;

public class BarServicioTest {
    private Cafe espresso = new Espresso();
    private Cafe latte = new Latte();
    private Cafe mocha = new Mocha();

    @Test
    public void testPedidoEspresso() {
        MaquinaCafe maquinaCafe = new MaquinaCafe(espresso);
        BarService bar = new BarService(maquinaCafe);
        
        String esperado = "Pedido de Espresso con 2 cucharadas de azucar\n";
        esperado += espresso.preparar();
        esperado = esperado + " con 2 cucharadas de azucar\n";

        assertEquals(esperado, bar.pedirCafe("Espresso", 2));
    }

    @Test
    public void testPedidoLatte() {
        MaquinaCafe maquinaCafe = new MaquinaCafe(latte);
        BarService bar = new BarService(maquinaCafe);
        
        String esperado = "Pedido de Latte con 2 cucharadas de azucar\n";
        esperado += latte.preparar();
        esperado = esperado + " con 2 cucharadas de azucar\n";

        assertEquals(esperado, bar.pedirCafe("Latte", 2));
    }

    @Test
    public void testPedidoMocha() {
        MaquinaCafe maquinaCafe = new MaquinaCafe(mocha);
        BarService bar = new BarService(maquinaCafe);
        
        String esperado = "Pedido de Mocha con 2 cucharadas de azucar\n";
        esperado += mocha.preparar();
        esperado = esperado + " con 2 cucharadas de azucar\n";

        assertEquals(esperado, bar.pedirCafe("Mocha", 2));
    }
}
