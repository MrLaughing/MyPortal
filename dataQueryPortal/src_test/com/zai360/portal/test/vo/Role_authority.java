package com.zai360.portal.test.vo;


public class Role_authority {
    private Long role;

    private Long authority;
    
    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public Long getAuthority() {
		return authority;
	}

	public void setAuthority(Long authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "Role_authority [role=" + role + ", authority=" + authority
				+ "]";
	}

}