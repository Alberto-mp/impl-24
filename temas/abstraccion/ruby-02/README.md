# Abstracción en Ruby

La abstracción es el proceso de identificar y crear una representación simplificada de algo más complejo. Esto es un aspecto vital de la programación y que los programadores aplicamos prácticamente sin darnos cuenta, pues (la mayoría) de los programas surgen con el objetivo de solucionar un problema complejo, cuya solución puede ser más o menos compleja a nivel de programación, pero el usuario final verá un producto sencillo de usar que solucione su problema, o al menos ese es nuestro objetivo.

Veremos como en este ejemplo tan simple programado en Ruby, ya estamos aplicando el concepto de abstracción simplemente usando el concepto de clases y módulos o mixins y definiendo métodos en ellos con diferentes modificadores de acceso.

# Ejemplo: El Robot que habla

En este primer ejemplo, trabajaremos con 2 archivos

- [robot.rb](src/robot.rb)
- [hablador.rb](src/hablador.rb)

## La capacidad de hablar

Nos centraremos en el el archivo [hablador.rb](src/hablador.rb).

```ruby
module Hablador
  def hablar
    raise NotImplementedError, "Debe implementarse en una clase"
  end

  def saludar
    "HOLA!"
  end
end
```

En este pequeño archivo, estamos definiendo un mixin, es decir estamos definiendo la capacidad de "algo" que todavía no conocemos a hablar. Concretamente un hablador será capaz de `hablar` y de `saludar`. En nuestro pequeño ejemplo, asumimos que todo "hablador" saludará con un simple "HOLA!", pero dejamos la capaz de hablar para implementación de aquel que vaya a usar nuestro mixin. 

Aquí encontramos ya nuestro primer concepto de abstracción y es que cualquier persona que desee incluir este mixin en su clase, no necesita saber la implementación del método `saludar`, simplemente tendrá que llamarlo y cualquier objeto de esa clase será capaz de saludar.

## El Robot

He aquí nuestra clase [robot.rb](src/robot.rb)

```ruby
class Robot
  include Hablador

  attr_accessor :nombre, :num_laseres
  attr_reader :material

  def initialize(nombre, material, num_laseres)
    @nombre = nombre
    @material = material
    @num_laseres = num_laseres
  end

  def hablar
    "HOLA. SOY. UN. ROBOT. MI. NOMBRE. ES. #{@nombre} Y. ESTOY. HECHO. DE. #{@material}."
  end

  def disparar_laser
    if @num_laseres > 0
      @num_laseres -= 1
      "PEW! Ahora tengo #{@num_laseres} láseres restantes."
    else
      recargar_laseres
      "NO. TENGO. MAS. LASERES. #{@recarga_mensaje}"
    end
  end

  private

  def recargar_laseres
    laseres = rand(1..10)
    @num_laseres += laseres
    @recarga_mensaje = "RECARGANDO. LASERES. RECARGA. COMPLETA. AHORA. TENGO. #{@num_laseres} LASERES."
  end
end
```
Como programador de esta clase, estoy asumiendo dos roles, por un lado, soy usuario de la clase `hablador`, eso me permite brindarle a mi robot la capacidad de `hablar` y `saludar`, la primera la implemento yo, haciendo que el robot diga su numbre y de que material está hecho, sin embargo el método saludar como comentamos anteriormente, viene implementado y yo no tengo por qué saber como, simplemente sé que cuando mande a mi robot a `saludar`, este lo hará perfectamente.

Por otro lado, como programador, estoy ofreciendo a otras personas la capacidad de crear sus propios robots. Veamos en detalle que hace nuestra clase `robot`.

### Métodos de acceso

`attr_accessor :nombre, :num_laseres`: Define métodos getter y setter para las variables de instancia `@nombre` y `@num_laseres`. Esto permite leer y modificar estos atributos desde fuera de la clase.

`attr_reader :material`: Define solo un método getter para la variable de instancia `@material`, lo que permite leer su valor desde fuera de la clase, pero no modificarlo.

Adicionalmente podríamos haber definido un método que fuese únicamente setter con `attr_writer`.

### Constructor

Este es el constructor de la clase, que es llamado cuando se crea un nuevo Robot. Inicializa las variables de instancia `@nombre`, `@material`, y `@num_laseres` con los valores proporcionados.

### Métodos

`hablar`, ya hemos hablado antes de él, es la implementación del método `hablar` definido en el mixin `Hablador ` 

`disparar_laser` dispara un láser, si es que el Robot los tiene, y si no llamará al método `recargar_laseres` para recargarlos

`recargar_laser` método privado que permite recargar un número aleatorio de láseres.

Con estos simples métodos, estamos aplicando el concepto de abstracción de dos maneras distintas, por un lado el usuario no sabe como están implementados, simplemente llamará a `hablar` y `disparar_laser` y cumplirán su función, pero, a mayores, el método `disparar_laser` llamará a `recargar_laser` si no le quedan, y lo hará sin que el usuario sepa ni que existe tal método, ese es el poder del modificador de acceso `private` y del encapsulamiento.

# Ejemplo: Las figuras

Con el ejemplo anterior vimos los modificadores de acceso `private` y `public` (este último no explicitamente, pero por defecto en Ruby todo método es público).

En este ejemplo, veremos muy por encima la herencia, qué permite la reutilización de código y el otro modificador de acceso que no habíamos visto, `protected`

Este fragmento de código se puede encontrar en [figura.rb](src/figura.rb):

```ruby
class Figura
    def area
      calcular_area
    end
  
    protected
  
    def calcular_area
      # Método protegido que se espera que las subclases implementen
      raise NotImplementedError, "El método 'calcular_area' debe ser implementado por las subclases."
    end
  end
  
  class Rectangulo < Figura
    def initialize(base, altura)
      @base = base
      @altura = altura
    end
  
    protected
  
    def calcular_area
      @base * @altura
    end
  end
  
  class Triangulo < Figura
    def initialize(base, altura)
      @base = base
      @altura = altura
    end
  
    protected
  
    def calcular_area
      @base * @altura / 2.0
    end
  end
```

Como podemos ver, con el concepto de herencia somos capaces de reutilizar el código de la súperclase en las clases que heredan de ella. Además, estamos usando el modificador de acceso `protected`, que al igual que `private` impide el acceso a sus miembros desde fuera de la clase, pero sí lo permite para las clases hijas, no como `private`, con el objetivo de que cada clase implemente la forma de calcular el área de la fígura específica, sin que se sepa desde fuera.

# Pruebas

Tendremos 3 ficheros de prueba para estas clases:

- [robot_test.rb](test/robot_test.rb)
- [hablador_test.rb](test/hablador_test.rb)
- [figura_test.rb](test/figura_test.rb)

En ellos encontraremos unas sencillas pruebas para verificar la correción de las clases. Se ha utilizado la gema `minitest`, que permite realizar pruebas unitarias de manera simple y sencilla, así como las gemas `sinatra` y `rackup` para la aplicación web.

Estas gemas vienen incluidas en el fichero de configuración [Gemfile](Gemfile).

La ejecución de las pruebas se puede realizar ejecutando los propios archivos de prueba con ruby, sin embargo se ha definido un pipeline de Github Actions que permite ejecutarlas automáticamente. Este archivo se encuentra [aquí](../../../.github/workflows/abstraccion.ruby-02.yml) y se puede ejecutar directamente desde GitHub en el apartado 'Actions'.

# Despliegue

Para el despliegue del programa web de prueba se ha definido un [Dockerfile](./Dockerfile) y un archivo de [Terraform](./main.tf).

Los siguientes pasos permiten desplegar el programa:

1. Crear la imagen ejecutando el siguiente comando, situándonos en el directorio en el que se encuentra el [Dockerfile](./Dockerfile): `docker build -t abstraccion_img .`
2. Desde el mismo directorio, ejecutar `terraform init` y `terraform apply`. Cuando nos lo pida, escribiremos 'yes'.

Con esto quedará desplegado el programa y podremos acceder a él a través de http://localhost:4567/.

Es importante liberar el puerto tras usar el programa, para ello nos volvemos a situar en el directorio anterior y ejecutamos `terraform destroy`.
