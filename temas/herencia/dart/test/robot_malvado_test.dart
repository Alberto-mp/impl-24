import 'package:test/test.dart';
import 'package:dart/robot_malvado.dart';

void main() {
  group('RobotMalvado', () {
    // Crear un robot malvado para los tests
    var robot = RobotMalvado('TestBot', 'Acero', 3);

    test('objetivo devuelve la cadena correcta', () {
      expect(robot.objetivo(), 'El objetivo de el robot malvado TestBot es destruir a los humanos y conquistar el mundo.');
    });

    test('dispararLaser disminuye el número de láseres y agrega una acción correcta', () {
      robot.dispararLaser();
      expect(robot.numLaseres, 2);
      expect(robot.acciones.last, 'PEW! El robot malvado TestBot dispara un láser. Le quedan 2 láseres.');
    });

    test('dispararLaser no disminuye el número de láseres por debajo de cero', () {
      robot.dispararLaser();
      robot.dispararLaser();
      robot.dispararLaser();
      expect(robot.numLaseres, 0);
      expect(robot.acciones.last, 'El robot malvado TestBot no tiene más láseres. Su conquista ha fracasado.');
    });

    test('mover agrega una acción correcta', () {
      robot.mover('adelante');
      expect(robot.acciones.last, 'TestBot moviéndose hacia adelante');
    });

    test('girar agrega una acción correcta', () {
      robot.girar('derecha');
      expect(robot.acciones.last, 'TestBot girando hacia derecha');
    });
  });
}