package com.lj.csp.data
/**
 * 用户权限 关联表----执行 s2-quickstart xbudget StaffMember Authority 自动生成 没有修改
 */
import org.apache.commons.lang.builder.HashCodeBuilder

class MemberAuthority implements Serializable {

	Member member
	Authority authority



	boolean equals(other) {
		if (!(other instanceof MemberAuthority)) {
			return false
		}

		other.member?.id == member?.id &&
			other.authority?.id == authority?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (member) builder.append(member.id)
		if (authority) builder.append(authority.id)
		builder.toHashCode()
	}

	static MemberAuthority get(long memberId, long authorityId) {
        MemberAuthority.where {
            member==Member.load(memberId) && authority==Authority.load(authorityId)
        }.get()
	}

    static boolean exists(long memberId,long authorityId){
        MemberAuthority.where {
            member==Member.load(memberId) && authority==Authority.load(authorityId)
        }.count() > 0
    }

	static MemberAuthority create(Member member, Authority authority, boolean flush = false) {
		new MemberAuthority(member: member, authority: authority).save(flush: flush)
	}

	static boolean remove(Member member, Authority authority) {
        if(member==null || authority==null) return false

        int rowCount= MemberAuthority.where{
            member==Member.load(member.id) && authority==Authority.load(authority.id)
        }.deleteAll()

        rowCount > 0
	}

	static void removeAll(Member member) {
		if(member==null) return
        MemberAuthority.where{
            member==Member.load(member.id)
        }.deleteAll()

	}

	static void removeAll(Authority authority) {
        if(authority==null) return
        MemberAuthority.where{
            authority==authority.load(authority.id)
        }.deleteAll()
	}

	static mapping = {
		id composite: ['authority', 'member']
		version false
	}
}
