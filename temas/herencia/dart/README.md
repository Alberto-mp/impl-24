# Herencia en Dart

La herencia es uno de los conceptos más característicos de la Programación Orientada a Objetos, y permite a una clase a la que llamamos hija o subclase heredar las características (métodos, propiedades...) de otra clase a la que llamamos clase padre o súperclase.

A continuación, vamos a ver una serie de archivos en los que vamos a poner en práctica los conceptos de herencia en el lenguaje de programación Dart, un lenguaje orientado a objetos desarrollado por Google.

# Ejemplo: El taller de Robots

Imaginemos que somos el orgulloso dueño de un taller de robots. En este taller creamos robots, que pueden moverse y girar en cualquier dirección. Sin embargo, para nosotros un robot no es nada si no tiene un objetivo, para ello hemos creado un robot "plantilla" o abstracto. Este robot puede moverse y girar, sin embargo está a la espera de que alguien le de un objetivo, o incluso de incorporarle más acciones. Veamos como podríamos programar este robot.

## El Robot sin rumbo

Si nos fijamos en el archivo [robot.dart](lib/robot.dart):

```dart
abstract class Robot {
  String nombre;
  String material;
  final List<String> acciones = [];

  Robot(this.nombre, this.material);

  //Acciones de cualquier robot

  void mover(String direccion) {
    acciones.add("$nombre moviéndose hacia $direccion");
  }

  void girar(String direccion) {
    acciones.add("$nombre girando hacia $direccion");
  }

  // Ejecutar las acciones del robot

  List<String> ejecutarAcciones() {
    return acciones;
  }

  //Método abstracto
  String objetivo();
}
```

Como podemos ver, nuestro robot tendrá un `nombre` y un `material` del que está hecho, además de una lista de `acciones`, con las que podremos programarle las acciones a realizar.

Con los métodos `mover()` y `girar()` añadimos a la lista de acciones el movimiento o giro hacia la direccion que queramos. Una vez hayamos programado las acciones para nuestro robot `ejecutarAcciones()` se encargará de ejecutarlas todas.

Pero, como hemos dicho antes, nuestro robot no tiene `objetivo()`, tendremos que esperar a que alguien *herede* de nuestra clase Robot y le dé un objetivo.

## El Robot Inteligente

Por suerte nuestro robot sin rumbo ha acabado en las manos de alguien con un objetivo noble.

Veamos el archivo [robot_inteligente.dart](lib/robot_inteligente.dart)

```dart
class RobotInteligente extends Robot with Hablador {
  RobotInteligente(super.nombre, super.material);

  @override
  String objetivo() {
    return "El objetivo de el robot inteligente $nombre es comunicarse con humanos y realizar cálculos complejos.";
  }

  @override
  void hablar() {
    acciones.add("HOLA. SOY. UN. ROBOT. INTELIGENTE. Y. PUEDO. HABLAR. CON. LOS. HUMANOS. MI. NOMBRE. ES. $nombre. Y. ESTOY. HECHO. DE. $material.");
  }

  void sumar(int a, int b) {
    acciones.add("$nombre sumando $a y $b, el resultado es ${a + b}");
  }

  void restar(int a, int b) {
    acciones.add("$nombre restando $a y $b, el resultado es ${a - b}");
  }

  void multiplicar(int a, int b) {
    acciones.add("$nombre multiplicando $a y $b, el resultado es ${a * b}");
  }

  void dividir(int a, int b) {
    if (b == 0) {
      acciones.add("$nombre no puede dividir por cero");
    } else {
      acciones.add("$nombre dividiendo $a y $b, el resultado es ${a / b}");
    }
  }
}
```

Como podemos comprobar, alguien ha hecho justo lo que queriamos con nuestro robot:

- El `RobotInteligente` es una subclase de nuestro `Robot`. Como detalle, podemos ver que el constructor de esta clase delega en el de la súperclase.
- Se ha implementado el `objetivo()` del robot
- Se han añadido nuevas acciones, entre ellas encontramos una serie de funciones que realizan cálculos "complejos" y se le ha otorgado la capacidad de hablar

Se puede apreciar que, el `RobotInteligente` recibe la capacidad de `hablar` de un mixin llamado `hablador`, los mixins no son un concepto de herencia, aunque están relacionados y puesto que no es común que un lenguaje de programación incorpore los mixins directamente como Dart me parecía buena idea añadirlo. El mixin `hablador` y otra clase `persona` que lo utiliza se pueden encontrar en los archivos [hablador.dart](lib/hablador.dart) y [persona.dart](lib/persona.dart)

```dart
mixin Hablador{
  void hablar() {
    throw UnimplementedError("Debe implementar el método en la clase que use este mixin");
  }
}

void hacerHablar(Hablador hablador) {
  hablador.hablar();
```

```dart
class Persona with Hablador {
  String nombre;
  int edad; 

  Persona(this.nombre, this.edad);

  @override
  void hablar() {
    print("Hola, mi nombre es $nombre y tengo $edad años.");
  }
}
}
```

Un detalle de los mixins es que, pese a que no se puedan instanciar, se pueden poner como parámetro en funciones, y aceptarán objetos de clases que utilicen dicho mixin. 

En este ejemplo, podríamos llamar a la función `hacerHablar()` con una instancia de `RobotInteligente` o de `Persona`, y el resultado de la función cambiaría completamente. Esto es un ejemplo de polimorfismo.

## El Antítesis

Por desgracia parece que nuestro robot ha llegado también a las manos equivocadas.

Hechemos un vistazo al archivo [robot_malvado.dart](lib/robot_malvado.dart)

```dart
class RobotMalvado extends Robot {
  int numLaseres;

  RobotMalvado(super.nombre, super.material, this.numLaseres);

  @override
  String objetivo() {
    return "El objetivo de el robot malvado $nombre es destruir a los humanos y conquistar el mundo.";
  }

  void dispararLaser() {
    if (numLaseres > 0) {
      numLaseres--;
      acciones.add("PEW! El robot malvado $nombre dispara un láser. Le quedan $numLaseres láseres.");
    } else {
      acciones.add("El robot malvado $nombre no tiene más láseres. Su conquista ha fracasado.");
    }
  }
}
```
Vamos a entrar en detalle y ver que novedades trae este terrorífico robot:

- Este robot incorpora un nuevo atributo, el número de laseres `numLaseres`. En el constructor podemos apreciar que se delega en el constructor de `Robot` para los atributos heredados, pero no para `numLaseres`.
- El `RobotMalvado` además implementa correctamente el objetivo, y añade la nueva acción `dispararLaser`.

Por suerte, el maquiavelico ser que haya programado este robot no ha añadido la capacidad de recargar el láser, así que esperemos que se quede sin láseres antes de completar su conquista.

# Pruebas

Tendremos 3 ficheros de prueba para estas clases:

- [persona_test.dart](test/persona_test.dart)
- [robot_inteligente_test.dart](test/persona_test.dart)
- [robot_malvado_test.dart](test/robot_malvado_test.dart)

En ellos encontraremos unas sencillas pruebas para verificar la correción de las clases. Se ha añadido el paquete `test`, que permite realizar pruebas unitarias de manera simple y sencilla junto con los paquetes `build_runner` y `build_web_compilers` para la construcción de la web.

Los paquetes vienen incluidos en el fichero de configuración [pubspec.yaml](pubspec.yaml)

Para la ejecución de las pruebas se ha definido un pipeline de Github Actions que permite ejecutarlas automáticamente. Este archivo se encuentra [aquí](../../../.github/workflows/herencia.dart.yml) y se puede ejecutar directamente desde GitHub en el apartado 'Actions'.

# Despliegue

Para el despliegue del programa web de prueba se ha definido un [Dockerfile](./Dockerfile) y un archivo de [Terraform](./main.tf).

Los siguientes pasos permiten desplegar el programa:

1. Crear la imagen ejecutando el siguiente comando, situándonos en el directorio en el que se encuentra el [Dockerfile](./Dockerfile): `docker build -t herencia_img .`
2. Desde el mismo directorio, ejecutar `terraform init` y `terraform apply`. Cuando nos lo pida, escribiremos 'yes'.

Con esto quedará desplegado el programa y podremos acceder a él a través de http://localhost:8080/.

Es importante liberar el puerto tras usar el programa, para ello nos volvemos a situar en el directorio anterior y ejecutamos `terraform destroy`.