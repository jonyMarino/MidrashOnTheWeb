package midrashontheweb

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TemaDeShiurController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TemaDeShiur.list(params), model:[temaDeShiurCount: TemaDeShiur.count()]
    }

    def show(TemaDeShiur temaDeShiur) {
        respond temaDeShiur
    }

    def create() {
        respond new TemaDeShiur(params)
    }

    @Transactional
    def save(TemaDeShiur temaDeShiur) {
        if (temaDeShiur == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (temaDeShiur.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond temaDeShiur.errors, view:'create'
            return
        }

        temaDeShiur.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'temaDeShiur.label', default: 'TemaDeShiur'), temaDeShiur.id])
                redirect temaDeShiur
            }
            '*' { respond temaDeShiur, [status: CREATED] }
        }
    }

    def edit(TemaDeShiur temaDeShiur) {
        respond temaDeShiur
    }

    @Transactional
    def update(TemaDeShiur temaDeShiur) {
        if (temaDeShiur == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (temaDeShiur.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond temaDeShiur.errors, view:'edit'
            return
        }

        temaDeShiur.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'temaDeShiur.label', default: 'TemaDeShiur'), temaDeShiur.id])
                redirect temaDeShiur
            }
            '*'{ respond temaDeShiur, [status: OK] }
        }
    }

    @Transactional
    def delete(TemaDeShiur temaDeShiur) {

        if (temaDeShiur == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        temaDeShiur.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'temaDeShiur.label', default: 'TemaDeShiur'), temaDeShiur.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'temaDeShiur.label', default: 'TemaDeShiur'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
