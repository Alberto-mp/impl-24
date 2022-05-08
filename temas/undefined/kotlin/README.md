# Null Safety ('Undefined')

Creado por Rub�n P�rez Mercado

## Compilaci�n
```
kotlinc NullSafety.kt -include-runtime -d NullSafety.jar
```
## Ejecuci�n
```
java -jar NullSafety.jar
```
Si no se dispone del compilador de Kotlin, se puede probar el c�digo en el <a href="https://play.kotlinlang.org/">Playground de Kotlin</a>
## Explicaci�n

Uno de los objetivos de Kotlin, al igual que el de muchos de los nuevos lenguajes, es eliminar los problemas relacionados con `null`. Lenguajes m�s antiguos como Java o C++ han incorporado mecanismos para proporcionar lo que de forma nativa ya proporcionan lenguajes como Kotlin.

En Java, es com�n encontrarnos con excepciones de tipo `NullPointerException` (`NPE`). En Kotlin, esto se da en circunstancias muy concretas:
- Se lanza expl�citamente una excepci�n de este tipo con `throw`.
- Se usa el operador `!!` descrito m�s adelante.
- Si existe inconsistencia en los datos (en constructores).
- Interoperabilidad con Java (como veremos ahora, Kotlin aborda de una manera especial los `null`).

### Nullable vs non-null

Kotlin realiza una distinci�n entre referencias que permiten el valor nulo (nullable) y referencias que no lo permiten (non-null). Veamos el siguiente ejemplo:
```
fun main(){
    var a = "Hola Mundo"
    a = null
    // Error: Null can not be a value of a non-null type String
}
```
En la l�nea 2 el compilador infiere la variable `a` es de tipo `String`, que es de tipo non-null. Al igualar la variable a `null`, da el error del comentario. Lo mismo pasar�a si expl�citamente declaramos `a` como `String`.

Para declarar una variable de tipo nullable, utilizamos el car�cter `?` detr�s del tipo de la variable:
```
fun main(){
    var b: String? = "abc" //Ahora s� puede valer null
    b = null // OK
    print(b) //imprime 'null'
}
```
En el caso de acceder a m�todos, no tendr�amos problemas con tipos non-null, puesto que nunca ser�n nulos. Con tipos nullable, en cambio, el compilador nos exige que utilicemos el operador `?.`, independientemente de si la variable vale `null` o no.
```
val l = a.length //OK
val l2 = b.length // Compilation Error: variable 'b' can be null
val l3 = b?.length // l3 = 'null'
```
En el ejemplo anterior, `l2` dar� error por lo que hemos descrito anteriormente, `l` ser� de tipo `Int`, y `l3` ser� de tipo `Int?`. Esta �ltima variable valdr� `null` en caso de serlo `b` tambi�n, o el resultado de aplicar el m�todo (en este caso, `length`). Como curiosidad, si aplicamos el operador `?.` a una variable de tipo non-null, nos saltar� un Warning, pero compilar�. Como es de esperar, se pueden encadenar varios operadores `?.` con llamadas a m�todos o acceso a propiedades, y si en alg�n punto se devuelve valor nulo, el resultado que devolver� ser� `null`
```
persona1?.nombre?.toUpperCase()
```
### Contenedores con valores nulos
Si tenemos un contenedor con valores nulos, y queremos iterar sobre �l sin tener en cuenta los valores nulos, podemos utilizar el operador `?.` junto con `let`.
```
val listWithNulls: List<String?> = listOf("Kotlin", null, "language")
for (item in listWithNulls) {
    item?.let { println(it) } 
    /* Ignora los null e imprime:
    Kotlin
    language
    */
}
```
Si nos interesase eliminar esos valores nulos previamente, tenemos el m�todo `filterNotNull()`, y as� podr�amos convertir el contenedor de tipo nullable a otro de tipo non-null:
```
val listWithNulls: List<String?> = listOf("Kotlin", null, "language")
List<String> listWithoutNulls = listWithNulls.filterNotNull()
```
### Operador Elvis
En Kotlin, no existe el operador ternario `condition ? case_true : case_false`. No obstante, tenemos un operador llamado Elvis operator (`?:`), mediante el cual podemos establecer un valor predeterminado a una variable si es nula, es decir, ser�a como un operador ternario pero con la condici�n `b != null`. Veamos un ejemplo:

Imaginemos que tenemos algo parecido a esto:
```
val l: Int = if (b != null) b.length else -1
```
Comprobamos primero que el valor de la variable `b` para asignarle un valor (en este caso, la longitud de la cadena), y si es nula la variable `b` asignamos a `l` el valor `-1`.
Con el Elvis operator, podr�amos hacerlo de la siguiente manera:
```
val l = b?.length ?: -1
```
Recordamos que al ser `b` de tipo nullable, es necesario el uso del operador `?.` cuando usamos sus m�todos. De esta manera, podemos acortar la expresi�n con un `if-else` de arriba, y convertirla en algo m�s corto.
Destacar que podemos utilizar palabras como return o throw en la parte de la derecha del Elvis operator (la parte del `else`), por si tenemos que controlar que el valor esperado no sea `null`.
### Operador !!
Como hemos dicho antes, existe un operador mediante el cual podemos lanzar excepci�n de tipo `NPE`. Lo que hace este operador es llamar al m�todo como si la variable fuese de tipo non-null, y si el valor de esa variable era `null`, lanza esa excepci�n.
```
val l = b!!.length // Si b == null, lanza NPE
```
### Casts Seguros
En algunos casos, podemos realizar un cast y que lance un `ClassCastException` si el objeto no es del mismo tipo que el de destino. Para evitar esto existe el operador `as?`, que devuelve null en caso de no ser posible la conversi�n.
```
val contador: Int? = a as? Int //Si 'a' no puede convertirse en Int, contador = null
```

### C�digo

Para este bloque hemos implementado el ejemplo `MobileTester`.
Destacamos, antes de hablar sobre los aspectos del bloque, la particularidad de Kotlin con las propiedades, que se componen de getter, setter y el propio atributo, lo que hace que las clases auxiliares queden muy escuetas. En este <a href="https://www.geeksforgeeks.org/kotlin-setters-and-getters/">enlace</a> se explica con detalle esta caracter�stica.

En el ejemplo hemos hecho uso de:

- Nullables y non-nulls: Tenemos propiedades de ambos tipos en el c�digo. En el `main` tenemos una instrucci�n comentada para ver el fallo de compilaci�n en caso de asignar un `null` a una propiedad non-null.
- Concatenaci�n del operador `?.` y del Elvis operator `?:` dentro de la funci�n `getMobileScreenWidth`, lo que nos permite no lanzar un NPE y devolver siempre algo distinto a `null`.
- Concatenaci�n del operador `?.` sin usar el Elvis operator `?:` dentro de la funci�n `getMobileScreenHeight`, lo que nos permite no lanzar un `NPE` pero podemos devolver `null` en caso de que alguno de los operadores `?.` devuelva `null`. En el ejemplo, hemos creado un `Mobile` con `DispalyFeatures` nulo, pero podemos ver que no se lanzar� en ning�n momento un `NPE`, aunque imprimimos `null` en la ejecuci�n.
- Operador `!!` en `getMobileScreenHeight`, con el que podemos lanzar un `NPE` en caso de valer `null` la propiedad en ese punto. Para probar esto, se necesita comentar el return anterior y descomentar el que viene debajo del comentario en la funci�n anteriormente dicha.