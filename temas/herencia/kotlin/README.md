# Herencia Kotlin 

Creado por Rub�n P�rez Mercado

## Compilaci�n
```
kotlinc Herencia.kt -include-runtime -d Herencia.jar
```
## Ejecuci�n
```
java -jar Herencia.jar
```
Si no se dispone del compilador de Kotlin, se puede probar el c�digo en el <a href="https://play.kotlinlang.org/">Playground de Kotlin</a>
## Explicaci�n
### Conceptos previos
Antes de entrar en conceptos de herencia, es importante saber c�mo funcionan los constructores en Kotlin. Tenemos constructores primarios y secundarios.
- Primarios: Si existe, s�lo hay uno. Va despu�s del nombre de la clase.
```
class Persona constructor(Nombre: String) { /*...*/ }
class Persona (Nombre: String) { /*...*/ } //Si no se define visibilidad o anotaciones, la palabra constructor puede omitirse
```
- Secundarios: Si existen, pueden haber varios. Se definen dentro de la propia clase. Siempre delega del constructor primario (incluso si no se ha definido).
```
class Persona (val mascotas: MutableList<Mascota> = mutableListOf()) // Ctor. Primario

class Mascota {  // Ctor. Primario no definido
    constructor(owner: Persona) { // Ctor. Secundario
        owner.mascotas.add(this) // A�ade la mascota a la lista de su due�o
    }
}
```

Por otro lado, en Kotlin tenemos propiedades, no campos. La diferencia est� en que cuando declaramos propiedades estamos declarando el campo, el getter y el setter (en caso de ser `var`, puesto que una propiedad declarada con `val` s�lo tendr� getter). Estos m�todos pueden ser modificados, pero eso queda fuera de esta explicaci�n.

Con estos conceptos, tenemos suficiente para entender lo que ocurre en los pr�ximos ejemplos. Destacar que no estamos utilizando los valores pasados al constructor, ya que queremos ver qu� sucede en lo relacionado a la herencia.

### Aspectos b�sicos

Al igual que sucede en Java, por defecto todas las clases heredan de una superclase com�n. En Java tenemos la clase `Object`, y en Kotlin tenemos la clase `Any`. Esta clase contiene �nicamente tres m�todos, adem�s del constructor por defecto: equals, hashCode y toString (si bien es cierto que existen m�s m�todos y propiedades que extienden a esta clase. M�s adelante veremos cu�l es el mecanismo que utiliza).

Una de las diferencias que tiene con respecto a Java es que por defecto las clases son finales, es decir, no se pueden generar subclases de ellas. Para habilitar la herencia en una clase, debemos escribir la palabra `open`. Esto permite que se vea m�s f�cilmente cuando estamos empleando herencia, y con qu� clases. Veamos un ejemplo con y sin `open`, con una clase base y otra derivada:
```
class Animal // No permitir� que otras clases hereden de ella
class Perro : Animal() // compilation error: this type is final, so it cannot be inherited from class Perro : Animal()
```

```
open class Animal //Ahora s� que permite la herencia
class Perro : Animal()
```
Como podemos ver, debemos utilizar `:` en la clase derivada para poder heredar de la clase base (declarada con open). Son importantes los par�ntesis a la hora de declarar esa herencia, pues de no ponerlos tambi�n dar� error. Si queremos pasar par�metros al constructor primario, se har� de esta forma:

```
open class Animal(nombre: String)
class Perro(nombre: String, raza: String) : Animal(nombre)
```
Si la clase derivada no tiene constructor primario, se debe emplear la palabra `super` en todos los constructores secundarios para referenciar a la clase base.

```
open class Animal (s: String){
    constructor(age: Int) : this("Animal Desconocido")
}
class Perro : Animal {
    constructor(s: String) : super(s)
    constructor(age: Int) : super(age)
}
```
Insistimos en la idea de que estos c�digos no tienen ning�n prop�sito m�s que ense�ar los mecanismos de la herencia. En el ejemplo de las carpetas existen ejemplos m�s elaborados.

### Overriding

Kotlin permite sobreescribir m�todos y propiedades.

```
open class Base{
    ...
    open val i = 0
    open fun metodo1() {/*...*/}
    ...
}

open class Derivada : Base(){
    ...
    override val i = 4
    override fun metodo1() {/*...*/}
    ...
}
```
Como podemos ver, es necesaria la palabra open en los m�todos y propiedades a sobreescribir. Destacar que `val` equival�a a un `const` de C++. Podr�amos sobreescribirlo por un variable (`var`) y funcionar�a. Como curiosidad, el proceso inverso no compilar�a, pues impl�citamente se declaran getters y setters por cada variable, y al convertir un `var` en `val` estar�amos "perdiendo" el setter.

Si quit�semos la palabra `override` el compilador nos indicar�a lo siguiente: `'i' hides member of supertype 'Base' and needs 'override' modifier`.

Podremos hacer referencia a m�todos de la superclase usando la palabra `super`
```
open class Rectangulo {
    open fun draw() { println("Pinto mi rectangulo") }
    val borderColor: String get() = "rojo"
}

class RectanguloRelleno : Rectangulo() {
    override fun draw() {
        super.draw()
        println("Relleno el rectangulo")
    }

    val fillColor: String get() = super.borderColor
}
```
### Herencia m�ltiple
Kotlin no permite herencia m�ltiple. Es decir, no podemos realizar algo as�:
```
open class A
open class B

class C : A(), B()
// Error: Only one class may appear in a supertype list
```

### Clases Abstractas

Al igual que en otros lenguajes, tambi�n tenemos clases abstractas. En este caso, no es necesaria la palabra `open`:
```
abstract class Animal {
    abstract fun comer()
}

class Leon : Animal() {
    override fun comer() {
        /* Come carne */
    }
}
```
### Interfaces
Tambi�n se permiten utilizar interfaces. Permiten opcionalmente implementar los m�todos. Se permiten declarar propiedades abstractas, o proporcionar implementaciones de los m�todos accesores.
```
interface MiInterfaz {
    val prop: Int // propiedad abstracta
    fun f1()
    fun f2() {
      // opcionalmente puede haber implementaci�n
    }
}

class derivada: miInterfaz {
    override val prop: Int = 5
    override fun f2() {
        // implementaci�n sobreescrita
    }
}
```
### Extensiones
En Kotlin podemos extender clases a las que no tenemos acceso de una forma muy sencilla. Por ejemplo, vamos a a�adir el m�todo `swap` a la clase `MutableList<T>`
```
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' es el objeto lista
    this[index1] = this[index2]
    this[index2] = tmp
}
```
Tambi�n podemos a�adir propiedades a las clases:
```
val <T> List<T>.lastIndex: Int
    get() = size - 1 
```

### C�digo
Hemos creado una versi�n de Kotlin de "Aventura", el ejemplo visto en clase. Hemos utilizado los siguientes conceptos relacionados:

- Interfaces: Anteriormente en Kotlin, exist�an los `traits`, pero en las �ltimas versiones han sido eliminados, teniendo que utilizar interfaces. No hemos creado ninguna implementaci�n por defecto en las funciones declaradas e las interfaces, pero s� hemos creado dos variables abstractas en sendas clases `sabeLuchar` y `sabeVolar`, que en Heroe ser�n instanciadas.

- Funci�n de extensi�n: Hemos creado una funci�n de extensi�n para la clase `Heroe` llamada `presentacion`. S�lo funcionar� si las propiedades que utilizamos son visibles, podemos probar a descomentar el `private` en la clase `Heroe` para comprobar que no funciona.

- Llamada a una funci�n de la clase base desde el m�todo de una clase derivada: Dentro de la funci�n `Luchar` de Heroe, estamos llamando a la funci�n hom�nima de la clase `personajeDeAccion` utilizando la palabra `super`.

- "Static" en Kotlin: Aunque no tenga tanta relaci�n con respecto a la herencia, hemos creado un `companion object` en la clase `Aventura`, que es la manera de declarar m�todos y propiedades como las est�ticas de Java. Para acceder a los elementos en �l, podemos ver c�mo hemos llamado a las funciones en el `main`. En cuanto a compatibilidad con Java, existe un peque�o problema cuando se llama a un `companion object` desde Java, se puede hacer pero existe una diferencia. Esta diferencia la resolvemos con anotaciones, teniendo un ejemplo en la carpeta Anotaciones / Kotlin / Interoperabilidad.

En la funci�n principal, llamamos a las funciones creadas, algunas de ellas usan las propiedades `fuerza`y `modo`, que eran las propiedades abstractas de las interfaces.