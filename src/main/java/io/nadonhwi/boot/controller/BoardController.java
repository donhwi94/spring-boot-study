package io.nadonhwi.boot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.nadonhwi.boot.model.Board;
import io.nadonhwi.boot.repository.BoardRepository;
import io.nadonhwi.boot.validator.BoardValidator;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private BoardValidator boardValidator;
	
	@GetMapping("/list")
	public String list(Model model, @PageableDefault(size=2) Pageable pageable, @RequestParam(required=false, defaultValue="") String searchText) {
		
		// page별 조회 
		// localhost:8090/board/list?page=0&size=1 과 같은식으로 표시할 page와 size를 파라미터로 넘겨줄 수 있다
		//Page<Board> boards = boardRepository.findAll(pageable);
		
		// title과 content로 검색
		Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
		
		int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
		int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4); 
		
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		// page별 조회 : 하드코딩
		//Page<Board> boards = boardRepository.findAll(PageRequest.of(0, 20));
		
		// 전체 리스트 조회
		// List<Board> boards = boardRepository.findAll();
		
		model.addAttribute("boards", boards);
		
		return "board/list";
	}
	
	@GetMapping("/form")
	public String form(Model model, @RequestParam(required=false) Long id) {
		if(id == null) {
			model.addAttribute("board", new Board());
		} else {
			// id가 잘못되었거나 없을때는 null을 넘겨준다
			Board board = boardRepository.findById(id).orElse(null);
			model.addAttribute("board", board);
		}
		
		return "board/form";
	}
	
	@PostMapping("/form")
	public String formSubmit(@Valid Board board, BindingResult bindingResult) {
		boardValidator.validate(board, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "board/form";
		}
		boardRepository.save(board);
		return "redirect:/board/list";
	}
}
