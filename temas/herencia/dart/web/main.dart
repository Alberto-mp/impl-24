import 'dart:async';
import 'dart:html';
import 'package:dart/robot_inteligente.dart';
import 'package:dart/robot_malvado.dart';
import 'package:dart/persona.dart';

void main() {
  // Crear instancias de las clases
  var robotInteligente = RobotInteligente('RoboInt', 'Metal');
  var robotMalvado = RobotMalvado('RoboMal', 'Acero', 3);
  var persona = Persona('Juan', 30);

  // Obtener los elementos del DOM para Robot Inteligente
  var robotInteligenteOutput = querySelector('#robotInteligenteOutput') as DivElement;

  var robotInteligenteHablarButton = querySelector('#robotInteligenteHablar') as ButtonElement;
  var robotInteligenteMoverButton = querySelector('#robotInteligenteMover') as ButtonElement;
  var robotInteligenteMoverDireccion = querySelector('#robotInteligenteMoverDireccion') as InputElement;
  var robotInteligenteGirarButton = querySelector('#robotInteligenteGirar') as ButtonElement;
  var robotInteligenteGirarDireccion = querySelector('#robotInteligenteGirarDireccion') as InputElement;
  var robotInteligenteSumarButton = querySelector('#robotInteligenteSumar') as ButtonElement;
  var robotInteligenteSumarA = querySelector('#robotInteligenteSumarA') as InputElement;
  var robotInteligenteSumarB = querySelector('#robotInteligenteSumarB') as InputElement;
  var robotInteligenteRestarButton = querySelector('#robotInteligenteRestar') as ButtonElement;
  var robotInteligenteRestarA = querySelector('#robotInteligenteRestarA') as InputElement;
  var robotInteligenteRestarB = querySelector('#robotInteligenteRestarB') as InputElement;
  var robotInteligenteMultiplicarButton = querySelector('#robotInteligenteMultiplicar') as ButtonElement;
  var robotInteligenteMultiplicarA = querySelector('#robotInteligenteMultiplicarA') as InputElement;
  var robotInteligenteMultiplicarB = querySelector('#robotInteligenteMultiplicarB') as InputElement;
  var robotInteligenteDividirButton = querySelector('#robotInteligenteDividir') as ButtonElement;
  var robotInteligenteDividirA = querySelector('#robotInteligenteDividirA') as InputElement;
  var robotInteligenteDividirB = querySelector('#robotInteligenteDividirB') as InputElement;
  var robotInteligenteEjecutarButton = querySelector('#robotInteligenteEjecutar') as ButtonElement;

  // Obtener los elementos del DOM para Robot Malvado
  var robotMalvadoOutput = querySelector('#robotMalvadoOutput') as DivElement;

  var robotMalvadoDispararButton = querySelector('#robotMalvadoDisparar') as ButtonElement;
  var robotMalvadoMoverButton = querySelector('#robotMalvadoMover') as ButtonElement;
  var robotMalvadoMoverDireccion = querySelector('#robotMalvadoMoverDireccion') as InputElement;
  var robotMalvadoGirarButton = querySelector('#robotMalvadoGirar') as ButtonElement;
  var robotMalvadoGirarDireccion = querySelector('#robotMalvadoGirarDireccion') as InputElement;
  var robotMalvadoEjecutarButton = querySelector('#robotMalvadoEjecutar') as ButtonElement;

  // Obtener los elementos del DOM para Persona
  var personaHablarButton = querySelector('#personaHablarButton') as ButtonElement;
  var personaOutput = querySelector('#personaOutput') as DivElement;

  // Función para agregar contenido al DOM
  void addContent(DivElement output, String text) {
    var paragraph = ParagraphElement()..text = text;
    output.append(paragraph);
  }

  // Manejadores de eventos para Robot Inteligente
  robotInteligenteHablarButton.onClick.listen((event) {
    robotInteligente.hablar();
  });

  robotInteligenteMoverButton.onClick.listen((event) {
    var direccion = robotInteligenteMoverDireccion.value;
    robotInteligente.mover(direccion!);
  });

  robotInteligenteGirarButton.onClick.listen((event) {
    var direccion = robotInteligenteGirarDireccion.value;
    robotInteligente.girar(direccion!);
  });

  robotInteligenteSumarButton.onClick.listen((event) {
    var a = int.parse(robotInteligenteSumarA.value!);
    var b = int.parse(robotInteligenteSumarB.value!);
    robotInteligente.sumar(a, b);
  });

  robotInteligenteRestarButton.onClick.listen((event) {
    var a = int.parse(robotInteligenteRestarA.value!);
    var b = int.parse(robotInteligenteRestarB.value!);
    robotInteligente.restar(a, b);
  });

  robotInteligenteMultiplicarButton.onClick.listen((event) {
    var a = int.parse(robotInteligenteMultiplicarA.value!);
    var b = int.parse(robotInteligenteMultiplicarB.value!);
    robotInteligente.multiplicar(a, b);
  });

  robotInteligenteDividirButton.onClick.listen((event) {
    var a = int.parse(robotInteligenteDividirA.value!);
    var b = int.parse(robotInteligenteDividirB.value!);
    robotInteligente.dividir(a, b);
  });

  robotInteligenteEjecutarButton.onClick.listen((event) {
    robotInteligenteOutput.children.clear();
    var acciones = robotInteligente.ejecutarAcciones();
    for (var accion in acciones) {
      addContent(robotInteligenteOutput, accion);
    }
  });

  // Manejadores de eventos para Robot Malvado
  robotMalvadoDispararButton.onClick.listen((event) {
    robotMalvado.dispararLaser();
  });

  robotMalvadoMoverButton.onClick.listen((event) {
    var direccion = robotMalvadoMoverDireccion.value;
    robotMalvado.mover(direccion!);
  });

  robotMalvadoGirarButton.onClick.listen((event) {
    var direccion = robotMalvadoGirarDireccion.value;
    robotMalvado.girar(direccion!);
  });

  robotMalvadoEjecutarButton.onClick.listen((event) {
    robotMalvadoOutput.children.clear();
    var acciones = robotMalvado.ejecutarAcciones();
    for (var accion in acciones) {
      addContent(robotMalvadoOutput, accion);
    }
  });

  // Manejador de evento para Persona
  personaHablarButton.onClick.listen((event) {
    personaOutput.children.clear();

    // Capturar la salida de print usando un StringBuffer
    var buffer = StringBuffer();
    capturePrint(() {
      persona.hablar();
    }, buffer);

    // Agregar contenido capturado al output
    addContent(personaOutput, buffer.toString());
  });
}

void addContent(Element parent, String content) {
  parent.appendText(content);
}

void capturePrint(Function action, StringBuffer buffer) {
  // Guardar la referencia original de print
  var originalPrint = Zone.current[#print];

  // Redefinir print en la zona actual
  runZoned(() {
    action();
  }, zoneSpecification: ZoneSpecification(
    print: (self, parent, zone, line) {
      buffer.writeln(line);
    },
  ));
}

