package com.api.rest.dto;

import java.io.Serializable;

import com.api.rest.model.Usuario;

public class UsuarioDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String userLogin;
	private String userNome;
	private String userCpf;
	
	
	public UsuarioDTO(Usuario u) {
		this.userLogin = u.getLogin();
		this.userNome = u.getNome();
		this.userCpf = u.getCpf();
	}
	
	public String getUserCpf() {
		return userCpf;
	}
	
	public void setUserCpf(String userCpf) {
		this.userCpf = userCpf;
	}
	
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	public String getUserNome() {
		return userNome;
	}
	public void setUserNome(String userNome) {
		this.userNome = userNome;
	}
	
	
	
	
}
