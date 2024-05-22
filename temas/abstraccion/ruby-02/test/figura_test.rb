require 'minitest/autorun'  # Requiere la gema Minitest y carga las asunciones por defecto
require_relative '../src/figura'  # Requiere el archivo que se va a probar

class TestFigura < Minitest::Test
  def test_rectangulo
    rectangulo = Rectangulo.new(4, 5)
    assert_equal 20, rectangulo.area
  end

  def test_triangulo
    triangulo = Triangulo.new(4, 6)
    assert_equal 12.0, triangulo.area
  end
end