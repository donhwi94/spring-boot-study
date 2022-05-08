package io.nadonhwi.boot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(min=2, max=30, message="제목을 입력해주세요")
	private String title;
	private String content;
	
	@ManyToOne
	// 참조하고자 하는 속성값이 pk 값이면 referencedColumnName = "id" 생략 가능
	//@JoinColumn(name = "user_id", referencedColumnName = "id")
	@JoinColumn(name = "user_id")
	private User user;
}
