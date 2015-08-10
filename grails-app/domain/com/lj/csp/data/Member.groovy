package com.lj.csp.data
/**
 * 用户实体类 --执行 s2-quickstart xbudget StaffMember Authority 自动生成后改动
 */
class Member {

	transient springSecurityService

	String username
	String password
	boolean enabled=true
	boolean accountExpired=false
	boolean accountLocked=false
	boolean passwordExpired=false

    static transients = ['springSecurityService']

    String realName //真实姓名

    static hasMany = [roles:Roles]

	static constraints = {
		username blank: false, unique: true
		password blank: false
        realName nullable: true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Authority> getAuthorities() {
		MemberAuthority.findAllByMember(this).collect { it.authority } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}

    def getData(){
        return
        [
                id:this.id,
                username:this.username,
                enabled:this.enabled,
                accountExpired:this.accountExpired,
                accountLocked:this.accountLocked,
                passwordExpired:this.passwordExpired
        ]
    }

    @Override
    public java.lang.String toString() {
        if(realName){
            return realName;
        }else{
            return username;
        }
    }
}
