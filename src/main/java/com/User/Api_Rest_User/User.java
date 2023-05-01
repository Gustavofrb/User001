package com.User.Api_Rest_User;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")	
public class User implements Serializable{

	  private static final long serialVersionUID = 1L;
	  
	  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column
	String name;
	@Column
	String address;
	@Column
	String phone;
    @Column(unique = true)
    private String login;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    String password;


	public String getLogin;


	public String getPassword;
	
	

	public Object getPassword() {
		// TODO Auto-generated method stub
		return null;
	}



	public Object getLogin() {
		// TODO Auto-generated method stub
		return null;
	}


	}

	

