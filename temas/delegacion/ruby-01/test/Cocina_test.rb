require 'minitest/autorun' # Requiere la gema Minitest y carga las asunciones por defecto
require_relative '../src/Cocina' # Requiere el archivo que se va a probar

class CocinaTest < Minitest::Test
    def setup
        @horno = Horno.new
        @cocina = Cocina.new(@horno)
    end

    def test_calentar
        assert_equal "Calentando pizza...", @cocina.calentar("pizza")
    end

    def test_cocinar
        assert_equal "Preparando ingredientes... Calentando pizza... El plato está listo.", @cocina.cocinar("pizza")
    end
end