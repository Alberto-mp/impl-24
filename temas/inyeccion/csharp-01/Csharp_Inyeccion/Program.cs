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
