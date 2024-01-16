package com.project.crud.board.repository;

import com.project.crud.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueryDslRepository {
    List<Board> readAllPagingDesc(final int pageIndex, final int pageSize);
}
