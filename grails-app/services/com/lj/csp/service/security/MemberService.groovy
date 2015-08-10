package com.lj.csp.service.security

import com.lj.csp.bui.BuiUtil
import com.lj.csp.data.Member
import com.lj.csp.data.MemberAuthority
import com.lj.csp.data.Roles
import com.lj.csp.data.RolesAuthority
import com.lj.csp.enumCustom.ReCode

/**
 * 用户类服务，新增、修改、删除、更新密码
 */
class MemberService {
    def springSecurityService
    boolean transactional = false
    def save(params) {
        println params
        if(Member.findByUsername(params.username)){
            return  ReCode.MEMEBER_EXSITS
        }

        Member.withTransaction {status->
            try{
                if(!params.password)
                    params.password='111111'

                def roles=params.remove('roles')
                def member = new Member(params)
                if(roles){
                    def r=roles.split(',')
                    def role
                    r.each{
                        role= Roles.get(it as long)
                        if(role){
                            member.addToRoles(role)
                        }
                    }
                }
                if (!member.save(flush: true)) {
                    println member.errors
                    return ReCode.NEW_FAIL
                }else{


                    def raes
                    member.roles.each{role->

                        raes=RolesAuthority.findAllByRoles(role)
                        if(raes){

                            raes.each{ra->

                               MemberAuthority.create(member,ra.authority,true)
                           }


                        }

                    }
                    return ( [member:member] << ReCode.NEW_SUCCESS)
                }
            }catch(Exception e){
                println e
                status.setRollbackOnly()

                return ReCode.NEW_FAIL
            }
        }

    }


    def update(params) {
        //System.out.println("params->"+params);
        //参数处理
         if(!params.enabled){
             params.enabled=false;
         }
        if(!params.accountExpired){
            params.accountExpired=false;
        }
        if(!params.accountLocked){
            params.accountLocked=false;
        }
        if(!params.passwordExpired){
            params.passwordExpired=false;
        }
        //System.out.println("params1->"+params);
        def member = Member.get(params.id)
        if (!member) {
            return ReCode.NO_RECORD
        }

        if (params.version) {
            def version = params.version.toLong()
            if (member.version > version) {
                return ReCode.MANY_UPDATEING
            }
        }

        if(params.username!=member.username){
            if(Member.findByUsername(params.username)){
                return  ReCode.MEMEBER_EXSITS
            }
        }

        Member.withTransaction {status->
            try{
                if(!params.password)
                    params.remove('password')
                MemberAuthority.removeAll(member)
                member.roles.clear()
                def roles=params.remove('roles')
                member.properties = params
                if(roles){
                    def r=roles.split(',')
                    def role
                    r.each{
                        role= Roles.get(it as long)
                        if(role){
                            member.addToRoles(role)
                        }
                    }
                }

                if (!member.save(flush: true)) {
                    return ReCode.UPDATE_FAIL
                }

                def raes
                member.roles.each{role->
                    raes=RolesAuthority.findAllByRoles(role)
                    if(raes){
                        raes.each{ra->
                            MemberAuthority.create(member,ra.authority,true)
                        }
                    }

                }

                return  ReCode.UPDATE_SUCCESS
            }catch(Exception e){
                status.setRollbackOnly()
                println e
                return ReCode.UPDATE_FAIL
            }
        }
    }



    def modifyPass(params){
        def member = Member.get(params.id)
        if (!member) {
            return ReCode.NO_RECORD
        }

        if (params.version) {
            def version = params.version.toLong()
            if (member.version > version) {
                return ReCode.MANY_UPDATEING
            }
        }

        member.properties['password'] = params

        if (!member.save(flush: true)) {
            return ReCode.PASS_UPDATE_FAIL
        }

        return ReCode.PASS_UPDATE_SUCCESS
    }

    def delete(params) {
        def member = Member.get(params.ids)
        if (!member) {
            return ReCode.NO_RECORD
        }

        def currentUser=springSecurityService.currentUser;
        if(member==currentUser){
            return  ReCode.NOT_ALLOW_DEL
        }

        if(member.username=='dev'){
            return  ReCode.NOT_ALLOW_DEL
        }

        Member.withTransaction{status->
            try{
                MemberAuthority.removeAll(member)
                member.delete(flush: true)
                return ReCode.DEL_SUCCESS
            }catch(Exception ex){
                status.setRollbackOnly()
                println ex
                return ReCode.DEL_FAIL
            }
        }

    }

    def updRoles(params){
        if(!params.roles || params.id)
            return ReCode.UPDATE_ROLE_FAIL

        def member=Member.get(params.id as long)
        if(!member)
            return ReCode.UPDATE_ROLE_FAIL

        RolesAuthority.withTransaction {
            try{
                MemberAuthority.removeAll(member)
                Roles.findAllByMember(member:member).each{roles->
                    MemberAuthority.create(member,roles.rolesAuthority.authority,true)
                }
                return ReCode.UPDATE_ROLE_SUCCESS
            }catch(Exception ex){
                println ex
                return ReCode.UPDATE_ROLE_FAIL
            }
        }

    }

    def findMember(def params){
        def query={p->
             return Member.createCriteria().list(p){
                 notEqual('username','dev')
                 if(p.username){
                     ilike('username',"%${p.username}%")
                 }

             }
        }

        def format={member->
            [
                    id:member.id,
                    username:member.username,
                    realName:member.realName,
                    enabled:member.enabled?'on':'off',
                    accountExpired:member.accountExpired?'on':'off',
                    accountLocked:member.accountLocked?'on':'off',
                    passwordExpired:member.passwordExpired?'on':'off',
                    roles:member.roles.collect{it.id}.join(',')
            ]

        }

        return BuiUtil.createForJson(params,query,format)
    }


    def changePass(def params){
       if(!params.oldPass || !params.newPass ||!params.newPass1){
            return ReCode.PARAMETERS_ERROR
       }

       if(params.newPass !=params.newPass1){
           return ReCode.PASSWORD_DIFF
       }

        def member = springSecurityService.currentUser

        def n=springSecurityService.encodePassword(params.oldPass)
        if(n!=member.password){
            return ReCode.OLD_ERROR
        }

        member.password=params.newPass
        if(!member.save(flush:true))
            return ReCode.UPD_PWD_FAIL

        return ReCode.UPD_PWD_SUCCESS

    }

}
