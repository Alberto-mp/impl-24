import 'package:dart/hablador.dart';
import 'package:test/test.dart';
import 'package:dart/robot_inteligente.dart';

void main() {
  group('RobotInteligente', () {
    // Crear un robot inteligente para los tests
    var robot = RobotInteligente('TestBot', 'Acero');

    test('hablar agrega una acción correcta', () {
      robot.hablar();
      expect(robot.acciones.last, 'HOLA. SOY. UN. ROBOT. INTELIGENTE. Y. PUEDO. HABLAR. CON. LOS. HUMANOS. MI. NOMBRE. ES. TestBot. Y. ESTOY. HECHO. DE. Acero.');
    });

    test('sumar agrega una acción correcta', () {
      robot.sumar(2, 3);
      expect(robot.acciones.last, 'TestBot sumando 2 y 3, el resultado es 5');
    });

    test('restar agrega una acción correcta', () {
      robot.restar(5, 3);
      expect(robot.acciones.last, 'TestBot restando 5 y 3, el resultado es 2');
    });

    test('multiplicar agrega una acción correcta', () {
      robot.multiplicar(2, 3);
      expect(robot.acciones.last, 'TestBot multiplicando 2 y 3, el resultado es 6');
    });

    test('dividir agrega una acción correcta', () {
      robot.dividir(6, 3);
      expect(robot.acciones.last, 'TestBot dividiendo 6 y 3, el resultado es 2.0');
    });

    test('dividir por cero agrega una acción correcta', () {
      robot.dividir(6, 0);
      expect(robot.acciones.last, 'TestBot no puede dividir por cero');
    });

    test('objetivo devuelve el objetivo correcto', () {
      expect(robot.objetivo(), 'El objetivo de el robot inteligente TestBot es comunicarse con humanos y realizar cálculos complejos.');
    });

    test('hacerHablar llama al método hablar', () {
      expect(() => hacerHablar(robot), returnsNormally);
    });
    
    test('movimiento correcto', () {
      robot.mover('adelante');
      expect(robot.acciones.last, 'TestBot moviéndose hacia adelante');
    });

    test('giro correcto', () {
      robot.girar('derecha');
      expect(robot.acciones.last, 'TestBot girando hacia derecha');
    });
  });
}