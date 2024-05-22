# Anotaciones (Atributos en C#)

## Introducción
Este repositorio demuestra el uso efectivo de atributos en C#, una funcionalidad poderosa que permite agregar metadatos a las clases y sus miembros para controlar su comportamiento durante la ejecución. El proyecto explora el diseño y aplicación de atributos personalizados para validar condiciones específicas y controlar la serialización de objetos a JSON, proporcionando ejemplos prácticos y útiles de estas técnicas.

## Estructura de Directorio
La estructura del directorio de este proyecto está cuidadosamente organizada para diferenciar claramente entre el código principal y las pruebas, facilitando así su comprensión, navegación y mantenimiento. A continuación, se describe cómo están organizados los directorios y archivos principales:
- **/README.md:** Este archivo actúa como la principal fuente de información sobre el proyecto, proporcionando una visión general, instrucciones detalladas de instalación y uso, y documentación general que ayuda a los nuevos usuarios a entender y comenzar a trabajar con el proyecto.
- **/Csharp_Anotaciones:** Directorio raíz que contiene todos los archivos de código fuente relacionados con la implementación de atributos en C#. Aquí se encuentran los ejemplos de cómo se definen y utilizan los atributos personalizados para la validación de datos y la configuración de la serialización JSON:
    - **Program.cs:** Archivo principal que contiene la definición de los atributos personalizados y ejemplos de su aplicación práctica. Se incluyen clases como `Persona` y `Producto` que demuestran el uso de estos atributos para imponer reglas de validación y controlar el comportamiento de la serialización.
- **/Tests_Anotaciones:** Este directorio está dedicado exclusivamente a albergar los archivos de pruebas unitarias para los ejemplos implementados. Está diseñado para asegurar que todas las funcionalidades desarrolladas funcionan como se espera:
    - **UnitTest1.cs:** Archivo que contiene pruebas unitarias diseñadas para validar la correcta funcionalidad de los atributos personalizados y la lógica de serialización. Utiliza un framework de pruebas como NUnit o xUnit para definir y ejecutar estas pruebas.

Esta estructura de directorio está pensada no solo para facilitar el desarrollo y la comprensión del código sino también para apoyar prácticas de desarrollo robustas como la integración y el despliegue continuos, asegurando que el código sea fácilmente verificable y mantenible.

## Conceptos Básicos
En C#, los atributos son construcciones que permiten añadir información declarativa a varios elementos del código, como clases, métodos, propiedades y más. Estos metadatos adicionales pueden ser utilizados en tiempo de compilación o ejecución para alterar el comportamiento de aplicaciones. A continuación, se detallan conceptos fundamentales asociados con el uso de atributos en C#.
### 1.Definición y Uso de Atributos
Los atributos en C# se definen como clases que heredan del tipo `System.Attribute`. Pueden contener propiedades, campos y métodos que permiten configurar su comportamiento. Al definir un atributo, puedes especificar en qué elementos del código se puede aplicar mediante el uso de la propiedad `AttributeTargets`, y si se pueden aplicar múltiples instancias del mismo atributo a un solo elemento.
```csharp
[AttributeUsage(AttributeTargets.Class | AttributeTargets.Method, AllowMultiple = true)]
public class ExampleAttribute : Attribute
{
    public string Description { get; set; }
    public ExampleAttribute(string description)
    {
        Description = description;
    }
}
```
### 2.Aplicación de Atributos
Los atributos se aplican colocándolos entre corchetes `[ ]` justo antes del elemento del código al que se refieren. Esto puede incluir clases, métodos, propiedades, ensamblados, etc. Los atributos pueden tomar argumentos en su constructor, así como valores para las propiedades públicas.
```csharp
[Example("This is a class attribute.")]
public class MyClass
{
    [Example("This is a method attribute.")]
    public void MyMethod() { }
}
```
### 3.Atributos Intrínsecos y Personalizados
C# proporciona varios atributos predefinidos que son parte del .NET Framework, como `[Obsolete]`, `[Serializable]`, `[DllImport]`, etc., que tienen funcionalidades específicas predefinidas. Además, los desarrolladores pueden crear sus propios atributos personalizados para satisfacer necesidades específicas que no están cubiertas por los atributos estándar.
### 4.Reflexión y Atributos
La reflexión es una capacidad poderosa en C# que permite inspeccionar la estructura de un programa en tiempo de ejecución. Los atributos juegan un papel crucial aquí, ya que la reflexión puede ser utilizada para descubrir qué atributos están aplicados a diferentes elementos del código y actuar en consecuencia.
```csharp
var attributes = typeof(MyClass).GetCustomAttributes(false);
foreach (Attribute attr in attributes)
{
    Console.WriteLine(attr);
}
```
### 5.Atributos y Serialización
Los atributos son especialmente útiles en la serialización y deserialización de objetos. Permite controlar cómo se serializan las propiedades de los objetos, por ejemplo, incluyendo o excluyendo propiedades de la serialización JSON, o cambiando el nombre de las propiedades en el JSON resultante.
```csharp
public class Product
{
    [JsonPropertyName("id")]
    public int Id { get; set; }

    [JsonIgnore]
    public string InternalData { get; set; }
}
```
### 6.Atributos y Validación
Los atributos también se utilizan ampliamente para la validación de datos. Por ejemplo, en aplicaciones web, atributos como `[Required]`, `[Range]`, `[StringLength]`, y otros, aseguran que los datos recibidos cumplen con los criterios antes de procesarlos.

## Código de Ejemplo
En este apartado se describen detalladamente los ejemplos de código incluidos en el proyecto, que demuestran el uso efectivo de atributos personalizados y control de serialización en C#. Los ejemplos proporcionados ilustran cómo implementar y aplicar atributos para validar datos y personalizar la serialización de objetos a JSON, utilizando funcionalidades avanzadas de C# para mejorar la calidad y el mantenimiento del código.
```csharp
using System;
using System.Text.Json;
using System.Text.Json.Serialization;

// Definición del atributo personalizado
[AttributeUsage(AttributeTargets.Property, AllowMultiple = false)]
public class RangoEdadAttribute : Attribute
{
    public int Min { get; } // Valor mínimo permitido para la edad
    public int Max { get; } // Valor máximo permitido para la edad

    // Constructor del atributo personalizado
    public RangoEdadAttribute(int min, int max)
    {
        Min = min;
        Max = max;
    }
}

// Clase Persona para usar el atributo personalizado
public class Persona
{
    [RangoEdad(0, 130)] // Aplicación del atributo personalizado a la propiedad Edad
    public int Edad { get; set; } // Propiedad para almacenar la edad de la persona
    public string Nombre { get; set; } = string.Empty; // Propiedad para almacenar el nombre de la persona, inicializada para evitar null

    // Método para validar la edad de la persona según el atributo personalizado
    public void Validar()
    {
        var properties = GetType().GetProperties(); // Obtiene todas las propiedades de la clase
        foreach (var property in properties)
        {
            var rangoEdadAttr = Attribute.GetCustomAttribute(property, typeof(RangoEdadAttribute)) as RangoEdadAttribute; // Obtiene el atributo personalizado de la propiedad
            if (rangoEdadAttr != null) // Si la propiedad tiene el atributo personalizado
            {
                int valor = (int)property.GetValue(this)!; // Obtiene el valor de la propiedad Edad, asumiendo que no es null
                if (valor < rangoEdadAttr.Min || valor > rangoEdadAttr.Max) // Comprueba si el valor está dentro del rango permitido
                {
                    throw new ArgumentOutOfRangeException(property.Name, $"El valor de {property.Name} debe estar entre {rangoEdadAttr.Min} y {rangoEdadAttr.Max}."); // Lanza una excepción si el valor está fuera del rango
                }
            }
        }
    }
}

// Clase Producto para demostrar el control de serialización
public class Producto
{
    [JsonPropertyName("id_producto")] // Personaliza el nombre de la propiedad en el JSON resultante
    public int Id { get; set; } // Propiedad para el ID del producto

    [JsonIgnore] // Ignora esta propiedad durante la serialización JSON
    public string Proveedor { get; set; } = string.Empty; // Propiedad para el proveedor del producto, inicializada para evitar null

    [JsonPropertyName("nombre")] // Personaliza el nombre de la propiedad en el JSON resultante
    public string Nombre { get; set; } = string.Empty; // Propiedad para el nombre del producto, inicializada para evitar null

    [JsonPropertyName("precio")] // Personaliza el nombre de la propiedad en el JSON resultante
    public decimal Precio { get; set; } // Propiedad para el precio del producto
}

// Programa principal
class Program
{
    static void Main(string[] args)
    {
        // Ejemplo 1: Validación Correcta
        Console.WriteLine("Ejemplo 1: Validación Correcta");
        var personaValida = new Persona { Nombre = "Juan", Edad = 31 };
        try
        {
            personaValida.Validar();
            Console.WriteLine("Validación exitosa para Juan.");
        }
        catch (ArgumentOutOfRangeException ex)
        {
            Console.WriteLine(ex.Message);
        }

        Console.WriteLine(); // Línea en blanco para separar los ejemplos

        // Ejemplo 2: Validación Incorrecta
        Console.WriteLine("Ejemplo 2: Validación Incorrecta");
        var personaInvalida = new Persona { Nombre = "Laura", Edad = -5 }; // Edad incorrecta
        try
        {
            personaInvalida.Validar();
            Console.WriteLine("Validación exitosa para Laura.");
        }
        catch (ArgumentOutOfRangeException ex)
        {
            Console.WriteLine($"Error: {ex.Message}");
        }

        Console.WriteLine(); // Línea en blanco para separar los ejemplos

        // Ejemplo 3: Serialización JSON
        Console.WriteLine("Ejemplo 3: JSON Resultante");
        var producto = new Producto
        {
            Id = 1,
            Proveedor = "Proveedor ABC",
            Nombre = "Teclado Mecánico",
            Precio = 99.99m
        };

        string json = JsonSerializer.Serialize(producto);
        Console.WriteLine(json);
    }
}
```
## Métodos Utilizados en el Ejemplo
En tu proyecto, los métodos implementados demuestran efectivamente cómo los atributos personalizados y las anotaciones de serialización pueden ser utilizados en C# para validar datos y controlar la representación de objetos en formatos de intercambio de datos como JSON. A continuación, se detalla cómo cada método contribuye a estas funcionalidades:
### Método `Validar` en la Clase `Persona`
Este método es crucial para asegurar que los valores de las propiedades de las instancias de `Persona` cumplen con las restricciones definidas por los atributos personalizados. Aquí se hace uso intensivo de la reflexión para iterar sobre las propiedades de la instancia y verificar si cumplen con los rangos definidos en los atributos `RangoEdadAttribute`.
```csharp
public void Validar()
{
    var properties = GetType().GetProperties();
    foreach (var property in properties)
    {
        var rangoEdadAttr = Attribute.GetCustomAttribute(property, typeof(RangoEdadAttribute)) as RangoEdadAttribute;
        if (rangoEdadAttr != null)
        {
            int valor = (int)property.GetValue(this)!;
            if (valor < rangoEdadAttr.Min || valor > rangoEdadAttr.Max)
            {
                throw new ArgumentOutOfRangeException(property.Name, $"El valor de {property.Name} debe estar entre {rangoEdadAttr.Min} y {rangoEdadAttr.Max}.");
            }
        }
    }
}
```
Este método ilustra cómo los atributos pueden ser herramientas poderosas para la validación de datos, proporcionando un mecanismo para declarar restricciones directamente en las definiciones de clase y asegurando que el estado de la instancia sea válido antes de proceder con otras operaciones.
### Personalización de la Serialización JSON en la Clase `Producto`
La clase `Producto` utiliza atributos de `System.Text.Json` para modificar la forma en que las instancias se serializan a JSON. Estos atributos permiten especificar los nombres de las propiedades en el JSON resultante y excluir propiedades que no deberían ser serializadas.
```csharp
public class Producto
{
    [JsonPropertyName("id_producto")]
    public int Id { get; set; }

    [JsonIgnore]
    public string Proveedor { get; set; }

    [JsonPropertyName("nombre")]
    public string Nombre { get; set; }

    [JsonPropertyName("precio")]
    public decimal Precio { get; set; }
}
```
Estos atributos no solo mejoran la interoperabilidad con otros sistemas y APIs que pueden requerir un formato específico de JSON, sino que también permiten controlar la privacidad y la seguridad de la información al evitar la serialización de datos sensibles.
### Metodo `RangoEdadAttribute`
Este atributo personalizado es una implementación específica diseñada para restringir el rango de valores aceptables para una propiedad, en este caso, la edad de una persona. Aquí se detalla su estructura y funcionalidad:
```csharp
[AttributeUsage(AttributeTargets.Property, AllowMultiple = false)]
public class RangoEdadAttribute : Attribute
{
    public int Min { get; } // Valor mínimo permitido para la edad
    public int Max { get; } // Valor máximo permitido para la edad

    // Constructor del atributo personalizado
    public RangoEdadAttribute(int min, int max)
    {
        Min = min;
        Max = max;
    }
}
```
#### Características y Funcionalidad
- **Restricción de Uso:** El uso de `AttributeUsage` con `AttributeTargets.Property` especifica que este atributo sólo puede aplicarse a propiedades. El parámetro `AllowMultiple = false` asegura que no se pueda aplicar el mismo atributo más de una vez a la misma propiedad, manteniendo claridad y evitando conflictos en las reglas de validación.
- **Propiedades de Solo Lectura:** `Min` y `Max` son propiedades de solo lectura que almacenan los valores mínimos y máximos permitidos para la propiedad a la que se aplica el atributo. Estas propiedades son inicializadas a través del constructor del atributo y no pueden ser modificadas posteriormente, lo que garantiza la integridad de las reglas de validación una vez definidas.
#### Función del Constructor
- **Inicialización de Propiedades:** El constructor del atributo acepta dos parámetros (`min` y `max`), que definen los límites del rango de edad permitido. Estos valores se asignan a las propiedades `Min` y `Max` respectivamente, estableciendo así los criterios de validación que se aplicarán a la propiedad decorada.
#### Aplicación Práctica
Este atributo se utiliza para asegurar que la edad de una persona, representada por la propiedad `Edad `en la clase `Persona`, caiga dentro de un rango definido. Aquí se muestra cómo se aplica:
```csharp
public class Persona
{
    [RangoEdad(0, 130)]
    public int Edad { get; set; }
    public string Nombre { get; set; } = string.Empty;
}
```
En el contexto de uso, este atributo facilita la validación automática de la propiedad `Edad` al utilizar el método `Validar`, donde se examina si el valor de `Edad` se encuentra dentro de los límites especificados por el atributo `RangoEdad`.
#### Impacto en la Validación
El método `Validar` en la clase `Persona` explora dinámicamente las propiedades de la instancia y aplica la validación basada en los atributos `RangoEdad` encontrados, lanzando una excepción si el valor de la propiedad no cumple con el rango establecido. Esto demuestra cómo los atributos personalizados pueden ser usados para centralizar y estandarizar la lógica de validación, haciendo el código más limpio y mantenible.

Este atributo es un ejemplo claro de cómo C# permite extender el lenguaje a través de metadatos para influir en el comportamiento del programa de manera declarativa y controlada.
### Método Main
El método Main en tu programa actúa como el punto de entrada y sirve para demostrar el uso práctico de los atributos personalizados y la serialización JSON. Este método incorpora todos los conceptos explicados anteriormente, mostrando cómo se pueden aplicar los atributos para validar propiedades y controlar la serialización de objetos en un escenario real de ejecución.

## Código de Test
Los tests unitarios verifican la correcta implementación:
```csharp
using NUnit.Framework;
using System;
using System.IO;

namespace C_
{
    [TestFixture]
    public class Tests
    {
        private StringWriter output;

        [SetUp]
        public void Setup()
        {
            output = new StringWriter();
            Console.SetOut(output);
        }

        [TearDown]
        public void TearDown()
        {
            output.Dispose();
        }

        [Test]
        public void TestEmailNotification()
        {
            INotifier emailNotifier = new EmailNotifier();
            NotificationManager manager = new NotificationManager(emailNotifier);
            manager.Notify("Test email");
            Assert.IsTrue(output.ToString().Contains("Enviando Email: Test email"));
        }

        [Test]
        public void TestSmsNotification()
        {
            INotifier smsNotifier = new SmsNotifier();
            NotificationManager manager = new NotificationManager(smsNotifier);
            manager.Notify("Test SMS");
            Assert.IsTrue(output.ToString().Contains("Enviando SMS: Test SMS"));
        }

        [Test]
        public void TestEmptyMessage()
        {
            INotifier emailNotifier = new EmailNotifier();
            NotificationManager manager = new NotificationManager(emailNotifier);
            manager.Notify("");
            // Asegúrate de que no se produzca una excepción al intentar notificar con un mensaje vacío
        }

        [Test]
        public void TestMaximumMessageLength()
        {
            INotifier emailNotifier = new EmailNotifier();
            NotificationManager manager = new NotificationManager(emailNotifier);
            string maxLengthMessage = new string('A', 255); // Mensaje con longitud máxima
            manager.Notify(maxLengthMessage);
            Assert.IsTrue(output.ToString().Contains("Enviando Email: " + maxLengthMessage));
        }

        [Test]
        public void TestLongMessage()
        {
            INotifier emailNotifier = new EmailNotifier();
            NotificationManager manager = new NotificationManager(emailNotifier);
            string longMessage = new string('A', 256); // Mensaje más largo que la longitud máxima permitida
            manager.Notify(longMessage);
            // Asegúrate de que el mensaje se trunque o maneje de manera adecuada
        }

        [Test]
        public void TestDoubleOperation()
        {
            Operations.Operation operation = Operations.Double;
            Assert.AreEqual(6, operation(3));
        }

        [Test]
        public void TestTripleOperation()
        {
            Operations.Operation operation = Operations.Triple;
            Assert.AreEqual(9, operation(3));
        }

        [Test]
        public void TestRuntimeNotifierChange()
        {
            INotifier emailNotifier = new EmailNotifier();
            INotifier smsNotifier = new SmsNotifier();
            NotificationManager manager = new NotificationManager(emailNotifier);
            manager.Notify("Cambio a SMS");
            manager = new NotificationManager(smsNotifier);
            manager.Notify("Cambio a SMS");
            string outputString = output.ToString();
            Assert.IsTrue(outputString.Contains("Enviando Email: Cambio a SMS"));
            Assert.IsTrue(outputString.Contains("Enviando SMS: Cambio a SMS"));
        }

        [Test]
        public void TestMultipleMessages()
        {
            INotifier emailNotifier = new EmailNotifier();
            NotificationManager manager = new NotificationManager(emailNotifier);
            manager.Notify("Mensaje 1");
            manager.Notify("Mensaje 2");
            string outputString = output.ToString();
            Assert.IsTrue(outputString.Contains("Enviando Email: Mensaje 1"));
            Assert.IsTrue(outputString.Contains("Enviando Email: Mensaje 2"));
        }

        [Test]
        public void TestDelegateChangeInExecution()
        {
            Operations.Operation op = Operations.Double;
            Assert.AreEqual(10, op(5)); // Prueba inicial para doblar
            op = Operations.Triple; // Cambio de delegado
            Assert.AreEqual(15, op(5)); // Verificación de triplicar
        }

        [Test]
        public void TestDelegateWithNull()
        {
            Operations.Operation op = null;
            Assert.Throws<NullReferenceException>(() => op(5));
        }
    }
}
```
## Ejecucion desde una Terminal
Para ejecutar y gestionar un proyecto de C# en Linux, es esencial tener instalado el SDK de .NET. Aquí te guío paso a paso desde la instalación de .NET SDK hasta la compilación, ejecución y prueba de tu proyecto C#.
### 1.Instalacion el SDK de .NET
El .NET SDK incluye todo lo necesario para desarrollar y ejecutar aplicaciones C#. Para instalar .NET en Linux, sigue estos pasos:
#### 1.1 Instalar SDK de .NET
Microsoft proporciona varias maneras de instalar .NET en Linux. La más común es a través de los paquetes proporcionados para tu distribución específica.
```bash
wget https://packages.microsoft.com/config/ubuntu/$(lsb_release -rs)/packages-microsoft-prod.deb -O packages-microsoft-prod.deb
sudo dpkg -i packages-microsoft-prod.deb
sudo apt-get update; \
sudo apt-get install -y apt-transport-https && \
sudo apt-get update && \
sudo apt-get install -y dotnet-sdk-6.0
```
#### 1.2 Verificar la instalacion
Después de instalar, verifica que .NET se ha instalado correctamente:
```bash
dotnet --version
```
### 2. Clonar el repositorio
Si el código está disponible en GitHub, puedes clonar el repositorio utilizando Git.
Si aún no tienes Git instalado, puedes descargarlo desde:
https://git-scm.com/downloads
```bash
git clone https://github.com/sistemas-sw/impl-24.git
```
### 3.Preparar Proyecto
Asegúrate de que tienes un archivo `.csproj` adecuado tanto para tu proyecto principal como para tus tests. Específicamente, necesitas configurar el proyecto de test para que haga referencia al proyecto principal si están separados.
#### Modificar el archivo UnitTest1.csproj para hacer referencia al proyecto principal
Suponiendo que tu proyecto principal se llama `Csharp.csproj` y reside en un directorio relativo conocido desde tus tests, puedes agregar la siguiente línea en tu `UnitTest1.csproj` dentro del elemento `<ItemGroup>`:
```xml
<ItemGroup>
  <ProjectReference  Include="..\Csharp_Anotaciones\Csharp_Anotaciones.csproj" />
</ItemGroup>
```
Esta configuración asegura que tu proyecto de pruebas pueda acceder a las clases y métodos del proyecto principal, permitiendo a NUnit ejecutar pruebas correctamente.

### 4.Compilacion Proyecto
Una vez clonado el repositorio y ver que tenemos el `.csproj` correctamente configurado, navega al directorio donde se encuentra el codigo:
```bash
cd impl-24/temas/anotaciones/Csharp_Anotaciones/

```
Compila tu proyecto para verificar que todo está configurado correctamente y que no hay errores de compilación.
```bash
dotnet build
```
Este comando compilará tanto el proyecto principal como los proyectos de pruebas asociados, mostrando cualquier error de compilación que necesite ser resuelto.

### 5.Ejecucion del Proyecto
Para ejecutar el proyecto y ver su comportamiento en tiempo de ejecución, utiliza:
```bash
dotnet run
```
Este comando iniciará la ejecución del proyecto principal, lo cual debería resultar en la ejecución de los métodos definidos en el método `Main()` y la visualización de los resultados en la consola.

### 6.Ejecucion de Pruebas Unitarias
Finalmente, ejecuta las pruebas unitarias para asegurarte de que todo funciona según lo esperado, pero antes debes irte al directorio donde se encuentra los test, para ello usa:
```bash
cd impl-24/temas/anotaciones/Tests_Anotaciones/
```
Una vez en el directorio correcto ejecuta:
```bash
dotnet test
```
Este comando buscará y ejecutará todos los tests definidos en tu proyecto de pruebas, reportando resultados para cada test y resumiendo el número de pruebas que pasaron, fallaron o fueron omitidas.

## Errores Comunes y sus Soluciones
Durante la configuración, compilación y ejecución de proyectos C# en Linux, puedes encontrarte con varios problemas. A continuación, se describen algunos de los errores más comunes junto con sus soluciones para ayudarte a resolverlos de manera eficiente.
### 1.Problemas de Versión de .NET SDK
#### Error: Versión Incorrecta del .NET SDK
#### Síntomas: Mensajes de error indicando que la versión del SDK no es compatible con el proyecto.
#### Solución: 
Asegúrate de que tienes instalada la versión correcta del .NET SDK que exige tu proyecto. Puedes listar todas las versiones instaladas y seleccionar la adecuada con los siguientes comandos:
```bash
dotnet --list-sdks
dotnet new globaljson --sdk-version <versión_deseada>
```
### 2.Referencias de Proyecto Incorrectas
#### Error: Falta de Referencias entre Proyectos
#### Síntomas: Errores de compilación donde se mencionan tipos o miembros no definidos que deberían estar en otros proyectos.
#### Solución:
Verifica las referencias de proyecto en tus archivos .csproj. Asegúrate de que el proyecto de test tiene una referencia correcta al proyecto principal, como se muestra a continuación:
```xml
<ItemGroup>
  <ProjectReference Include="..\Csharp_Anotaciones\Csharp_Anotaciones.csproj" />
</ItemGroup>
``` 
### 3.Errores de Compilación
#### Error: Fallos al Compilar el Proyecto
#### Síntomas: Mensajes de error durante la compilación que pueden incluir problemas con sintaxis, referencias a librerías faltantes, etc.
#### Solución:
- **Revisa los mensajes de error para entender cuál es el problema específico.**
- **Si faltan dependencias o paquetes, asegúrate de que están incluidos en el archivo .csproj y vuelve a intentar la compilación:**
```bash
dotnet restore
dotnet build
```
### 4.Problemas al Ejecutar el Proyecto
#### Error: Errores de Ejecución
#### Síntomas: Excepciones o comportamientos inesperados al ejecutar el proyecto.
#### Solución:
- **Asegúrate de que todas las configuraciones de entorno y de proyecto son correctas.**
- **Utiliza herramientas de depuración o añade declaraciones de impresión para rastrear el flujo de ejecución y entender dónde falla.**
### 5.Errores al Ejecutar Pruebas
#### Error: Fallos en las Pruebas Unitarias
#### Síntomas: Pruebas unitarias que fallan cuando deberían pasar.
#### Solución:
- **Revisa las aserciones y la lógica de las pruebas. Asegúrate de que el entorno de pruebas está configurado correctamente y que no hay dependencias externas afectando los resultados.**
- **Utiliza el modo detallado para obtener más información sobre los fallos:**
```bash
dotnet test --logger "console;verbosity=detailed"
```