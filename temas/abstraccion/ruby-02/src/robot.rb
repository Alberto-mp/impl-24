require_relative 'hablador'

class Robot
  include Hablador

  attr_accessor :nombre, :num_laseres
  attr_reader :material

  def initialize(nombre, material, num_laseres)
    @nombre = nombre
    @material = material
    @num_laseres = num_laseres
  end

  def hablar
    "HOLA. SOY. UN. ROBOT. MI. NOMBRE. ES. #{@nombre} Y. ESTOY. HECHO. DE. #{@material}."
  end

  def disparar_laser
    if @num_laseres > 0
      @num_laseres -= 1
      "PEW! Ahora tengo #{@num_laseres} láseres restantes."
    else
      recargar_laseres
      "NO. TENGO. MAS. LASERES. #{@recarga_mensaje}"
    end
  end

  private

  def recargar_laseres
    laseres = rand(1..10)
    @num_laseres += laseres
    @recarga_mensaje = "RECARGANDO. LASERES. RECARGA. COMPLETA. AHORA. TENGO. #{@num_laseres} LASERES."
  end
end