package com.project.crud.account.service;

import com.project.crud.account.domain.Account;
import com.project.crud.account.dto.AccountRequestDto;
import com.project.crud.account.dto.AccountResponseDto;
import com.project.crud.account.dto.AccountUsernameUpdateDto;
import com.project.crud.account.repository.AccountRepository;
import com.project.crud.board.domain.Board;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.account.domain.AccountRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;
    private final AccountRepository accountRepository;

    public AccountServiceImpl(BCryptPasswordEncoder passwordEncoder, BoardRepository boardRepository, AccountRepository accountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.boardRepository = boardRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void create(final AccountRequestDto request) {
        checkUsernameAlreadyExist(request.getUsername());

        final String encodedPassword = passwordEncoder.encode(request.getPassword());
        accountRepository.save(request.toEntity(encodedPassword));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponseDto> readAll() {
        final List<Account> accounts = accountRepository.findByRole(AccountRole.USER);

        return toResponseDto(accounts);
    }

    @Override
    public AccountResponseDto readOne(final Long accountId) {
        return null;
    }

    @Override
    public void updateUsername(final AccountUsernameUpdateDto dto) {
        final Account account = findByUsername(dto.getBefore());

        checkUsernameAlreadyExist(dto.getAfter());
        account.updateUsername(dto.getAfter());
        changeUsernameInBoard(dto);
    }

    @Override
    public void updatePassword(final AccountRequestDto dto) {
        final Account account = findByUsername(dto.getUsername());

        account.updatePassword(passwordEncoder.encode(dto.getPassword()));
    }

    @Override
    public void delete(final String username) {
        final Account account = findByUsername(username);

        accountRepository.deleteById(account.getId());
        boardRepository.deleteByWriter(username);
    }

    private Account findByUsername(final String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 없습니다"));
    }

    private List<AccountResponseDto> toResponseDto(final List<Account> accounts) {
        List<AccountResponseDto> results = new ArrayList<>();

        accounts.stream().forEach(u -> {
            results.add(u.toResponseDto());
        });

        return results;
    }

    private void checkUsernameAlreadyExist(final String username) {
        final boolean check = accountRepository.existsByUsername(username);

        if (check) {
            throw new IllegalStateException("이미 있는 계정입니다");
        }
    }

    private void changeUsernameInBoard(final AccountUsernameUpdateDto dto) {
        List<Board> boards = boardRepository.findByWriter(dto.getBefore());

        boards.stream().forEach(board -> {
            board.updateWriter(dto.getAfter());
        });
    }
}
