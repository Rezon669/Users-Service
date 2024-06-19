package com.ecom.usersservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Login {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long loginid;



	private String emailid;
	
	private String password;

	public Long getLoginid() {
		return loginid;
	}

	public void setLoginid(Long loginid) {
		this.loginid = loginid;
	}
	
	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	@Override
	public String toString() {
		return "Login [loginid=" + loginid + ", emailid=" + emailid + ", password=" + password + "]";
	
}
}
