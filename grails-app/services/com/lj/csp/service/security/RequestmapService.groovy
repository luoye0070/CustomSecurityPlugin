package com.lj.csp.service.security

import com.lj.csp.data.Requestmap
import com.lj.csp.enumCustom.ReCode
import org.springframework.dao.DataIntegrityViolationException

class RequestmapService {

    def springSecurityService

    def save(params) {

        def requestmapInstance = new Requestmap(params)
        if (!requestmapInstance.save(flush: true)) {
            return ReCode.NEW_FAIL
        }


        springSecurityService.clearCachedRequestmaps()

        return (  [requestmap:requestmapInstance] << ReCode.NEW_SUCCESS )
    }


    def update(params) {

        def requestmapInstance = Requestmap.get(params.id)
        if (!requestmapInstance) {
            return ReCode.NO_RECORD
        }

        if (params.version) {
            def version = params.version.toLong()
            if (requestmapInstance.version > version) {
               return ReCode.MANY_UPDATEING
            }
        }

        requestmapInstance.properties = params

        if (!requestmapInstance.save(flush: true)) {
            return ReCode.UPDATE_FAIL
        }

        springSecurityService.clearCachedRequestmaps()

        return ([request:requestmapInstance] << ReCode.UPDATE_SUCCESS)
    }

    def delete(params) {
        try {
            Requestmap.executeUpdate("delete from Requestmap where id in (${params.ids})")
            springSecurityService.clearCachedRequestmaps()
            return ReCode.DEL_SUCCESS
        }
        catch (DataIntegrityViolationException e) {
            return ReCode.DEL_FAIL
        }
    }

    def findAll(){
        return Requestmap.findAll().collect{
            it.getData()
        }
    }

}
