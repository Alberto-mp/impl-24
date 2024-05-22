# Inyecion en C#

## Introducción
Este repositorio demuestra la implementación de la inyección de dependencias en C#, una técnica esencial en el desarrollo de software que mejora la modularidad y la testabilidad del código. A través de varios ejemplos, exploramos cómo configurar y utilizar un contenedor de inyección de dependencias para administrar y resolver dependencias de manera eficiente, haciendo que el código sea más limpio y mantenible.

## EStructura de Directorio
El proyecto está estructurado para separar claramente el código de la aplicación de las pruebas unitarias, facilitando tanto el desarrollo como la verificación del funcionamiento correcto del código:
- **/README.md:** Archivo que proporciona información detallada sobre el proyecto, incluyendo cómo configurar y ejecutar las aplicaciones.
- **/Csharp_Inyeccion:** Directorio raíz que contiene todos los archivos de código fuente relacionados con la implementación de inyeccion de dependencias en C#.
    - **/Program.cs:** Contiene la implementación de la inyección de dependencias usando `Microsoft.Extensions.DependencyInjection`, incluidos ejemplos de cómo configurar y utilizar el contenedor de servicios.
- **/Tests_Inyeccion:** Este directorio está dedicado exclusivamente a albergar los archivos de pruebas unitarias para los ejemplos implementados. Está diseñado para asegurar que todas las funcionalidades desarrolladas funcionan como se espera:
    - **/UnitTest1.cs:** Aloja las pruebas unitarias que validan la funcionalidad de la inyección de dependencias implementada en Program.cs, usando marcos de prueba como NUnit o xUnit.
## Conceptos Básicos
La inyección de dependencias es un método para remover dependencias explícitas entre los objetos de tu código, haciéndolos más fáciles de gestionar y más flexibles en sus interacciones. En lugar de que los objetos creen sus propias dependencias, estas les son suministradas, lo cual permite configuraciones más flexibles y facilita las pruebas unitarias.
### Contenedor de Inyección de Dependencias
En C#, la gestión de inyección de dependencias se realiza típicamente a través de un contenedor de DI, que es responsable de la creación, configuración y provisión de instancias de clases dependientes. Este contenedor se configura al inicio de una aplicación, definiendo cómo se deben crear las instancias y sus dependencias correspondientes.
### Registro de Dependencias
El registro de dependencias se realiza en el contenedor de DI mediante la definición de interfaces y sus implementaciones concretas. Esto incluye decidir el ciclo de vida de estos objetos, como transientes, de alcance o singletones:
- **Transientes:** Una nueva instancia cada vez que se solicita.
- **Scopped:** Una instancia única por solicitud.
- **Singletones:** Una instancia única para toda la aplicación.
### Resolución de Dependencias
Cuando se necesita un objeto, el contenedor de DI se encarga de resolver las dependencias automáticamente. Esto se realiza construyendo el objeto requerido y todas sus dependencias de forma recursiva según lo definido en la configuración del contenedor.
### Ventajas de la Inyección de Dependencias
- **Desacoplamiento:** Los componentes son menos dependientes entre sí, lo que facilita el cambio y la gestión de las dependencias.
- **Facilidad de Pruebas:** La inyección de dependencias hace que las pruebas unitarias sean más fáciles, ya que permite la sustitución de dependencias reales por mocks o stubs.
- **Gestión Centralizada:** Todas las dependencias están configuradas en un solo lugar, lo que mejora la mantenibilidad del código.
- **Flexibilidad:** Es fácil cambiar las implementaciones de dependencias sin modificar los consumidores.
### Patrones Comunes
- **Constructor Injection:** Las dependencias se proporcionan a través de los constructores de clase.
- **Property Injection:** Las dependencias se asignan a través de propiedades públicas.
- **Method Injection:** Las dependencias se suministran como parámetros a los métodos que las necesitan.
### Frameworks de DI en C#
- **Microsoft.Extensions.DependencyInjection:** La implementación más común utilizada en aplicaciones .NET, especialmente en ASP.NET Core.
- **Autofac:** Un poderoso contenedor de inyección de dependencias que ofrece funcionalidades avanzadas.
- **Ninject:** Un contenedor que se enfoca en ser fácil de usar y configurar.
Cada uno de estos frameworks de DI proporciona diferentes capacidades y formas de configurar y manejar la inyección de dependencias, permitiendo a los desarrolladores elegir la herramienta que mejor se adapte a sus necesidades específicas.

La correcta implementación de la inyección de dependencias en C# puede llevar a una arquitectura de software significativamente más robusta, facilitando la gestión de dependencias y aumentando la escalabilidad y la mantenibilidad del código.
## Código de Ejemplo
Este proyecto proporciona un enfoque práctico y funcional para implementar la inyección de dependencias en C# utilizando el contenedor de servicios `Microsoft.Extensions.DependencyInjection`. A través del código de ejemplo, exploramos cómo configurar y utilizar un sistema de inyección de dependencias para mejorar la escalabilidad, la prueba y el mantenimiento de las aplicaciones .NET.
```csharp
using System;
using Microsoft.Extensions.DependencyInjection;

// Definición de las interfaces
public interface INotifier
{
    void SendNotification(string message);
}

public interface IOrderProcessor
{
    void ProcessOrder(string orderId);
}

public interface ILogger
{
    void Log(string message);
}

// Implementaciones de Notificadores
public class EmailNotifier : INotifier
{
    public void SendNotification(string message)
    {
        Console.WriteLine($"Enviando Email: {message}");
    }
}

public class SmsNotifier : INotifier
{
    public void SendNotification(string message)
    {
        Console.WriteLine($"Enviando SMS: {message}");
    }
}

// Implementación de Logger
public class ConsoleLogger : ILogger
{
    public void Log(string message)
    {
        Console.WriteLine($"Log: {message}");
    }
}

// Servicio de procesamiento de pedidos
public class OrderProcessor : IOrderProcessor
{
    private readonly INotifier _notifier;
    private readonly ILogger _logger;

    public OrderProcessor(INotifier notifier, ILogger logger)
    {
        _notifier = notifier;
        _logger = logger;
    }

    public void ProcessOrder(string orderId)
    {
        try
        {
            _logger.Log($"=== Inicio del procesamiento del pedido {orderId} ===");
            Console.WriteLine($"Procesando el pedido {orderId}.\n");

            if (orderId.Contains("VIP"))
            {
                _notifier.SendNotification($"Su pedido VIP {orderId} ha sido procesado con prioridad.");
            }
            else
            {
                _notifier.SendNotification($"Su pedido {orderId} ha sido procesado exitosamente.");
            }

            _logger.Log($"Pedido {orderId} procesado y notificado.");
            Console.WriteLine("=== Fin del procesamiento del pedido ===\n");
        }
        catch (Exception ex)
        {
            _logger.Log($"Error durante el procesamiento del pedido {orderId}: {ex.Message}");
            throw;  // Re-throw the exception if you need to handle it further up the stack.
        }
    }
}

// Programa principal
public class Program
{
    public static void Main(string[] args)
    {
        Console.WriteLine("\nConfigurando servicios y resolviendo dependencias...\n");
        var services = new ServiceCollection();
        ConfigureServices(services, args);
        var serviceProvider = services.BuildServiceProvider();
        Console.WriteLine("Servicios configurados con éxito.\n");

        var orderProcessor = serviceProvider.GetService<IOrderProcessor>();
        if (orderProcessor != null)
        {
            Console.WriteLine("Iniciando procesamiento de pedido...");
            orderProcessor.ProcessOrder("123456");
            Console.WriteLine("Procesamiento de pedido completado.\n");
        }
        else
        {
            Console.WriteLine("No se pudo resolver IOrderProcessor.");
        }
    }

    private static void ConfigureServices(IServiceCollection services, string[] args)
    {
        services.AddSingleton<ILogger, ConsoleLogger>();

        // Determinar dinámicamente qué notificador utilizar
        if (args.Length > 0 && args[0].ToLower() == "sms")
        {
            services.AddTransient<INotifier, SmsNotifier>();
        }
        else
        {
            services.AddTransient<INotifier, EmailNotifier>();
        }

        services.AddTransient<IOrderProcessor, OrderProcessor>();
    }
}
```
## Métodos Utilizados en el Ejemplo
El código proporciona una visión práctica sobre cómo se implementan y utilizan diferentes métodos para manejar la inyección de dependencias en una aplicación C#. A continuación, se detallan los métodos claves utilizados en el ejemplo:
### Método ConfigureServices
Este método es crucial para configurar los servicios que se utilizarán en la aplicación. Utiliza `IServiceCollection` para registrar las dependencias. Este es el punto donde se configura la inyección de dependencias, especificando qué implementaciones concretas se deben usar para las interfaces definidas.
```csharp
private static void ConfigureServices(IServiceCollection services, string[] args)
{
    services.AddSingleton<ILogger, ConsoleLogger>();

    // Determinar dinámicamente qué notificador utilizar basado en los argumentos del programa
    if (args.Length > 0 && args[0].ToLower() == "sms")
    {
        services.AddTransient<INotifier, SmsNotifier>();
    }
    else
    {
        services.AddTransient<INotifier, EmailNotifier>();
    }

    services.AddTransient<IOrderProcessor, OrderProcessor>();
}
```
Este método demuestra cómo se puede controlar la configuración de los servicios de manera flexible y dinámica, permitiendo variaciones en la configuración sin cambiar el código fuente de la aplicación.
### Método ProcessOrder en OrderProcessor
Este método encapsula la lógica para procesar pedidos, demostrando cómo las dependencias (como `INotifier` y `ILogger`) se inyectan y se utilizan dentro de un servicio. Muestra un uso práctico de la inyección de dependencias para desacoplar la lógica de notificación y registro de la lógica de procesamiento de pedidos.
```csharp
public void ProcessOrder(string orderId)
{
    try
    {
        _logger.Log($"=== Inicio del procesamiento del pedido {orderId} ===");
        Console.WriteLine($"Procesando el pedido {orderId}.\n");

        if (orderId.Contains("VIP"))
        {
            _notifier.SendNotification($"Su pedido VIP {orderId} ha sido procesado con prioridad.");
        }
        else
        {
            _notifier.SendNotification($"Su pedido {orderId} ha sido procesado exitosamente.");
        }

        _logger.Log($"Pedido {orderId} procesado y notificado.");
        Console.WriteLine("=== Fin del procesamiento del pedido ===\n");
    }
    catch (Exception ex)
    {
        _logger.Log($"Error durante el procesamiento del pedido {orderId}: {ex.Message}");
        throw;
    }
}
```
Este método no solo procesa pedidos sino también demuestra cómo las dependencias inyectadas pueden ser utilizadas para realizar tareas específicas, como notificar a los usuarios y registrar las operaciones.

### Interfaces y sus Implementaciones en el Código

En EL código, has definido interfaces esenciales y sus implementaciones correspondientes que juegan un papel crucial en la estructura de inyección de dependencias de tu aplicación. Cada una de estas interfaces y sus implementaciones contribuyen a la separación de preocupaciones y la flexibilidad en el manejo de las notificaciones y el registro de eventos dentro de la aplicación.
#### Interfaces Definidas
##### 1. INotifier:
- **Propósito:** Define un contrato para los servicios de notificación, asegurando que cualquier implementador de esta interfaz pueda enviar notificaciones.
- **Método:** `void SendNotification(string message);` Método que debe ser implementado para enviar notificaciones.
##### 2. IOrderProcessor:
- **Propósito:** Establece un contrato para los servicios que procesan pedidos, permitiendo que se implementen diversas lógicas de procesamiento de pedidos.
- **Método:** `void ProcessOrder(string orderId);` Método que debe ser implementado para procesar un pedido.
##### 3. ILogger:
- **Propósito:** Define un contrato para los servicios de registro (logging), permitiendo la implementación de diversas estrategias de registro.
- **Método:** `void Log(string message);` Método que debe ser implementado para registrar mensajes.
#### Implementaciones de las Interfaces
##### 1. EmailNotifier (Implementa INotifier):
- **Función:** Esta clase es responsable de enviar notificaciones por correo electrónico.
- **Método Implementado:** `SendNotification(string message)` Imprime un mensaje en la consola que simula el envío de un correo electrónico.
```csharp
public void SendNotification(string message)
{
    Console.WriteLine($"Enviando Email: {message}");
}
```
##### 2. SmsNotifier (Implementa INotifier):
- **Función:** Maneja el envío de notificaciones vía SMS.
- **Método Implementado:** `SendNotification(string message)` Imprime un mensaje en la consola que simula el envío de un SMS.
```csharp
public void SendNotification(string message)
{
    Console.WriteLine($"Enviando SMS: {message}");
}
```
##### 3. ConsoleLogger (Implementa ILogger):
- **Función:** Proporciona un mecanismo para registrar mensajes en la consola, facilitando el seguimiento de eventos y errores.
- **Método Implementado:** `Log(string message)` Imprime un mensaje de log en la consola.
```csharp
public void Log(string message)
{
    Console.WriteLine($"Log: {message}");
}
```
##### Contribuciones al Proceso de Inyección de Dependencias
Estas interfaces y sus implementaciones son cruciales para la arquitectura de inyección de dependencias de tu proyecto. Al definir comportamientos en interfaces y luego proporcionar implementaciones concretas, puedes cambiar fácilmente la funcionalidad de tu aplicación sin modificar el código que depende de estas abstracciones. Por ejemplo, podrías introducir una nueva forma de notificación sin alterar el proceso de negocio que depende de `INotifier`.

La inyección de dependencias, gestionada a través de `IServiceCollection` y utilizada en `ServiceCollection`, te permite configurar y resolver estas dependencias dinámicamente en tiempo de ejecución, lo cual es evidente en cómo se configura y utiliza `EmailNotifier` o `SmsNotifier` dependiendo de los argumentos de entrada del programa.

Este enfoque no solo facilita la expansión y el mantenimiento del código sino que también mejora significativamente la capacidad de probar los componentes de manera aislada, proporcionando flexibilidad y robustez a la arquitectura de tu aplicación.

### Metodo Main
Actuando como el punto de entrada del programa, este método configura el contenedor de servicios y resuelve las dependencias necesarias para ejecutar la lógica de procesamiento de pedidos. También ilustra cómo se construye y se utiliza el proveedor de servicios para obtener las instancias de los servicios configurados.

## Código de Test
Los tests unitarios verifican la correcta implementación:
```csharp
using Microsoft.Extensions.DependencyInjection;
using NUnit.Framework;
using Moq;
using System;

namespace Tests_Inyeccion
{
    public class Tests
    {
        private Mock<INotifier> mockNotifier;
        private Mock<ILogger> mockLogger;
        private OrderProcessor orderProcessor;

        [SetUp]
        public void Setup()
        {
            // Crear mocks de las dependencias
            mockNotifier = new Mock<INotifier>();
            mockLogger = new Mock<ILogger>();

            // Crear una instancia de OrderProcessor con las dependencias simuladas
            orderProcessor = new OrderProcessor(mockNotifier.Object, mockLogger.Object);

            // Setup de comportamiento esperado de los mocks
            mockNotifier.Setup(n => n.SendNotification(It.IsAny<string>()))
                        .Callback<string>(msg => Console.WriteLine($"Mock Notifier: {msg}"));

            mockLogger.Setup(l => l.Log(It.IsAny<string>()))
                      .Callback<string>(msg => Console.WriteLine($"Mock Logger: {msg}"));
        }

        [Test]
        public void ProcessOrder_CallsSendNotification()
        {
            string orderId = "123";
            orderProcessor.ProcessOrder(orderId);
            mockNotifier.Verify(n => n.SendNotification(It.Is<string>(msg => msg.Contains(orderId))), Times.Once());
        }

        [Test]
        public void ProcessOrder_CallsLogTwice()
        {
            string orderId = "123";
            orderProcessor.ProcessOrder(orderId);
            // Verifica que el log es llamado exactamente dos veces
            mockLogger.Verify(l => l.Log(It.IsAny<string>()), Times.Exactly(2));
        }

        [Test]
        public void ProcessOrder_LogsOrderProcessingMessages()
        {
            string orderId = "123";
            orderProcessor.ProcessOrder(orderId);
            // Verifica que se loguee el inicio del procesamiento
            mockLogger.Verify(l => l.Log(It.Is<string>(msg => msg.Contains("Inicio del procesamiento del pedido"))), Times.Once());
            // Verifica que se loguee el procesamiento y notificación
            mockLogger.Verify(l => l.Log(It.Is<string>(msg => msg.Contains("procesado y notificado"))), Times.Once());
        }        

        [Test]
        public void ProcessOrder_SpecialOrderId_SendsPriorityNotification()
        {
            string specialOrderId = "VIP123";
            orderProcessor.ProcessOrder(specialOrderId);
            // Verifica que se envíe una notificación de prioridad para un pedido VIP
            mockNotifier.Verify(n => n.SendNotification(It.Is<string>(msg => msg.Contains("VIP") && msg.Contains("procesado con prioridad"))), Times.Once());
        }

        [Test]
        public void ProcessOrder_NotifierThrowsException_LogsError()
        {
            string orderId = "123";
            // Configura el mock para lanzar una excepción cuando se llame a SendNotification
            mockNotifier.Setup(n => n.SendNotification(It.IsAny<string>())).Throws(new Exception("Error de notificación"));
            // Verifica que se lanza la excepción
            Assert.Throws<Exception>(() => orderProcessor.ProcessOrder(orderId));
            // Verifica que se registra el error específico
            mockLogger.Verify(l => l.Log(It.Is<string>(msg => msg.Contains("Error de notificación"))), Times.Once());
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
  <ProjectReference Include="..\Csharp_Inyeccion\Csharp_Inyeccion." />
</ItemGroup>
```
Esta configuración asegura que tu proyecto de pruebas pueda acceder a las clases y métodos del proyecto principal, permitiendo a NUnit ejecutar pruebas correctamente.

### 4.Compilacion Proyecto
Una vez clonado el repositorio y ver que tenemos el `.csproj` correctamente configurado, navega al directorio donde se encuentra el codigo:
```bash
cd impl-24/temas/inyeccion/csharp-01/Csharp_Inyeccion/
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
cd impl-24/temas/inyeccion/csharp-01/Tests_Inyeccion/
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
  <ProjectReference Include="..\Csharp_Inyeccion\Csharp_Inyeccion." />
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