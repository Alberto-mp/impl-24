import 'dart:async';

import 'package:test/test.dart';
import 'package:dart/persona.dart';

void main() {
  group('Persona', () {
    // Crear una persona para los tests
    var persona = Persona('Test', 30);

    test('hablar devuelve el mensaje correcto', () async {
      // Capturar la salida estándar de print
      var output = await capturePrint(() {
        persona.hablar();
      });

      // Verificar que el resultado sea el esperado
      expect(output, 'Hola, mi nombre es Test y tengo 30 años.\n');
    });
  });
}

// Función para capturar la salida estándar de print
Future<String> capturePrint(Function function) async {
  var output = '';
  var zone = Zone.current.fork(
    specification: ZoneSpecification(print: (self, parent, zone, line) {
      output += '$line\n';
    }),
  );
  await zone.run(() async {
    function();
  });
  return output;
}
