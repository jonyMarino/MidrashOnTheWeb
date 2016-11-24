package midrashontheweb

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Horario)
class HorarioSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
		MediaHora mediaHora = new MediaHora ( hora: 19, mediaHora: RANGO0A30)
        Horario horario1 = new Horario(dia : LUNES, hora: mediaHora)
		Horario horario2 = new Horario(dia : LUNES, hora: mediaHora)
		assert horario1.incluye(horario2) == true
    }
}
