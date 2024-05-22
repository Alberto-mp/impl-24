import 'robot.dart';

class RobotMalvado extends Robot {
  int numLaseres;

  RobotMalvado(super.nombre, super.material, this.numLaseres);

  @override
  String objetivo() {
    return "El objetivo de el robot malvado $nombre es destruir a los humanos y conquistar el mundo.";
  }

  void dispararLaser() {
    if (numLaseres > 0) {
      numLaseres--;
      acciones.add("PEW! El robot malvado $nombre dispara un láser. Le quedan $numLaseres láseres.");
    } else {
      acciones.add("El robot malvado $nombre no tiene más láseres. Su conquista ha fracasado.");
    }
  }
}