package io.nadonhwi.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.nadonhwi.boot.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
