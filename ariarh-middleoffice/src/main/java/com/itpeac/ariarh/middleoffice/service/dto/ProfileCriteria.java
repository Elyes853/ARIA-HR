package com.itpeac.ariarh.middleoffice.service.dto;

import java.time.Instant;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProfileCriteria {

	private String codeMetier;	
	private String description;
	private String createdBy;
	private Instant createdDate;
	private String lastModifiedBy;
	private Instant lastModifiedDate;
	private String permissionDescs ;
	private String permissionRoles ;
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Instant getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getCodeMetier() {
		return codeMetier;
	}
	public void setCodeMetier(String codeMetier) {
		this.codeMetier = codeMetier;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	public String getPermissionDescs() {
		return permissionDescs;
	}
	public void setPermissionDescs(String permissionDescs) {
		this.permissionDescs = permissionDescs;
	}
	public String getPermissionRoles() {
		return permissionRoles;
	}
	public void setPermissionRoles(String permissionRoles) {
		this.permissionRoles = permissionRoles;
	}
	public ProfileCriteria() {
		
	}
	@Override
	public String toString() {
		return new ToStringBuilder(getClass()).
				append("codeMetier",codeMetier).
				append("description", description).
				toString();
	}
	
	

	
}
