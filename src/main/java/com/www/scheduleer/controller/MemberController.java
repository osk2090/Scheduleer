package com.www.scheduleer.controller;

import com.www.scheduleer.config.annotation.CurrentMember;
import com.www.scheduleer.controller.dto.member.MemberLoginResponseDto;
import com.www.scheduleer.controller.dto.member.SignInDto;
import com.www.scheduleer.controller.dto.member.SignUpDto;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.Board.BoardService;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final BoardService boardService;

    @PostMapping("/signUp")
    public Long signUp(@RequestBody SignUpDto signUpDto) {
        return memberService.signUp(signUpDto);
    }

    @PostMapping("/signIn")
    public MemberLoginResponseDto signInp(@RequestBody SignInDto signInDto) {
        return memberService.signIn(signInDto.getEmail(), signInDto.getPassword());
    }

    @GetMapping("/list")
    public String list(@CurrentMember Member member) {
        return member.getEmail();
    }

}
