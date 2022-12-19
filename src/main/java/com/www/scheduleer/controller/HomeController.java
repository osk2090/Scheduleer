package com.www.scheduleer.controller;

import com.www.scheduleer.config.annotation.CurrentMember;
import com.www.scheduleer.controller.dto.member.LoginDto;
import com.www.scheduleer.controller.dto.member.MemberLoginResponseDto;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class HomeController {
    //    private final LoginService loginService;
    private final MemberService memberService;

    @PostMapping("/api/v1/signUp")
    public Long signUp(@RequestBody @Valid LoginDto loginDto) {
        return memberService.signUp(loginDto.getEmail(), loginDto.getPassword());
    }

    @PostMapping("/api/v1/signIn")
    public MemberLoginResponseDto signInp(@RequestBody LoginDto loginDto) {
        Optional<Member> member = memberService.getMember(loginDto.getEmail());
        return memberService.signIn(member.get());
    }

    @GetMapping("/api/v1/test")
    public void test(@CurrentMember Member member) {
    }
}