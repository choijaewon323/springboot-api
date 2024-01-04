package com.project.crud.reply.service;

import com.project.crud.board.domain.Board;
import com.project.crud.board.domain.BoardRepository;
import com.project.crud.reply.domain.Reply;
import com.project.crud.reply.domain.ReplyRepository;
import com.project.crud.reply.dto.ReplyRequestDto;
import com.project.crud.reply.dto.ReplyResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReplyServiceImpl implements ReplyService {
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    public ReplyServiceImpl(BoardRepository boardRepository, ReplyRepository replyRepository) {
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
    }

    @Override
    @Transactional
    public void create(Long boardId, ReplyRequestDto dto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(NoSuchElementException::new);

        Reply reply = new Reply(dto.getContent(), dto.getWriter(), board);
        replyRepository.save(reply);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReplyResponseDto> readAll(Long boardId) {
        List<Reply> replies = replyRepository.findAllByBoard(boardId);
        List<ReplyResponseDto> result = new ArrayList<>();

        for (Reply reply : replies) {
            result.add(new ReplyResponseDto(reply.getId(), reply.getContent(), reply.getWriter()));
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public ReplyResponseDto readOne(Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(NoSuchElementException::new);

        return new ReplyResponseDto(reply.getId(), reply.getContent(), reply.getWriter());
    }

    @Override
    @Transactional
    public void update(Long replyId, ReplyRequestDto dto) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(NoSuchElementException::new);

        reply.update(dto.getContent(), dto.getWriter());
    }

    @Override
    @Transactional
    public void delete(Long replyId) {
        replyRepository.deleteById(replyId);
    }
}
