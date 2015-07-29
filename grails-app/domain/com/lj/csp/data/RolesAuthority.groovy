package com.lj.csp.data
/**
 * 权限角色 关联的中间表 --新增
 */
class RolesAuthority implements Serializable{
    Roles roles
    Authority authority

    static mapping = {
        id composite: ['roles', 'authority']
        version false
    }

    static constraints = {

    }
    boolean equals(other) {
        if (!(other instanceof RolesAuthority)) {
            return false
        }
        other.roles?.id == roles?.id &&
                other.authority?.id == authority?.id
    }
    static RolesAuthority get(long rolesId, long authorityId) {
        RolesAuthority.where {
            roles==Roles.load(rolesId) && authority==Authority.load(authorityId)
        }.get()
    }
    static RolesAuthority create(Roles roles, Authority authority, boolean flush = false) {
        new RolesAuthority(roles: roles, authority: authority).save(flush: flush, insert: true)
    }
    static boolean remove(Roles roles, Authority authority, boolean flush = false) {
        if(roles==null || authority==null) return false

        int rowCount= RolesAuthority.where{
            roles==Roles.load(roles.id) && authority==Authority.load(authority.id)
        }.deleteAll()

        rowCount > 0
    }
    static void removeAll(Roles roles) {
        if(roles==null) return
        RolesAuthority.where{
            roles==Roles.load(roles.id)
        }.deleteAll()
    }
    static void removeAll(Authority authority) {
        if(authority==null) return
        RolesAuthority.where{
            authority==authority.load(authority.id)
        }.deleteAll()
    }
}
