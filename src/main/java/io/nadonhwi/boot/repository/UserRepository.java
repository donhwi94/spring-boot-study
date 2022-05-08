package io.nadonhwi.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.nadonhwi.boot.model.Board;
import io.nadonhwi.boot.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
