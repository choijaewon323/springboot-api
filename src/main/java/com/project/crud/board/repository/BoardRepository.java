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
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Board b where b.id = :id")
    Optional<Board> pessimisticFindById(@Param("id") Long id);

    List<Board> findByWriter(String writer);

    void deleteByWriter(String writer);

    @Query(value = "select board " +
            "from Board board " +
            "where board.content like concat('%', :keyword, '%')")
    List<Board> searchByContent(@Param("keyword") String keyword);

    @Query(value = "select * " +
            "from board " +
            "where MATCH(content) AGAINST (:keyword IN BOOLEAN MODE)", nativeQuery = true)
    List<Board> searchByContentFullText(@Param("keyword") String keyword);

    List<Board> findByTitleContaining(String title);

    List<Board> findByWriterContaining(String writer);
}
