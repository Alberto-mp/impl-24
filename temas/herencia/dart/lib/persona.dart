import 'hablador.dart';

class Persona with Hablador {
  String nombre;
  int edad; 

  Persona(this.nombre, this.edad);

  @override
  void hablar() {
    print("Hola, mi nombre es $nombre y tengo $edad años.");
  }
}