# Ejemplo Cafeteria
Este ejemplo sirve para reflejar el uso de inyeción de dependencias en Java con el framework Spring

El ejemplo se basa en una cafetería que puede ervir distintos tipos de café, para ello utiliza una máquina de café a la que se le inyecta gracias a Spring el café correspondiente al pedido .


## Explicación de [Cafe.java](./cafeteria/src/main/java/uca/example/tiposCafe/Cafe.java)
``` java
public interface Cafe {
    String preparar();
}
```
Interfaz de un café, cada tipo de cafe tiene que implementar cómo se elabora.
Tipos de café en el sistema:
- Espresso
- Latte
- Mocha


## Explicación de [MaquinaCafe.java](./cafeteria/src/main/java/uca/example/MaquinaCafe.java)
```Java
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
```
Clase que representa una máquina de café. Utiliza un objeto `Cafe` que es inyectado con Spring.
La anotación `@Component` se utiliza a la hora de definir una clase para indicar que las instancias de `MaquinaCafe` son `Beans`, de esta forma cuando se cree una instancia, spring la guardará en el contexto para intentar inyectarla posteriormente.

La anotación `@Autowired` se utiliza en el constructor para indicar a Spring que le inyecte una instancia de `Cafe`.


## Explicación de [BarService.java](./cafeteria/src/main/java/uca/example/BarService.java)
```Java
@Service
public class BarService {
    private MaquinaCafe maquinaCafe;

    // Inyecta la dependencia de MaquinaCafe
    @Autowired
    public BarService(MaquinaCafe maquinaCafe) {
        this.maquinaCafe = maquinaCafe;
    }

    // Devuelve un string con el pedido de cafe y la preparacion del cafe
    public String pedirCafe(String tipoCafe, int cucharadasAzucar) {
        String pedido = "";
        pedido += "Pedido de " + tipoCafe + " con ";
        pedido += cucharadasAzucar + " cucharadas de azucar\n";
        
        pedido += maquinaCafe.prepararCafe(cucharadasAzucar) + "\n";
        return pedido;
    }
}
```
Clase para realizar el pedido personalizado de un `Cafe`, dependiendo del tipo de café y la cantidad de cucharadas de azucar.
La anotación `@Service` se utiliza en la definición de la clase para indicar que las instancias creadas son `Beans` de tipo servicio.

La anotación `@Autowired` se utiliza en el constructor para indicar a Spring que le inyecte una instancia de `MaquinaCafe`.

El método `pedirCafe` recibe los detalles del pedido, tipo del cafe y cantidad de cuharadas de azucar, y devuelve un string con el pedido y la elaboración del tipo de café pedio.

## Explicación de [CafeConfig.java](./cafeteria/src/main/java/uca/example/CafeConfig.java)
```Java

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
```
La clase `CafeConfig` es una clase de configuración de Spring que define beans para los diferentes tipos de `Cafe`.
La anotación `@Configuration` al inicio de la clase indica a Spring que contiene las definiciones de los `Beans`. Spring se ocupará de crear los los `Beans` al inicio de la aplicación y los guardará en el contexto para poder inyectarlos donde hiciese falta.
La anotación `@ComponentScan` indica a Spring donde tiene que buscar los componentes y servicios.
La anotación `@Bean` indica a Spring que las instancias que tiene que crear y mantener en el contexto.
La anotación `@Primary` indica a Spring el `Bean` que se inyectará si no se especifica ninguno.

## Explicación de [Main.java](./cafeteria/src/main/java/uca/example/Main.java)
```Java
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
```
El método `main` crea un contexto de aplicación de Spring utlizando `AnnotationConfigApplicationContext` para definir el contexto y `CafeConfig.class` para indicar a Spring los `Beans` definidos que tiene que utilizar.
El método `getBean()` obtiene el `Bean` de `BarService` del contexto de la aplicación ya configurado para su uso.

## Pruebas
Para las pruebas se utiliza [BarServicioTest.java](./cafeteria/src/test/java/uca/example/BarServicioTest.java) y [MaquinaCafe.java](./cafeteria/src/test/java/uca/example/MaquinaCafeTest.java)
Las pruebas se realizan con `JUnit`

### Ejecutar los test
```
mvn test
```

## Construir y desplegar
### Crear el paquete jar
``` shell
mvn package
```
### Crear la imagen de docker
```shell
docker build -t cafeteria_app . 
```
### Desplegar el contenedor con la aplicación
```shell
docker run cafeteria_app
```
