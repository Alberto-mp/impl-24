require 'minitest/autorun'
require_relative '../src/Clan'

class ClanTest < Minitest::Test
    def setup
        @clan = Clan.new
        @tropa1 = Bruja.new('Bruja 1')
        @tropa2 = Arquera.new('Arquera 1')
        @tropa3 = Gigante.new('Gigante 1')
        @tropa4 = Bruja.new('Bruja 2')
        @tropa5 = Arquera.new('Arquera 2')
        @tropa6 = Gigante.new('Gigante 2')
    end

    def test_to_s
        assert_equal 'Bruja 1', @tropa1.to_s
        assert_equal 'Arquera 1', @tropa2.to_s
        assert_equal 'Gigante 1', @tropa3.to_s
    end

    def test_usar_habilidad
        assert_equal "Bruja Bruja 1 lanza una bola de fuego al enemigo",  @tropa1.usar_habilidad 
        assert_equal "Arquera Arquera 1 dispara una flecha envenenada al enemigo",  @tropa2.usar_habilidad 
        assert_equal "Gigante Gigante 1 golpea al enemigo con su maza", @tropa3.usar_habilidad
    end

    def test_add_tropa_ataque
        @clan.add_tropa_ataque(@tropa1)
        @clan.add_tropa_ataque(@tropa2)
        @clan.add_tropa_ataque(@tropa3)

        assert_equal 3, @clan.tropas_ataque.size
        assert_equal 'Bruja 1', @clan.tropas_ataque[0].to_s
        assert_equal 'Arquera 1', @clan.tropas_ataque[1].to_s
        assert_equal 'Gigante 1', @clan.tropas_ataque[2].to_s
    end

    def test_add_tropa_defensa
        @clan.add_tropa_defensa(@tropa4)
        @clan.add_tropa_defensa(@tropa5)
        @clan.add_tropa_defensa(@tropa6)

        assert_equal 3, @clan.tropas_defensa.size
        assert_equal 'Bruja 2', @clan.tropas_defensa[0].to_s
        assert_equal 'Arquera 2', @clan.tropas_defensa[1].to_s
        assert_equal 'Gigante 2', @clan.tropas_defensa[2].to_s
    end

    def test_atacar
        @clan.add_tropa_ataque(@tropa1)
        @clan.add_tropa_ataque(@tropa2)
        @clan.add_tropa_ataque(@tropa3)

        expected_output = [
      "Bruja Bruja 1 avanza y ataca al enemigo",
      "Bruja Bruja 1 lanza una bola de fuego al enemigo",
      "Arquera Arquera 1 avanza y ataca al enemigo",
      "Arquera Arquera 1 dispara una flecha envenenada al enemigo",
      "Gigante Gigante 1 avanza y ataca al enemigo",
      "Gigante Gigante 1 golpea al enemigo con su maza"
    ]
        assert_equal expected_output, @clan.atacar
    end

    def test_defender
        @clan.add_tropa_defensa(@tropa4)
        @clan.add_tropa_defensa(@tropa5)
        @clan.add_tropa_defensa(@tropa6)

        expected_output = [
      "Bruja Bruja 2 se retira a defender la base",
      "Bruja Bruja 2 lanza una bola de fuego al enemigo",
      "Arquera Arquera 2 se retira a defender la base",
      "Arquera Arquera 2 dispara una flecha envenenada al enemigo",
      "Gigante Gigante 2 se retira a defender la base",
      "Gigante Gigante 2 golpea al enemigo con su maza"
    ]
        assert_equal expected_output, @clan.defender
    end
end
