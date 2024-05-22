require 'sinatra'
require_relative 'src/Clan'
require_relative 'src/Cocina'

# Crear una instancia de Clan y Cocina como variables globales
$clan = Clan.new
$horno = Horno.new
$cocina = Cocina.new($horno)

# Rutas para la página principal
get '/' do
  erb :index
end

# Ruta para agregar tropas de ataque
post '/add_tropa_ataque' do
  tipo = params[:tipo]
  nombre = params[:nombre]

  case tipo
  when 'bruja'
    tropa = Bruja.new(nombre)
  when 'arquera'
    tropa = Arquera.new(nombre)
  when 'gigante'
    tropa = Gigante.new(nombre)
  end

  $clan.add_tropa_ataque(tropa) if tropa
  redirect '/'
end

# Ruta para agregar tropas de defensa
post '/add_tropa_defensa' do
  tipo = params[:tipo]
  nombre = params[:nombre]

  case tipo
  when 'bruja'
    tropa = Bruja.new(nombre)
  when 'arquera'
    tropa = Arquera.new(nombre)
  when 'gigante'
    tropa = Gigante.new(nombre)
  end

  $clan.add_tropa_defensa(tropa) if tropa
  redirect '/'
end

# Ruta para atacar
get '/atacar' do
  @accion = 'Atacar'
  @mensajes = $clan.atacar
  erb :accion
end

# Ruta para defender
get '/defender' do
  @accion = 'Defender'
  @mensajes = $clan.defender
  erb :accion
end

# Ruta para cocinar
post '/cocinar' do
  comida = params[:comida]
  @mensaje = $cocina.cocinar(comida)
  erb :resultado
end

__END__

@@ index
<!doctype html>
<html>
<head>
  <title>Clan Controller</title>
</head>
<body>
  <h1>Controla tu Clan</h1>
  <h2>Agregar Tropa de Ataque</h2>
  <form action="/add_tropa_ataque" method="post">
    <label for="tipo">Tipo de tropa:</label>
    <select name="tipo" id="tipo">
      <option value="bruja">Bruja</option>
      <option value="arquera">Arquera</option>
      <option value="gigante">Gigante</option>
    </select><br><br>
    <label for="nombre">Nombre:</label>
    <input type="text" name="nombre" id="nombre"><br><br>
    <input type="submit" value="Agregar Tropa de Ataque">
  </form>

  <h2>Agregar Tropa de Defensa</h2>
  <form action="/add_tropa_defensa" method="post">
    <label for="tipo">Tipo de tropa:</label>
    <select name="tipo" id="tipo">
      <option value="bruja">Bruja</option>
      <option value="arquera">Arquera</option>
      <option value="gigante">Gigante</option>
    </select><br><br>
    <label for="nombre">Nombre:</label>
    <input type="text" name="nombre" id="nombre"><br><br>
    <input type="submit" value="Agregar Tropa de Defensa">
  </form>

  <h2>Acciones</h2>
  <a href="/atacar">Atacar</a><br>
  <a href="/defender">Defender</a>

  <h2>Cocina</h2>
  <form action="/cocinar" method="post">
    <label for="comida">Comida:</label>
    <input type="text" name="comida" id="comida"><br><br>
    <input type="submit" value="Cocinar">
  </form>
</body>
</html>

@@ accion
<!doctype html>
<html>
<head>
  <title><%= @accion %></title>
</head>
<body>
  <h1><%= @accion %> del Clan</h1>
  <% @mensajes.each do |mensaje| %>
    <p><%= mensaje %></p>
  <% end %>
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

