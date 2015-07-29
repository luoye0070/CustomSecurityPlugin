package com.lj.csp.data
/**
 * 权限 --执行 s2-quickstart xbudget StaffMember Authority 自动生成后改动
 */
class Authority {

    String authority //权限名称
    String remark//权限说明

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
        remark nullable: true
	}

    def getData(){
        return [authority:this.authority,id:this.id,remark:this.remark]
    }
}
