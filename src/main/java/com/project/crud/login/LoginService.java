package com.project.crud.login;

import com.project.crud.account.domain.Account;
import com.project.crud.account.repository.AccountRepository;
import com.project.crud.login.dto.LoginRequestDto;
import com.project.crud.login.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class LoginService {
    private final AccountRepository accountRepository;

    public LoginResponseDto login(LoginRequestDto request) {
        Optional<Account> isExist = accountRepository.findByUsername(request.username());

        if (isExist.isEmpty()) {
            return new LoginResponseDto(false, "존재하지 않는 아이디입니다");
        }

        Account stored = isExist.get();

        if (stored.getPassword().equals(request.password())) {
            return new LoginResponseDto(true, "로그인 성공");
        }
        return new LoginResponseDto(false, "비밀번호가 틀렸습니다");
    }
}
