package com.lj.csp.service.security

import com.lj.csp.data.Authority
import com.lj.csp.data.MemberAuthority
import com.lj.csp.data.Roles
import com.lj.csp.data.RolesAuthority
import com.lj.csp.enumCustom.ReCode


class RolesService {
    boolean transactional = false
    def save(params) {
        Roles.withTransaction {status->
            try{
                def roles = new Roles(params)
                if(!roles.save(flush: true)){
                    return ReCode.NEW_FAIL
                }
                if(params.authorities){
                   def authority
                   params.authorities.split(",").each{
                       authority=Authority.get(it as long)
                       if(authority){
                           new RolesAuthority(roles:roles,authority: authority).save(flush:true)
                       }
                   }
                }

                return ([roles:roles] << ReCode.NEW_SUCCESS)
            }catch(Exception e){
                status.setRollbackOnly()
                return ReCode.NEW_FAIL
            }
        }
    }

    def update(params) {
        def roles = Roles.get(params.id)
        if (!roles) {
            return ReCode.NO_RECORD
        }

        if (params.version) {
            def version = params.version.toLong()
            if (roles.version > version) {
                return ReCode.MANY_UPDATEING
            }
        }

        Roles.withTransaction {status->
           try{
               roles.properties = params

               if (!roles.save(flush: true)) {
                   return ReCode.UPDATE_FAIL
               }

               //删除该角色所有的权限
               RolesAuthority.findAllByRoles(roles).each{
                   it.delete(flush:true)
               }

               //添加该角色对应的权限
               if(params.authorities) {
                   params.authorities.split(',').each{a->
                       def authority=Authority.get(a as long)
                       if(authority){
                           RolesAuthority.create(roles,authority,true)
                       }
                   }
               }

               def members=roles.member
               if(members){
                   members.each{member->
                       MemberAuthority.removeAll(member)
                       member.roles?.each{r->
                           r.rolesAuthority.collect{it.authority}.each{auth->
                               if(!MemberAuthority.findByMemberAndAuthority(member,auth))
                                   MemberAuthority.create(member,auth,true)
                           }
                       }
                   }
               }
               return ReCode.UPDATE_ROLE_SUCCESS

           }catch(Exception e){
               println e
               status.setRollbackOnly()
               return ReCode.NEW_FAIL
           }
        }
        return ReCode.UPDATE_SUCCESS
    }


    def delete(params) {
        def roles = Roles.get(params.ids)
        if (!roles) {
            return ReCode.NO_RECORD
        }

        Roles.withTransaction{status->
            try{
                RolesAuthority.where{
                    roles==Roles.load(roles.id)
                }.deleteAll()

                def members=roles.member
                if(members){
                    members.each{member->
                        MemberAuthority.removeAll(member)
                        member.roles?.each{r->
                            r.rolesAuthority.collect{it.authority}.each{authority->
                                MemberAuthority.create(member,authority,true)
                            }
                        }
                    }
                }

                roles.delete(flush: true)
                return ReCode.DEL_SUCCESS
            }catch(Exception ex){
                status.setRollbackOnly()
                println  ex
                return ReCode.DEL_FAIL
            }
        }
    }


    def findRoles(){
        return Roles.findAll().collect{
            it.getData()
        }
    }

}
