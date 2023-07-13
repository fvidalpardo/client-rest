package com.globallogic.clientrest.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globallogic.clientrest.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
	
	@Query("SELECT CASE WHEN COUNT(U) > 0 THEN TRUE ELSE FALSE END FROM User U WHERE U.email = ?1")
	Boolean checkUserByEmail(String email);
	
	@Query(value = "SELECT U FROM User U WHERE U.token = :token")
	User getUserByToken(String token);
}
