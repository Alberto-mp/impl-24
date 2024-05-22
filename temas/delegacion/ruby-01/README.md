# Delegación en Ruby

El concepto de delegación consiste en darle a un objeto la capacidad de delegar la responsabilidad de realizar una tarea a otro objeto distinto. Este concepto es muy útil, pues permite la separación de responsabilidades, lo que permite reutilizar el código y así mejorar tanto la modularidad como la flexibilidad de nuestro programa.

Vamos a ver un par de ejemplos muy sencillos de como aplicar la delegación en Ruby.

# Ejemplo: El Clan

Nos situamos en el archivo [Clan.rb](src/Clan.rb): 

```ruby
module Tropa
    def initialize(nombre)
      @nombre = nombre 
    end
  
    def atacar
      "#{self.class} #{@nombre} avanza y ataca al enemigo"
    end
  
    def defender
      "#{self.class} #{@nombre} se retira a defender la base"
    end
  
    def usar_habilidad
      raise NotImplementedError, 'Debe implementarse en una clase'
    end
  end
  
  class Clan
    include Enumerable
  
    attr_accessor :tropas_ataque, :tropas_defensa
  
    def initialize
      @tropas_ataque = []
      @tropas_defensa = []
    end
  
    # Métodos para agregar y remover tropas
    def add_tropa_ataque(tropa)
      @tropas_ataque << tropa
    end
  
    def add_tropa_defensa(tropa)
      @tropas_defensa << tropa
    end
  
    def remove_tropa_ataque(tropa)
      @tropas_ataque.delete(tropa)
    end
  
    def remove_tropa_defensa(tropa)
      @tropas_defensa.delete(tropa)
    end
  
    # Método para iterar sobre las tropas
    def each(&block)
      @tropas_ataque.each(&block)
      @tropas_defensa.each(&block)
    end
  
    def atacar
      @tropas_ataque.map do |tropa|
        [tropa.atacar, tropa.usar_habilidad]
      end.flatten
    end
  
    def defender
      @tropas_defensa.map do |tropa|
        [tropa.defender, tropa.usar_habilidad]
      end.flatten
    end
  end
  
  class Bruja
    include Tropa
    def initialize(nombre)
      @nombre = nombre
    end
  
    # Implementación del método usar_habilidad del módulo Tropa
    def usar_habilidad
      "#{self.class} #{@nombre} lanza una bola de fuego al enemigo"
    end
  
    def to_s
      @nombre
    end
  end
  
  class Arquera
    include Tropa
    def initialize(nombre)
      @nombre = nombre
    end
  
    # Implementación del método usar_habilidad del módulo Tropa
    def usar_habilidad
      "#{self.class} #{@nombre} dispara una flecha envenenada al enemigo"
    end
  
    def to_s
      @nombre
    end
  end
  
  class Gigante
    include Tropa
    def initialize(nombre)
      @nombre = nombre
    end
  
    # Implementación del método usar_habilidad del módulo Tropa
    def usar_habilidad
      "#{self.class} #{@nombre} golpea al enemigo con su maza"
    end
  
    def to_s
      @nombre
    end
  end
```

En este ejemplo, se puede apreciar un uso MASIVO de la delegación, veámoslo en profundidad:

La clase `Clan` tiene dos atributos, `@tropas_ataque` y `@tropas_defensa`. Estos atributos se encargarán de almacenar las tropas que estén asignadas al ataque y a la defensa respectivamente mediante los métodos `add_tropa_ataque` y `add_tropa_defensa`, así como eliminarlos con los métodos `remove_tropa_ataque` y  `remove_tropa_defensa`

Hasta aquí, no hemos delegado nada, pero llegamos al método `each`, que nos permite definir la manera de enumerar de manera muy sencilla delegando en el módulo `Enumerable` que hemos incluido en nuestra clase.

No contentos con eso, nos encontramos con los métodos `atacar` y `defender` que no hacen más que iterar sobre las listas y delegando el ataque y la defensa a las propias tropas.

Sin embargo, si indagamos un poco más en el código, descubrimos que las tropas son, en realidad un módulo o mixin llamado `Tropa` y que son las clases `Bruja`, `Arquera` y `Gigante` las que delegan en `Tropa` el ataque y la defensa y que además estas clases implementan el método `usar_habilidad` que no está definido en `Tropa`, luego el mixin delega la implementación de ese método en las clases que lo vayan a incluir, aunque esto último no es un concepto de delegación, pero es una pequeña peculiaridad del código y da la sensación de crear una especie de 'delegación bidireccional'.

Este es el ejemplo más clásico de delegación, mediante el uso de módulos que nos ofrece el propio lenguaje Ruby. Sin embargo, hay otras muchas formas de delegar, veamos otro ejemplo.

# Ejemplo: La Cocina

En este caso, le echaremos un vistazo al archivo [Cocina.rb](src/Cocina.rb)

```ruby
class Horno
    def calentar(comida)
        "Calentando #{comida}..."
    end
end

class Cocina
    delegate :calentar, to: :@horno

    def initialize(horno)
        @horno = horno
    end

    def cocinar(comida)
        "Preparando ingredientes... " + calentar(comida) + " El plato está listo."
    end
end
```

Este ejemplo, mucho más simple que el anterior, simplemente define dos clases, `Horno` y `Cocina`, la primera se encargará de calentar la comida y la segunda preparará la comida, delegando en el `Horno` para calentarla.

Si nos fijamos ahora no estamos usando módulos, sino `delegate`, una útilidad que viene incluida en la gema `ActiveSuport`, que nos ofrece una sintaxis muy intuitiva para delegar métodos a objetos asociados.

# Pruebas

Tendremos 2 ficheros de prueba para estas clases:

- [Clan_test.rb](test/Clan_test.rb)
- [Cocina_test.rb](test/Cocina_test.rb)

En ellos encontraremos unas sencillas pruebas para verificar la correción de ambas clases. Se ha utilizado la gema `minitest`, que permite realizar pruebas unitarias de manera simple y sencilla, junto con la gema `ActiveSupport` usada en el ejemplo de la cocina para la delegación así como las gemas `sinatra` y `rackup` para la aplicación web.

Estas gemas vienen incluidas en el fichero de configuración [Gemfile](Gemfile).

La ejecución de las pruebas se puede realizar ejecutando los propios archivos de prueba con ruby, sin embargo se ha definido un pipeline de Github Actions que permite ejecutarlas automáticamente. Este archivo se encuentra [aquí](../../../.github/workflows/delegacion.ruby-01.yml) y se puede ejecutar directamente desde GitHub en el apartado 'Actions'.

# Despliegue

Para el despliegue del programa web de prueba se ha definido un [Dockerfile](./Dockerfile) y un archivo de [Terraform](./main.tf).

Los siguientes pasos permiten desplegar el programa:

1. Crear la imagen ejecutando el siguiente comando, situándonos en el directorio en el que se encuentra el [Dockerfile](./Dockerfile): `docker build -t delegacion_img .`
2. Desde el mismo directorio, ejecutar `terraform init` y `terraform apply`. Cuando nos lo pida, escribiremos 'yes'.

Con esto quedará desplegado el programa y podremos acceder a él a través de http://localhost:4567/.

Es importante liberar el puerto tras usar el programa, para ello nos volvemos a situar en el directorio anterior y ejecutamos `terraform destroy`.

