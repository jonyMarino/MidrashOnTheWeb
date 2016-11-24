package midrashontheweb

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ShiurController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Shiur.list(params), model:[shiurCount: Shiur.count()]
    }

    def show(Shiur shiur) {
        respond shiur
    }

    def create() {
        respond new Shiur(params)
    }

    @Transactional
    def save(Shiur shiur) {
        if (shiur == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (shiur.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond shiur.errors, view:'create'
            return
        }

        shiur.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'shiur.label', default: 'Shiur'), shiur.id])
                redirect shiur
            }
            '*' { respond shiur, [status: CREATED] }
        }
    }

    def edit(Shiur shiur) {
        respond shiur
    }

    @Transactional
    def update(Shiur shiur) {
        if (shiur == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (shiur.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond shiur.errors, view:'edit'
            return
        }

        shiur.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'shiur.label', default: 'Shiur'), shiur.id])
                redirect shiur
            }
            '*'{ respond shiur, [status: OK] }
        }
    }

    @Transactional
    def delete(Shiur shiur) {

        if (shiur == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        shiur.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'shiur.label', default: 'Shiur'), shiur.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiur.label', default: 'Shiur'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
