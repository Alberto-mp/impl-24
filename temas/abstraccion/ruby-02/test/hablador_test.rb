require 'minitest/autorun'  # Requiere la gema Minitest y carga las asunciones por defecto
require_relative '../src/hablador'  # Requiere el archivo que se va a probar

class TestHablador < Minitest::Test
  class ClaseDePrueba
    include Hablador
  end

  def setup
    @instancia = ClaseDePrueba.new
  end

  def test_hablar
    assert_raises(NotImplementedError) { @instancia.hablar }
  end

  def test_saludar
    assert_equal "HOLA!", @instancia.saludar
  end
end