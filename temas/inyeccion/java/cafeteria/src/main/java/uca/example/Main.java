package uca.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        // Crea el contexto de Spring con la configuracion de CafeConfig
        // ApplicationContext implementa AutoCloseable, por lo que se cierra automaticamente al salir del try
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CafeConfig.class)) {
            // Obtiene el bean de BarService
            BarService bar = context.getBean(BarService.class);
            
            // Imprime los pedidos de cafe con diferentes tipos de cafe, 
            // la cantidad de azucar y la preparacion del cafe
            System.out.println(bar.pedirCafe("Espresso", 1));
            System.out.println(bar.pedirCafe("Latte", 2));
            System.out.println(bar.pedirCafe("Mocha", 3));
        }
    }
}