package com.example.entities;

import java.util.Set;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="roles")
public class Role {
	public enum RoleName{
		ADMIN,
		USER
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(unique = true,nullable = false)
	private RoleName roleName=RoleName.USER;
	
	@ManyToMany(mappedBy = "roles")         // field name in UseCredential Table
	@JsonIgnore                            //so that it will not return the Multiple users of USER in response
	private Set<UserCredential> user;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleName getRoleName() {
		return roleName;
	}

	public void setRoleName(RoleName roleName) {
		this.roleName = roleName;
	}

	public Set<UserCredential> getUser() {
		return user;
	}

	public void setUser(Set<UserCredential> user) {
		this.user = user;
	}

	

}
