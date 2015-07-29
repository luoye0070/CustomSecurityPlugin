package com.lj.csp.service.security

import com.lj.csp.data.Authority
import com.lj.csp.data.MemberAuthority
import com.lj.csp.data.Requestmap
import com.lj.csp.data.RolesAuthority
import com.lj.csp.enumCustom.ReCode
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

class AuthorityService {

    def springSecurityService
    boolean transactional = false
    def save(params) {
        def authority = new Authority(params)
        if (!authority.save(flush: true)) {
            return ReCode.NEW_FAIL
        }

        return ([authority:authority] << ReCode.NEW_SUCCESS)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    def updateTran(authority,params){
        if (!springSecurityService.updateRole(authority, params)) {
            return ReCode.UPDATE_FAIL
        }
        return ReCode.UPDATE_SUCCESS
    }

    def update(params) {
        def authority = Authority.get(params.id)
        if (!authority) {
            return ReCode.NO_RECORD
        }

        if (params.version) {
            def version = params.version.toLong()
            if (authority.version > version) {
                return ReCode.MANY_UPDATEING
            }
        }

        authority.properties = params

        Requestmap.withTransaction{status->
            try{
                return updateTran(authority,params)
            }catch(Exception ex){
                status.setRollbackOnly()
                return ReCode.UPDATE_FAIL
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    def deleteTran(authority){
        MemberAuthority.executeUpdate("delete from MemberAuthority where authority=?",[authority])
        RolesAuthority.executeUpdate("delete from RolesAuthority where authority=?",[authority])
        springSecurityService.deleteRole (authority)
    }

    def delete(params) {
        def authority = Authority.get(params.ids)
        if (!authority) {
            return ReCode.NO_RECORD
        }

        Requestmap.withTransaction{status->
            try{
                deleteTran(authority)
                return ReCode.DEL_SUCCESS
            }catch(Exception ex){
                status.setRollbackOnly()
                println ex
                return ReCode.DEL_FAIL
            }
        }

    }

    def findAuthorities(){
        return Authority.findAll().collect{
            it.getData()
        }
    }
}
