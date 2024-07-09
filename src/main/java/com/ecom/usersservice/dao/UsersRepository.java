package com.ecom.usersservice.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.usersservice.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByUsername(String username);

	// String findByUsername(String username);

	public Optional<Users> findByEmailid(String emailid);

	@Query("SELECT u.userid from Users u WHERE u.emailid = :emailid")
	public long findUserid(String emailid);

	@Query("SELECT u.role from Users u WHERE u.emailid = :emailid")
	public String findEmailid(String emailid);

	@Modifying
	@Transactional
	@Query("UPDATE Users u SET u.password = :password WHERE u.emailid = :email")
	void updatePasswordByEmailid(@Param("password") String password, @Param("email") String email);

	@Query("SELECT username from Users u WHERE u.emailid = :emailid")
	public String findByUser(String emailid);

}
