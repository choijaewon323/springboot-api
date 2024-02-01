package com.project.crud.reply.service;

import com.project.crud.account.domain.Account;
import com.project.crud.account.repository.AccountRepository;
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
    private final AccountRepository accountRepository;

    public ReplyServiceImpl(BoardRepository boardRepository, ReplyRepository replyRepository, AccountRepository accountRepository) {
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void create(final Long boardId, final ReplyRequestDto dto) {
        replyRepository.save(dto.toEntity(boardId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReplyResponseDto> readAll(final Long boardId) {
        final List<Reply> replies = replyRepository.findByBoardId(boardId);

        return makeDtoList(replies);
    }

    @Override
    @Transactional(readOnly = true)
    public ReplyResponseDto readOne(final Long replyId) {
        final Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new NoSuchElementException("해당 reply이 존재하지 않습니다"));

        return reply.toDto();
    }

    @Override
    public void update(final Long replyId, final ReplyRequestDto dto) {
        final Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new NoSuchElementException("해당 reply이 존재하지 않습니다"));

        reply.update(dto);
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
