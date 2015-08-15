package com.lj.csp.service.security

import com.lj.csp.data.Authority
import com.lj.csp.data.Member
import com.lj.csp.data.Requestmap
import com.lj.csp.data.Roles

/**
 * Title:用户总服务
 * Description:处理用户相关的后台逻辑
 * @author: 王刚
 * @create: 2015-03-27
 * @edit:
 *
 */
class UserApiService {

    def memberService
    def rolesService
    boolean transactional = false

    void init(){
        initRequest()
        initAuthority()
        initRoles()
        initMember()
    }

     //初始化Request
    void initRequest(){
        if(!Requestmap.findByUrl("/userApi/**")){
            new Requestmap(url:'/userApi/**',configAttribute:"hasAnyRole('R_DEV','R_ADMIN')").save(flush: true);
            log.info("初始化之用户管理权限配置：/userApi/**");
        }
        if(!Requestmap.findByUrl("/userApi/roles/**")){
            new Requestmap(url:'/userApi/roles/**',configAttribute:"hasRole('R_DEV')").save(flush: true);
            log.info("初始化之角色管理权限配置：/userApi/roles/**");
        }
        if(!Requestmap.findByUrl("/userApi/authority/**")){
            new Requestmap(url:'/userApi/authority/**',configAttribute:"hasRole('R_DEV')").save(flush: true);
            log.info("初始化之权限管理权限配置：/userApi/authority/**");
        }
        if(!Requestmap.findByUrl("/userApi/requestmap/**")){
            new Requestmap(url:'/userApi/requestmap/**',configAttribute:"hasRole('R_DEV')").save(flush: true);
            log.info("初始化之权限配置管理权限配置：/userApi/requestmap/**");
        }
        if(!Requestmap.findByUrl("/userApi/updPass/**")){
            new Requestmap(url:'/userApi/updPass/**',configAttribute:"authenticated").save(flush: true);
            log.info("初始化之密码修改权限配置：/userApi/updPass/**");
        }
    }

    //初始话authority
    void initAuthority(){
        def authority=Authority.findByAuthority('R_ADMIN')
        if(!authority)
            new Authority(authority:'R_ADMIN',remark: '管理员使用').save(flush: true)

        authority=Authority.findByAuthority('R_DEV')
        if(!authority)
            new Authority(authority:'R_DEV',remark: '有些功能只限开发人员使用').save(flush: true)
    }

    //初始化Roles
    void initRoles(){
       def params=[:]
       def authority
       def roles=Roles.findByName('管理员')
       if(!roles){
          params << [name:'管理员',remark:'管理系统正常功能']
          authority=Authority.findByAuthority('R_ADMIN')
          if(authority)
              params << [authorities:authority.id.toString()]

           rolesService.save(params)
       }

        roles=Roles.findByName('开发人员')
        if(!roles){
            params << [name:'开发人员',remark:'有些功能只供开发人员使用']
            authority=Authority.findByAuthority('R_DEV')
            if(authority)
                params << [authorities:authority.id.toString()]

            println  rolesService.save(params)
        }
    }


    void initMember(){
        def member=Member.findByUsername('admin')
        def roles
        def params=[:]
        if(!member){
            params << [username:'admin',password:'admin']
            roles=Roles.findByName('管理员')
            if(roles){
                params << [roles:roles.id.toString()]
            }

           println  memberService.save(params)
        }



        params.clear()
        member=Member.findByUsername('dev')
        if(!member){
            params << [username:'dev',password:'dev']
            roles=Roles.findByName('开发人员')
            if(roles){
                params << [roles:roles.id.toString()]
            }

           println  memberService.save(params)
        }
    }
}
