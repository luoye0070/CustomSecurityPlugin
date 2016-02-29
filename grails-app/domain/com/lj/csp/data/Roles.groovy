package com.lj.csp.data
/**
 * 角色 自己新增
 */
class Roles {

    String name//角色名称
    String remark//描述

    static belongsTo = Member
    static hasMany = [member:Member,rolesAuthority:RolesAuthority]//多个权限

    static constraints = {
        remark nullable: true
        name unique: true
    }

    String toString()
    {
        return name;
    }


    static Set<Roles> findAll(Member member) {
         Roles.where{
             member==Member.load(member.id)
         }.findAll()
    }

    def getData(){
        return [id:this.id,name:this.name,remark:this.remark,authorities:(this.rolesAuthority.collect{it.authority.id}.join(','))]
    }


}
