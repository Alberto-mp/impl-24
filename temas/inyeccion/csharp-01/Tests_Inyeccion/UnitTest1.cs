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
