package com.lj.csp.controller.page.securitymanage

import com.lj.csp.data.Authority
import com.lj.csp.data.Roles
import grails.converters.JSON
/**
 * Title:用户总控制器
 * Description:处理用户相关的前台逻辑
 * @author: 王刚
 * @create: 2015-03-27
 * @edit:
 *
 */
class UserApiController {

    def userApiService
    def requestmapService
    def memberService
    def rolesService
    def authorityService

    //用户总界面
    def index() {

    }

    def member(){
        def rs=[:]
        Roles.findAll().each{
            rs << ["${it.id}":"${it.name}"]
        }

        render(view:'member',model:[roles:(rs as JSON)])
    }


    def requestmap(){
       render(view:'requsetmap')
    }

    def findAllRequestmap(){
        //有start pageIndex
        render requestmapService.findAll()  as JSON
    }

    def saveRequestmap(def params){
        println params
        render requestmapService.save(params) as JSON
    }

    def updRequestmap(def params){
        render requestmapService.update(params) as JSON
    }

    def delRequestmap(def params){
        render requestmapService.delete(params) as JSON
    }

    def findMember(){

        render memberService.findMember(params) as JSON
    }

    def saveMember(){
        render memberService.save(params) as JSON
    }

    def updMember(){
        render memberService.update(params) as JSON
    }

    def delMember(){
        render memberService.delete(params) as JSON
    }

    def modifyPass(){
        render memberService.modifyPass(params) as JSON
    }

    def updMemberRoles(){
        render memberService.updRoles(params) as JSON
    }

    def roles(){
        def rs=[:]
        Authority.findAll().each{
            rs << ["${it.id}":"${it.authority}"]
        }

        render(view:'roles',model: [authories:(rs as JSON)])
    }

    def findAllRoles(){

        render rolesService.findRoles() as JSON
    }

    def saveRoles(){
        println params
        render rolesService.save(params) as JSON
    }

    def updRoles(){
        render rolesService.update(params) as JSON
    }

    def delRoles(){
        render rolesService.delete(params) as JSON
    }

    def authority(){
        render(view:'authority')
    }

    def findAuthorities(){
       render authorityService.findAuthorities()  as JSON
    }

    def saveAuthority(){
        render authorityService.save(params) as JSON
    }

    def updAuthority(){
        render authorityService.update(params) as JSON
    }

    def delAuthority(){
        render authorityService.delete(params) as JSON
    }

    def updPass(){
        render(view:'updPass')
    }

    def changePass(){
        render memberService.changePass(params) as JSON
    }
}
