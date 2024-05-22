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
