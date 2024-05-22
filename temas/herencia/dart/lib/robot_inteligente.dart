import 'robot.dart';
import 'hablador.dart';

class RobotInteligente extends Robot with Hablador {
  RobotInteligente(super.nombre, super.material);

  @override
  String objetivo() {
    return "El objetivo de el robot inteligente $nombre es comunicarse con humanos y realizar cálculos complejos.";
  }

  @override
  void hablar() {
    acciones.add("HOLA. SOY. UN. ROBOT. INTELIGENTE. Y. PUEDO. HABLAR. CON. LOS. HUMANOS. MI. NOMBRE. ES. $nombre. Y. ESTOY. HECHO. DE. $material.");
  }

  void sumar(int a, int b) {
    acciones.add("$nombre sumando $a y $b, el resultado es ${a + b}");
  }

  void restar(int a, int b) {
    acciones.add("$nombre restando $a y $b, el resultado es ${a - b}");
  }

  void multiplicar(int a, int b) {
    acciones.add("$nombre multiplicando $a y $b, el resultado es ${a * b}");
  }

  void dividir(int a, int b) {
    if (b == 0) {
      acciones.add("$nombre no puede dividir por cero");
    } else {
      acciones.add("$nombre dividiendo $a y $b, el resultado es ${a / b}");
    }
  }
}