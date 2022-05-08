package io.nadonhwi.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.nadonhwi.boot.model.Role;
import io.nadonhwi.boot.model.User;
import io.nadonhwi.boot.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User save(User user) {
		
		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		user.setEnabled(true);
		
		// 하드코딩 부분 - 원래는 roleRepository가 필요함
		Role role = new Role();
		role.setId(1l);
		
		// 다대다 양방향일때 데이터 넣는 법 
		user.getRoles().add(role);
		
		// https://cjw-awdsd.tistory.com/47 - 일대다, 다대일 관계일 때 데이터 넣는 법
		/* 하다가 오류남 
		role.setName("관리자");
		
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		
		user.getRoles().add(userRole);
		role.getUsers().add(userRole);
		
		//roleRepository.save(role);
		//userRoleRepository.save(userRole);
		*/
		return userRepository.save(user);
	}
}
