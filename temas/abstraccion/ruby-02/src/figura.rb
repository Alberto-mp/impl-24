class Figura
    def area
      calcular_area
    end
  
    protected
  
    def calcular_area
      # Método protegido que se espera que las subclases implementen
      raise NotImplementedError, "El método 'calcular_area' debe ser implementado por las subclases."
    end
  end
  
  class Rectangulo < Figura
    def initialize(base, altura)
      @base = base
      @altura = altura
    end
  
    protected
  
    def calcular_area
      @base * @altura
    end
  end
  
  class Triangulo < Figura
    def initialize(base, altura)
      @base = base
      @altura = altura
    end
  
    protected
  
    def calcular_area
      @base * @altura / 2.0
    end
  end