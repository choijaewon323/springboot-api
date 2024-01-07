package com.project.crud.like.service;

import com.project.crud.like.dto.BoardLikeRequestDto;

public interface BoardLikeService {
    void up(BoardLikeRequestDto request);
    void down(BoardLikeRequestDto request);
}
