mixin Hablador{
  void hablar() {
    throw UnimplementedError("Debe implementar el método en la clase que use este mixin");
  }
}

void hacerHablar(Hablador hablador) {
  hablador.hablar();
}