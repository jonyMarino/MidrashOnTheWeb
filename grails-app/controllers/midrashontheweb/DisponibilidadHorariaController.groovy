package midrashontheweb

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DisponibilidadHorariaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DisponibilidadHoraria.list(params), model:[disponibilidadHorariaCount: DisponibilidadHoraria.count()]
    }

    def show(DisponibilidadHoraria disponibilidadHoraria) {
        respond disponibilidadHoraria
    }

    def create() {
        respond new DisponibilidadHoraria(params)
    }

    @Transactional
    def save(DisponibilidadHoraria disponibilidadHoraria) {
        if (disponibilidadHoraria == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (disponibilidadHoraria.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond disponibilidadHoraria.errors, view:'create'
            return
        }

        disponibilidadHoraria.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'disponibilidadHoraria.label', default: 'DisponibilidadHoraria'), disponibilidadHoraria.id])
                redirect disponibilidadHoraria
            }
            '*' { respond disponibilidadHoraria, [status: CREATED] }
        }
    }

    def edit(DisponibilidadHoraria disponibilidadHoraria) {
        respond disponibilidadHoraria
    }

    @Transactional
    def update(DisponibilidadHoraria disponibilidadHoraria) {
        if (disponibilidadHoraria == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (disponibilidadHoraria.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond disponibilidadHoraria.errors, view:'edit'
            return
        }

        disponibilidadHoraria.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'disponibilidadHoraria.label', default: 'DisponibilidadHoraria'), disponibilidadHoraria.id])
                redirect disponibilidadHoraria
            }
            '*'{ respond disponibilidadHoraria, [status: OK] }
        }
    }

    @Transactional
    def delete(DisponibilidadHoraria disponibilidadHoraria) {

        if (disponibilidadHoraria == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        disponibilidadHoraria.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'disponibilidadHoraria.label', default: 'DisponibilidadHoraria'), disponibilidadHoraria.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'disponibilidadHoraria.label', default: 'DisponibilidadHoraria'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
