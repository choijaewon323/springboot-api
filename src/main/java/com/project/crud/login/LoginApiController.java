package com.project.crud.login;

import com.project.crud.login.dto.LoginRequestDto;
import com.project.crud.login.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LoginApiController {
    private final LoginService loginService;

    @PostMapping("/success")
    public LoginResponseDto login(@RequestBody LoginRequestDto request) {
        return loginService.login(request);
    }


}
