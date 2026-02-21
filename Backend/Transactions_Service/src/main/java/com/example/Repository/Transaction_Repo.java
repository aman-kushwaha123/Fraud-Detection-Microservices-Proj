package com.example.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entities.Transactions;

@Repository
public interface Transaction_Repo extends JpaRepository<Transactions, Long>{
	
	@Query(
			value="SELECT COUNT(*) FROM transactions where user_id=:userId AND time_stamp>=:timeStamp",
			nativeQuery=true)
	Long countByUserIdAndTimeStamp(@Param("userId") Long userId,@Param("timeStamp") LocalDateTime timeStamp);
	
	List<Transactions> findByFromAccountIdInAndToAccountIdIn(List<Long> fromAccountId,List<Long> toAccountId);
	List<Transactions> findByFromAccountIdIn(List<Long> fromAccountId);
	List<Transactions> findByToAccountIdIn(List<Long> toAccountId);
	
	List<Transactions> findByUserId(Long userId);

}
