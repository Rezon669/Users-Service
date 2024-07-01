package com.ecom.usersservice.model;

import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "logindetails")
@Component
public class LoginDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long loginid;

	private String emailid;

	private String token;

	private Date issuedAt;

	private int active;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(Date date) {
		this.issuedAt = date;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int i) {
		this.active = i;
	}

	@Override
	public String toString() {
		return "Login [loginid=" + loginid + ", emailid=" + emailid + ", token=" + token + ", issuedAt=" + issuedAt
				+ ", active=" + active + "]";
	}

}
