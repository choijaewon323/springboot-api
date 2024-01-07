package com.project.crud.reply.repository;

import com.project.crud.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Query("select r from Reply r where r.board.id = :boardId")
    List<Reply> findAllByBoard(@Param("boardId") Long boardId);
}
