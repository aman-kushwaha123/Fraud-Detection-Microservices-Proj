package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Role;
import com.example.entities.Role.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	 Role findByRoleName(RoleName roleName);
	

}
