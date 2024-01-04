package com.project.crud.like.service;

import com.project.crud.like.dto.BoardLikeRequest;

public interface BoardLikeService {
    void up(BoardLikeRequest request);
    void down(BoardLikeRequest request);
}
