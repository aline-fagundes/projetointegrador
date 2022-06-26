package br.com.passaporteclio.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "tb_login")
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_login")
	private Long idLogin;

	@Column(name = "email")
	private String email;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password")
	private String password;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.permissoes;
	}

	public User() {
	}

	public User(Long idLogin, String email, String username, String password, List<Permission> permissoes) {
		this.idLogin = idLogin;
		this.email = email;
		this.username = username;
		this.password = password;
		this.permissoes = permissoes;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_permissao_login", joinColumns = {
			@JoinColumn(name = "id_login", referencedColumnName = "id_login") }, inverseJoinColumns = {
					@JoinColumn(name = "id_permissao", referencedColumnName = "id_permissao") })

	private List<Permission> permissoes;

	public void setPermissions(List<Permission> permissoes) {
		this.permissoes = permissoes;
	}

	public List<Permission> getPermissions() {
		return this.permissoes;
	}

	public void setId(Long idLogin) {
		this.idLogin = idLogin;
	}

	public Long getId() {
		return idLogin;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, idLogin, permissoes, password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(idLogin, other.idLogin)
				&& Objects.equals(permissoes, other.permissoes) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public List<String> getRoles() {
		List<String> roles = new ArrayList<>();
		for (Permission permissao : this.permissoes) {
			roles.add(permissao.getDescricao());
		}
		return roles;
	}
}