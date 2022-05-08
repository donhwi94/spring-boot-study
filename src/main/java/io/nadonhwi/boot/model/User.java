package io.nadonhwi.boot.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Column(unique=true)
	private String username;
	@NotNull
	private String password;
	@NotNull
	private Boolean enabled;
	
	@OneToMany(mappedBy = "user")
	private List<UserRole> roles = new ArrayList<>();
}
