package midrashontheweb

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class HorarioController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Horario.list(params), model:[horarioCount: Horario.count()]
    }

    def show(Horario horario) {
        respond horario
    }

    def create() {
        respond new Horario(params)
    }

    @Transactional
    def save(Horario horario) {
        if (horario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (horario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond horario.errors, view:'create'
            return
        }

        horario.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'horario.label', default: 'Horario'), horario.id])
                redirect horario
            }
            '*' { respond horario, [status: CREATED] }
        }
    }

    def edit(Horario horario) {
        respond horario
    }

    @Transactional
    def update(Horario horario) {
        if (horario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (horario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond horario.errors, view:'edit'
            return
        }

        horario.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'horario.label', default: 'Horario'), horario.id])
                redirect horario
            }
            '*'{ respond horario, [status: OK] }
        }
    }

    @Transactional
    def delete(Horario horario) {

        if (horario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        horario.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'horario.label', default: 'Horario'), horario.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'horario.label', default: 'Horario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
