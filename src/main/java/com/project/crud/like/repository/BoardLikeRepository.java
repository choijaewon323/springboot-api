package com.project.crud.like.repository;

import com.project.crud.like.domain.BoardLike;
import com.project.crud.like.domain.BoardLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikeId> {

}
