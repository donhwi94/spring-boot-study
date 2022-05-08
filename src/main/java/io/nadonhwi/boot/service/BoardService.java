package io.nadonhwi.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nadonhwi.boot.model.Board;
import io.nadonhwi.boot.model.User;
import io.nadonhwi.boot.repository.BoardRepository;
import io.nadonhwi.boot.repository.UserRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Board save(String username, Board board) {
		User user = userRepository.findByUsername(username);
		board.setUser(user);
		
		return boardRepository.save(board);
	}
}
