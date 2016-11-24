package midrashontheweb

import grails.test.mixin.*
import spock.lang.*

@TestFor(ShiurController)
@Mock(Shiur)
class ShiurControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.shiurList
            model.shiurCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.shiur!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def shiur = new Shiur()
            shiur.validate()
            controller.save(shiur)

        then:"The create view is rendered again with the correct model"
            model.shiur!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            shiur = new Shiur(params)

            controller.save(shiur)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/shiur/show/1'
            controller.flash.message != null
            Shiur.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def shiur = new Shiur(params)
            controller.show(shiur)

        then:"A model is populated containing the domain instance"
            model.shiur == shiur
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def shiur = new Shiur(params)
            controller.edit(shiur)

        then:"A model is populated containing the domain instance"
            model.shiur == shiur
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/shiur/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def shiur = new Shiur()
            shiur.validate()
            controller.update(shiur)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.shiur == shiur

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            shiur = new Shiur(params).save(flush: true)
            controller.update(shiur)

        then:"A redirect is issued to the show action"
            shiur != null
            response.redirectedUrl == "/shiur/show/$shiur.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/shiur/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def shiur = new Shiur(params).save(flush: true)

        then:"It exists"
            Shiur.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(shiur)

        then:"The instance is deleted"
            Shiur.count() == 0
            response.redirectedUrl == '/shiur/index'
            flash.message != null
    }
}
