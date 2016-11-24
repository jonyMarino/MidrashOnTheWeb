package midrashontheweb

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MoreController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond More.list(params), model:[moreCount: More.count()]
    }

    def show(More more) {
        respond more
    }

    def create() {
        respond new More(params)
    }

    @Transactional
    def save(More more) {
        if (more == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (more.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond more.errors, view:'create'
            return
        }

        more.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'more.label', default: 'More'), more.id])
                redirect more
            }
            '*' { respond more, [status: CREATED] }
        }
    }

    def edit(More more) {
        respond more
    }

    @Transactional
    def update(More more) {
        if (more == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (more.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond more.errors, view:'edit'
            return
        }

        more.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'more.label', default: 'More'), more.id])
                redirect more
            }
            '*'{ respond more, [status: OK] }
        }
    }

    @Transactional
    def delete(More more) {

        if (more == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        more.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'more.label', default: 'More'), more.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'more.label', default: 'More'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
