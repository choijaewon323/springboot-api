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

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;

    public AccountServiceImpl(final AccountRepository accountRepository,
                              final BCryptPasswordEncoder passwordEncoder,
                              final BoardRepository boardRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.boardRepository = boardRepository;
    }

    @Override
    public void create(final AccountRequestDto request) {
        checkUsernameAlreadyExist(request.getUsername());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        accountRepository.save(Account.builder()
                        .username(request.getUsername())
                        .password(encodedPassword)
                        .role(AccountRole.USER)
                .build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponseDto> readAll() {
        List<Account> users = accountRepository.findByRole(AccountRole.USER);
        List<AccountResponseDto> results = new ArrayList<>();

        users.stream().forEach(u -> {
            results.add(u.toResponseDto());
        });

        return results;
    }

    @Override
    public AccountResponseDto readOne(final Long accountId) {
        return null;
    }

    @Override
    public void updateUsername(final AccountUsernameUpdateDto dto) {
        Account account = getExistAccount(dto.getBefore());

        checkUsernameAlreadyExist(dto.getAfter());
        account.updateUsername(dto.getAfter());
        changeUsernameInBoard(dto);
    }

    @Override
    public void updatePassword(final AccountRequestDto dto) {
        Account account = getExistAccount(dto.getUsername());

        account.updatePassword(passwordEncoder.encode(dto.getPassword()));
    }

    @Override
    public void delete(final String username) {
        Account account = getExistAccount(username);

        accountRepository.deleteById(account.getId());
        boardRepository.deleteByWriter(username);
    }

    private void checkUsernameAlreadyExist(final String username) {
        boolean check = accountRepository.findByUsername(username).isPresent();

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

    private Account getExistAccount(final String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 username입니다"));
    }
}
