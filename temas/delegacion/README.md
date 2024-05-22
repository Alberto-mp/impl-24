# Delegación en C#

## Introducción
Este repositorio contiene un proyecto en C# diseñado para ilustrar el uso de delegaciones, notificaciones y pruebas unitarias. El proyecto se divide en dos partes principales: el código principal que implementa las funcionalidades y un conjunto de pruebas unitarias para verificar la correctitud del código.

## Estructura de Directorio
La estructura del directorio de este proyecto está organizada para separar claramente el código de implementación del código de pruebas, facilitando la navegación y el mantenimiento del proyecto. A continuación, se detalla la organización de los directorios y archivos principales:

- `/README.md`: Este archivo proporciona una visión general del proyecto, instrucciones de instalación y uso, y documentación general.

- `/Csharp`: Directorio raíz que alberga todos los archivos de código fuente relacionados con los ejemplos de delegación implementados en C#. Dentro de este directorio, encontrarás los siguientes subdirectorios y archivos:
    - `Program.cs`: Archivo principal que contiene la lógica de las delegaciones, incluyendo definiciones de interfaces y clases que gestionan las notificaciones.

- `/Test`: Directorio raíz dedicado a los archivos de pruebas unitarias para los ejemplos proporcionados. Este directorio contiene:
    - `UnitTest1.cs`: Archivo que incluye todas las pruebas unitarias necesarias para asegurar el correcto funcionamiento de las funcionalidades implementadas en el proyecto. Utiliza el framework NUnit para definir y ejecutar las pruebas.

Esta estructura de directorio está diseñada para mantener una clara distinción entre el desarrollo del código y las pruebas, apoyando prácticas de desarrollo de software como la integración continua y el despliegue continuo.

## Conceptos Previos
La delegación en C# es un concepto fundamental que permite a los desarrolladores encapsular un método dentro de un objeto delegado. Esto facilita el paso de métodos como argumentos a otros métodos, la gestión de eventos y la creación de callbacks, proporcionando así una gran flexibilidad y potenciando patrones de diseño como el observador y el comando. Aquí se explican los conceptos esenciales de la delegación en C#:

- **Defenicion de Delegados:**
Un delegado es un tipo que representa referencias seguras a métodos con una firma y tipo de retorno específicos. En C#, un delegado es una clase que puede mantener una referencia a un método. Los delegados son similares a los punteros a funciones en C++, pero son tipo seguro y están integrados con el sistema de tipos de .NET.
- **Declaración de Delegados:**
Un delegado se declara definiendo su firma de método. Esto incluye los tipos de los parámetros y el tipo de retorno del método que puede referenciar el delegado. Por ejemplo:
```csharp
public delegate int Calculation(int x, int y);
```
Este delegado puede referenciar cualquier método que tenga dos parámetros enteros y devuelva un entero.
- **Uso de Delegados**
Los delegados son especialmente útiles para implementar llamadas de retorno `(callbacks)` y eventos. Permiten a los desarrolladores escribir métodos que son independientes de los métodos a los que llaman, haciendo el código más modular y fácil de mantener.

- **Multicasting**
Los delegados en C# soportan el multicasting, lo que significa que un delegado puede referenciar y llamar a más de un método. Esto se utiliza a menudo en la gestión de eventos, donde múltiples métodos necesitan ser llamados cuando ocurre un evento. Los delegados multicast utilizan el operador `+=` para añadir métodos a la lista de llamadas y `-=` para removerlos.

- **Delegados Predefinidos**
.NET Framework proporciona varios delegados predefinidos para facilitar el desarrollo, incluyendo `Action`, `Func`, y `Predicate`. Estos delegados cubren la mayoría de las necesidades comunes de delegación, eliminando la necesidad de declarar delegados explícitos en muchos casos.
    - **Action:** Representa un método que ejecuta una acción y no devuelve un valor.
    - **Func:** Representa un método que devuelve un valor.
    - **Predicate:** Representa un método que devuelve un valor booleano.

- **Conclusion**
Los delegados en C# proporcionan una manera poderosa y flexible de manejar referencias a métodos, permitiendo el diseño de software que es fácil de cambiar y extender. Su integración con eventos y soporte para multicasting amplía aún más su utilidad en el desarrollo de aplicaciones .NET.

## Código de Ejemplo
En este apartado se detallan ejemplos de código en C# que ilustran el uso de delegaciones para implementar diferentes patrones y funcionalidades. A continuación, se explica cómo se pueden utilizar los delegados en varios contextos prácticos, incluyendo la notificación y la ejecución de operaciones matemáticas mediante delegados.
```csharp
using System;

// Definición de la interfaz INotifier
public interface INotifier
{
    void SendNotification(string message);
}

// Implementación de notificación por Email
public class EmailNotifier : INotifier
{
    public void SendNotification(string message)
    {
        Console.WriteLine($"Enviando Email: {message}");
    }
}

// Implementación de notificación por SMS
public class SmsNotifier : INotifier
{
    public void SendNotification(string message)
    {
        Console.WriteLine($"Enviando SMS: {message}");
    }
}

// Clase NotificationManager que delega el envío de notificaciones
public class NotificationManager
{
    private INotifier notifier;

    public NotificationManager(INotifier notifier)
    {
        this.notifier = notifier;
    }

    public void Notify(string message)
    {
        Console.WriteLine("Preparando para enviar notificación...");
        notifier.SendNotification(message);
    }
}

public class Operations
{
    // Declaración de un delegado que apunta a cualquier método que reciba un int y devuelva un int
    public delegate int Operation(int number);

    public static int Double(int x)
    {
        return x * 2;
    }

    public static int Triple(int x)
    {
        return x * 3;
    }
}

// Programa principal
public class Program
{
    public static void Main()
    {
        Console.WriteLine("===== Notificación por Email =====");
        INotifier emailNotifier = new EmailNotifier();
        NotificationManager manager = new NotificationManager(emailNotifier);
        manager.Notify("Hola! Este es un mensaje de prueba.");

        Console.WriteLine("\n===== Cambio a Notificación por SMS =====");
        INotifier smsNotifier = new SmsNotifier();
        manager = new NotificationManager(smsNotifier);
        manager.Notify("Otro mensaje, ahora por SMS.");

        Console.WriteLine("\n===== Operaciones Matemáticas con Delegados =====");
        Operations.Operation op = Operations.Double;
        Console.WriteLine("El doble de 5 es: " + op(5));

        op = Operations.Triple;
        Console.WriteLine("El triple de 5 es: " + op(5));
    }
}
```
## Métodos Utilizados en el Ejemplo
En este proyecto se implementan varios métodos que demuestran el uso de interfaces, delegación y patrones de diseño en C#. A continuación se detalla la funcionalidad y el propósito de cada uno de los métodos utilizados en el ejemplo.
### Metodos de Notificacion
- **SendNotifer:**
Este método es parte de la interfaz `INotifier` y es implementado por las clases `EmailNotifier` y `SmsNotifier`. Su propósito principal es enviar una notificación, adaptándose al medio específico según la clase que lo implemente:
- **EmailNotifier.SendNotification:**
Envía una notificación vía email. Este método toma un mensaje como parámetro y muestra una salida que simula el envío de un email con ese mensaje.
```csharp
public void SendNotification(string message)
{
    Console.WriteLine($"Enviando Email: {message}");
}
```
- **SmsNotifier.SendNotification:**
Funciona de manera similar al notificador de email, pero simula el envío de un SMS.
```csharp
public void SendNotification(string message)
{
    Console.WriteLine($"Enviando SMS: {message}");
}
```
- **Notify:**
Este método está definido en la clase `NotificationManager` y delega el proceso de enviar una notificación a un objeto `INotifier`. Actúa como un puente entre el cliente del código y las implementaciones concretas de `INotifier`, lo que permite cambiar la forma de notificación sin alterar el resto del sistema.
    - **Funcionamiento:**
    Primero muestra un mensaje de preparación y luego llama al método `SendNotification` del objeto `notifier` asignado. Esta estructura facilita la inyección de dependencia y demuestra un uso práctico del patrón de diseño Estrategia.
```csharp
public void Notify(string message)
{
    Console.WriteLine("Preparando para enviar notificación...");
    notifier.SendNotification(message);
}
```
### Métodos Operaciones Matemáticas
- **Double y Tripe**
Estos métodos estáticos están definidos en la clase `Operations` y se utilizan para demostrar operaciones matemáticas simples: duplicar y triplicar un número, respectivamente.
```csharp
public static int Double(int x)
{
    return x * 2;
}
```
Retorna el doble del valor entero proporcionado.

```csharp
public static int Triple(int x)
{
    return x * 3;
}
```
Retorna el triple del valor entero proporcionado.

Estos métodos son utilizados como referencias en delegados del tipo Operation, que es un delegado definido para referenciar cualquier método que acepte un int y devuelva un int.

### Uso de Delegados 
- **Operation**
Este delegado es un tipo definido para referenciar métodos que toman un entero y devuelven un entero. Se utiliza para demostrar cómo se pueden asignar diferentes métodos a una variable de tipo delegado y cambiar dinámicamente el comportamiento de las operaciones matemáticas sin cambiar el código que utiliza estas operaciones.
```csharp
public delegate int Operation(int number);
```
### Método Main
El método `main` actúa como el punto de entrada del programa y utiliza todos los métodos anteriores para demostrar cómo se manejan diferentes tipos de errores en la práctica.

## Código de Test
Los tests unitarios verifican la correcta implementación del manejo de errores:
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
  <ProjectReference Include="../Csharp/Csharp.csproj" />
</ItemGroup>
```
Esta configuración asegura que tu proyecto de pruebas pueda acceder a las clases y métodos del proyecto principal, permitiendo a NUnit ejecutar pruebas correctamente.

### 4.Compilacion Proyecto
Una vez clonado el repositorio y ver que tenemos el `.csproj` correctamente configurado, navega al directorio donde se encuentra el codigo:
```bash
cd impl-24/temas/delegacion/csharp-01/Csharp
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
cd impl-24/temas/delegacion/csharp-01/Tests/
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
  <ProjectReference Include="../Csharp/Csharp.csproj" />
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