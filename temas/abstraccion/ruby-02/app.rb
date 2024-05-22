require 'sinatra'
require_relative 'src/robot'
require_relative 'src/hablador'
require_relative 'src/figura'

# Crear una instancia de Robot como una variable global
$robot = Robot.new("Robbie", "Acero", 3)

# Ruta principal que muestra las opciones disponibles
get '/' do
  erb :index
end

# Ruta para el método hablar
get '/hablar' do
  @mensaje = $robot.hablar
  erb :resultado
end

# Ruta para el método disparar_laser
get '/disparar' do
  @mensaje = $robot.disparar_laser
  erb :resultado
end

# Ruta para el método saludar del módulo Hablador
get '/saludar' do
  @mensaje = $robot.saludar
  erb :resultado
end

# Ruta para mostrar el formulario de cálculo de área
get '/calcular_area' do
  erb :calcular_area
end

# Ruta para procesar el formulario y calcular el área
post '/calcular_area' do
  tipo = params[:tipo]
  base = params[:base].to_f
  altura = params[:altura].to_f

  if tipo == "rectangulo"
    figura = Rectangulo.new(base, altura)
  elsif tipo == "triangulo"
    figura = Triangulo.new(base, altura)
  end

  @mensaje = "El área del #{tipo} es #{figura.area}"
  erb :resultado
end

__END__

@@ index
<!doctype html>
<html>
  <head>
    <title>Robot Controller</title>
  </head>
  <body>
    <h1>Controla a tu Robot</h1>
    <ul>
      <li><a href="/hablar">Hablar</a></li>
      <li><a href="/disparar">Disparar Láser</a></li>
      <li><a href="/saludar">Saludar</a></li>
      <li><a href="/calcular_area">Calcular Área de Figuras</a></li>
    </ul>
  </body>
</html>

@@ calcular_area
<!doctype html>
<html>
  <head>
    <title>Calcular Área</title>
  </head>
  <body>
    <h1>Calcular Área de una Figura</h1>
    <form action="/calcular_area" method="post">
      <label for="tipo">Tipo de figura:</label>
      <select name="tipo" id="tipo">
        <option value="rectangulo">Rectángulo</option>
        <option value="triangulo">Triángulo</option>
      </select><br><br>
      <label for="base">Base:</label>
      <input type="text" name="base" id="base"><br><br>
      <label for="altura">Altura:</label>
      <input type="text" name="altura" id="altura"><br><br>
      <input type="submit" value="Calcular">
    </form>
    <a href="/">Volver</a>
  </body>
</html>

@@ resultado
<!doctype html>
<html>
  <head>
    <title>Resultado</title>
  </head>
  <body>
    <h1>Resultado</h1>
    <p><%= @mensaje %></p>
    <a href="/">Volver</a>
  </body>
</html>

