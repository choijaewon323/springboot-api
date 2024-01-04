package com.project.crud.reply.service;

import com.project.crud.reply.dto.ReplyRequestDto;
import com.project.crud.reply.dto.ReplyResponseDto;

import java.util.List;

public interface ReplyService {

    void create(Long boardId, ReplyRequestDto dto);
    List<ReplyResponseDto> readAll(Long boardId);
    ReplyResponseDto readOne(Long replyId);
    void update(Long replyId, ReplyRequestDto dto);
    void delete(Long replyId);
}
