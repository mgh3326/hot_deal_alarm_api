package me.khmoon.hot_deal_alram_api.repository;

import me.khmoon.hot_deal_alram_api.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Board, Long> {
}
