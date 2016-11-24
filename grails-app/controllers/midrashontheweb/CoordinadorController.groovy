package midrashontheweb

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CoordinadorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Coordinador.list(params), model:[coordinadorCount: Coordinador.count()]
    }

    def show(Coordinador coordinador) {
        respond coordinador
    }

    def create() {
        respond new Coordinador(params)
    }

    @Transactional
    def save(Coordinador coordinador) {
        if (coordinador == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (coordinador.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond coordinador.errors, view:'create'
            return
        }

        coordinador.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'coordinador.label', default: 'Coordinador'), coordinador.id])
                redirect coordinador
            }
            '*' { respond coordinador, [status: CREATED] }
        }
    }

    def edit(Coordinador coordinador) {
        respond coordinador
    }

    @Transactional
    def update(Coordinador coordinador) {
        if (coordinador == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (coordinador.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond coordinador.errors, view:'edit'
            return
        }

        coordinador.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'coordinador.label', default: 'Coordinador'), coordinador.id])
                redirect coordinador
            }
            '*'{ respond coordinador, [status: OK] }
        }
    }

    @Transactional
    def delete(Coordinador coordinador) {

        if (coordinador == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        coordinador.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'coordinador.label', default: 'Coordinador'), coordinador.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'coordinador.label', default: 'Coordinador'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
