package com.project.crud.reply.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.reply.domain.Reply;
import com.project.crud.reply.repository.ReplyRepository;
import com.project.crud.reply.dto.ReplyRequestDto;
import com.project.crud.reply.dto.ReplyResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    public ReplyServiceImpl(final BoardRepository boardRepository, final ReplyRepository replyRepository) {
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
    }

    @Override
    public void create(final Long boardId, final ReplyRequestDto dto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("해당 board가 존재하지 않습니다"));

        replyRepository.save(dto.toEntity(board));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReplyResponseDto> readAll(final Long boardId) {
        List<Reply> replies = replyRepository.findAllByBoard(boardId);

        return makeDtoList(replies);
    }

    @Override
    @Transactional(readOnly = true)
    public ReplyResponseDto readOne(final Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new NoSuchElementException("해당 reply이 존재하지 않습니다"));

        return reply.toDto();
    }

    @Override
    public void update(final Long replyId, final ReplyRequestDto dto) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new NoSuchElementException("해당 reply이 존재하지 않습니다"));

        reply.update(dto);
        ReplyResponseDto replyResponseDto = ReplyResponseDto.builder()
                .build();
    }

    @Override
    public void delete(final Long replyId) {
        replyRepository.deleteById(replyId);
    }

    private List<ReplyResponseDto> makeDtoList(final List<Reply> replies) {
        List<ReplyResponseDto> results = new ArrayList<>();

        replies.stream().forEach(e -> results.add(e.toDto()));
        return results;
    }
}
