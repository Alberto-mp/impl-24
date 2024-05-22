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
