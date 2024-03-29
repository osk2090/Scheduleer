package com.www.scheduleer.controller;

import com.www.scheduleer.config.annotation.CurrentMember;
import com.www.scheduleer.controller.dto.member.ChangePasswdDto;
import com.www.scheduleer.controller.dto.member.MemberLoginResponseDto;
import com.www.scheduleer.controller.dto.member.SignInDto;
import com.www.scheduleer.controller.dto.member.SignUpDto;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.Board.BoardService;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController()
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final BoardService boardService;

    @PostMapping(value = "/signUp")
    public Long signUp(@ModelAttribute SignUpDto signUpDto) throws IOException {
        return memberService.signUp(signUpDto);
    }

    @PostMapping("/signIn")
    public MemberLoginResponseDto signInp(@RequestBody SignInDto signInDto) {
        return memberService.signIn(signInDto.getEmail(), signInDto.getPassword());
    }

    @GetMapping("/myInfo")
    public ResponseEntity myInfo(@CurrentMember Member member) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMemberInfo(member));
    }

    @GetMapping("/myInfo/boards")
    public ResponseEntity myInfoForBoards(@CurrentMember Member member, @RequestParam(required = false) Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.getBoardList(0, id, member, 10));
    }

    @PatchMapping("/updatePasswd")
    public ResponseEntity updatePassword(@RequestBody() ChangePasswdDto changePasswd, @CurrentMember Member member) {
        memberService.changePw(changePasswd, member);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
