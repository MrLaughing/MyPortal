package com.zai360.portal.test.vo;


import java.util.Date;
import java.util.Set;

public class Role {
    private Long id;

    private Date createDate;

    private Date modifyDate;

    private String description;

    private Boolean isSystem;

    private String name;
    
    
	@Override
	public String toString() {
		return "Role [id=" + id + ", createDate=" + createDate
				+ ", modifyDate=" + modifyDate + ", description=" + description
				+ ", isSystem=" + isSystem + ", name=" + name + "]";
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}