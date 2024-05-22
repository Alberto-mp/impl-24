abstract class Robot {
  String nombre;
  String material;
  final List<String> acciones = [];

  Robot(this.nombre, this.material);

  //Acciones de cualquier robot

  void mover(String direccion) {
    acciones.add("$nombre moviéndose hacia $direccion");
  }

  void girar(String direccion) {
    acciones.add("$nombre girando hacia $direccion");
  }

  // Ejecutar las acciones del robot

  List<String> ejecutarAcciones() {
    return acciones;
  }

  //Método abstracto
  String objetivo();
}
