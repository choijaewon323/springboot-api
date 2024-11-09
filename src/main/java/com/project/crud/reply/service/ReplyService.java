package com.project.crud.reply.service;

import com.project.crud.reply.domain.Reply;
import com.project.crud.reply.repository.ReplyRepository;
import com.project.crud.reply.dto.ReplyRequestDto;
import com.project.crud.reply.dto.ReplyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    public void create(final Long boardId, final ReplyRequestDto dto) {
        replyRepository.save(dto.toEntity(boardId));
    }

    @Transactional(readOnly = true)
    public List<ReplyResponseDto> readAll(final Long boardId) {
        final List<Reply> replies = replyRepository.findByBoardId(boardId);

        return replies.stream()
                .map(ReplyResponseDto::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReplyResponseDto readOne(final Long replyId) {
        final Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new NoSuchElementException("해당 reply이 존재하지 않습니다"));

        return ReplyResponseDto.of(reply);
    }

    public void update(final Long replyId, final ReplyRequestDto dto) {
        final Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new NoSuchElementException("해당 reply이 존재하지 않습니다"));

        reply.update(dto);
    }

    public void delete(final Long replyId) {
        replyRepository.deleteById(replyId);
    }
}
