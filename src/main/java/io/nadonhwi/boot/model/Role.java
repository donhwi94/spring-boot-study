package io.nadonhwi.boot.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String name;
	
	// ManyToMany 방법 1 - 일대다, 다대일
	// @OneToMany(mappedBy = "role")
	// private List<UserRole> users = new ArrayList<>();
	
	//양방향 매핑
	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
    private List<User> users;
	
}
