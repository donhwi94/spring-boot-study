package io.nadonhwi.boot.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	// ManyToMany 방법 1 - 일대다, 다대일
	//@OneToMany(mappedBy = "user")
	//private List<UserRole> roles = new ArrayList<>();
	
	// 양방향 매핑
	
	@ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();
	
	// onetomany 관계의 경우 user테이블에는 board 정보가 저장되지 않는다 -> 해결 : cascade
	// orphanRemoval - 기존의 데이터는 다 지운다
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Board> boards = new ArrayList<>();
}
