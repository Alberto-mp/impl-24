using NUnit.Framework;
using System.Text.Json;
using System;

namespace Tests_Anotaciones
{
    public class Tests
    {
        [SetUp]
        public void Setup()
        {
            // Configuración inicial para todas las pruebas, si es necesario.
        }

        // Pruebas para la clase Persona
        [Test]
        public void Validar_EdadDentroDelRango_NoDebeArrojarExcepcion()
        {
            var persona = new Persona { Nombre = "Ana", Edad = 25 };
            Assert.DoesNotThrow(() => persona.Validar());
        }

        [TestCase(-1)]
        [TestCase(131)]
        public void Validar_EdadFueraDelRango_DebeArrojarExcepcion(int edadInvalida)
        {
            var persona = new Persona { Nombre = "Roberto", Edad = edadInvalida };
            var ex = Assert.Throws<ArgumentOutOfRangeException>(() => persona.Validar());
            Assert.That(ex.ParamName, Is.EqualTo("Edad"));
            Assert.That(ex.Message, Does.Contain($"El valor de Edad debe estar entre 0 y 130."));
        }

        [TestCase(0)]
        [TestCase(130)]
        public void Validar_EdadEnLimiteDelRango_NoDebeArrojarExcepcion(int edadLimite)
        {
            var persona = new Persona { Nombre = "Carlos", Edad = edadLimite };
            Assert.DoesNotThrow(() => persona.Validar());
        }

        // Pruebas para la clase Producto
        [Test]
        public void Serializar_Producto_DebeIgnorarProveedorYFormatearCorrectamente()
        {
            var producto = new Producto
            {
                Id = 2,
                Proveedor = "Proveedor XYZ",
                Nombre = "Mouse Inalambrico",
                Precio = 29.99m
            };

            string jsonEsperado = "{\"id_producto\":2,\"nombre\":\"Mouse Inalambrico\",\"precio\":29.99}";
            string jsonObtenido = JsonSerializer.Serialize(producto);

            Assert.That(jsonObtenido, Is.EqualTo(jsonEsperado));
        }

        [Test]
        public void Serializar_Producto_DebeIncluirNombreYPrecioCorrectamente()
        {
            var producto = new Producto
            {
                Id = 3,
                Nombre = "Monitor LCD",
                Precio = 150.50m
            };

            string json = JsonSerializer.Serialize(producto);
            Assert.That(json, Does.Contain("\"nombre\":\"Monitor LCD\""));
            Assert.That(json, Does.Contain("\"precio\":150.5")); // JSON no incluye ceros no significativos
        }

        [TestCase(0.01)]
        [TestCase(10000.00)]
        public void Serializar_Producto_ConValoresLimitesDePrecio_DebeSerializarCorrectamente(decimal precio)
        {
            var producto = new Producto
            {
                Id = 4,
                Nombre = "Producto Costoso",
                Precio = precio
            };

            string json = JsonSerializer.Serialize(producto);
            Assert.That(json, Does.Contain($"\"precio\":{precio.ToString("0.##", System.Globalization.CultureInfo.InvariantCulture)}"));
        }
    }
}
