package com.project.crud.like.repository;

import com.project.crud.like.domain.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

}
