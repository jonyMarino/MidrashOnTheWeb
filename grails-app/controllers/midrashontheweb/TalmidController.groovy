package midrashontheweb

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TalmidController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Talmid.list(params), model:[talmidCount: Talmid.count()]
    }

    def show(Talmid talmid) {
        respond talmid
    }

    def create() {
        respond new Talmid(params)
    }

    @Transactional
    def save(Talmid talmid) {
        if (talmid == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (talmid.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond talmid.errors, view:'create'
            return
        }

        talmid.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'talmid.label', default: 'Talmid'), talmid.id])
                redirect talmid
            }
            '*' { respond talmid, [status: CREATED] }
        }
    }

    def edit(Talmid talmid) {
        respond talmid
    }

    @Transactional
    def update(Talmid talmid) {
        if (talmid == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (talmid.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond talmid.errors, view:'edit'
            return
        }

        talmid.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'talmid.label', default: 'Talmid'), talmid.id])
                redirect talmid
            }
            '*'{ respond talmid, [status: OK] }
        }
    }

    @Transactional
    def delete(Talmid talmid) {

        if (talmid == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        talmid.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'talmid.label', default: 'Talmid'), talmid.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'talmid.label', default: 'Talmid'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
