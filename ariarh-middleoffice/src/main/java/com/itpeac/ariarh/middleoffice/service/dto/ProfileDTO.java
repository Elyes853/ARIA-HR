package com.itpeac.ariarh.middleoffice.service.dto;

import java.time.Instant;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProfileDTO {

	private Long id;
	private String codeMetier;	
	private String description;
	private List<PermissionDTO> permissions;
	// private List<User> users ;
	private String createdBy;
	private Instant createdDate;
	private String lastModifiedBy;
	private Instant lastModifiedDate;
	
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

	 public List<PermissionDTO> getPermissions() {
			return permissions;}

	public void setPermissions(List<PermissionDTO> permissions) {
		this.permissions = permissions;
	}

	//public List<User> getUsers() {
	// 	return users;
	// }
	 //public void setUsers(List<User> users) {
	//	this.users = users;
	 //}
	public ProfileDTO() {
	}

	@Override
	public String toString() {
		return new ToStringBuilder(getClass()).
				append("codeMetier", codeMetier).
				append("description",description).
				append("permissions", permissions).
				toString();
	}
}
