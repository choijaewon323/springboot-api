package com.project.crud.board.repository;

import com.project.crud.board.domain.Board;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, QueryDslRepository {
    List<Board> findByContentContaining(String content);

    @Query(value = "select board_id, title, content, writer, like_count, cnt, created_date, modified_date " +
            "from board " +
            "where MATCH(content) AGAINST (:keyword IN BOOLEAN MODE)", nativeQuery = true)
    List<Board> findByContentFulltext(@Param("keyword") String keyword);

    List<Board> findByTitleContaining(String title);

    List<Board> findByWriterContaining(String writer);

    void deleteByWriter(String writer);

    List<Board> findByWriter(String writer);
}
