package com.ecom.usersservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.usersservice.model.LoginDetails;
@Repository
public interface LoginRepository extends JpaRepository<LoginDetails, Long> {

}
