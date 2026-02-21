package com.example.Repository;

import java.util.List;
import java.util.Optional;
import java.util.jar.JarEntry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.AccountDetails;


@Repository
public interface AccountDetail_Repo extends JpaRepository<AccountDetails,Long>{
	AccountDetails findByUserId(Long userId);
	List<AccountDetails> findAllByUserId(Long userId);
	

}
