package com.www.scheduleer.controller;

import com.www.scheduleer.controller.dto.member.LoginDto;
import com.www.scheduleer.controller.dto.member.SignInResponseDto;
import com.www.scheduleer.service.Board.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final LoginService loginService;

    @PostMapping("/api/v1/signUp")
    public Long signUp(@RequestBody LoginDto loginDto) {
        return loginService.signUp(loginDto.getEmail(), loginDto.getPassword());
    }

    @PostMapping("/api/v1/signIn")
    public SignInResponseDto signInp(@RequestBody LoginDto loginDto) {
        return loginService.signIn(loginDto.getEmail(), loginDto.getPassword());
    }
}