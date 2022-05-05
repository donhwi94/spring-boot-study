package io.nadonhwi.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.nadonhwi.boot.model.Board;
import io.nadonhwi.boot.repository.BoardRepository;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Board> boards = boardRepository.findAll();
		model.addAttribute("boards", boards);
		
		return "board/list";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("board", new Board());
		return "board/form";
	}
	
	@PostMapping("/form")
	public String formSubmit(@ModelAttribute Board board) {
		boardRepository.save(board);
		return "redirect:/board/list";
	}
}
