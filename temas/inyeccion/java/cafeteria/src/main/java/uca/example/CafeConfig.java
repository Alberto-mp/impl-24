package uca.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import uca.example.tiposCafe.Cafe;
import uca.example.tiposCafe.Espresso;
import uca.example.tiposCafe.Latte;
import uca.example.tiposCafe.Mocha;

@Configuration
@ComponentScan({"uca.example.caferia", "uca.example"})
public class CafeConfig {
    /* 
     * Define los beans de los tipos de cafe 
     */
    
    // Define el bean por defecto
    @Bean
    @Primary
    public Cafe defaultCafe() {
        return new Espresso();
    }

    // Define los otros beans
    @Bean
    public Cafe Latte() {
        return new Latte();
    }

    @Bean
    public Cafe Mocha() {
        return new Mocha();
    }
}
