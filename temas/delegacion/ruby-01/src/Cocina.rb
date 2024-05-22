require 'active_support/core_ext/module/delegation'

class Horno
    def calentar(comida)
        "Calentando #{comida}..."
    end
end

class Cocina
    delegate :calentar, to: :@horno

    def initialize(horno)
        @horno = horno
    end

    def cocinar(comida)
        "Preparando ingredientes... " + calentar(comida) + " El plato está listo."
    end
end