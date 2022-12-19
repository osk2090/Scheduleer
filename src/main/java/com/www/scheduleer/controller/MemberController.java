package com.www.scheduleer.controller;

import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.Board.BoardService;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController()
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final BoardService boardService;

//    @PostMapping("/login")
//    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
//        System.out.println(memberLoginRequestDto);
////        return memberService.login(memberLoginRequestDto.getEmail(), memberLoginRequestDto.getPassword());
//    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder
                .getContext().getAuthentication());
        return "/main";
    }

//    @PostMapping("/signup")
//    public ResponseEntity signup(@RequestBody MemberDto infoDto) {
//        Optional<Member> m = memberService.getMember(infoDto.getEmail());
//        if (m.isPresent()) {
//            return ResponseEntity.badRequest().body("해당 계정은 중복됩니다.");
//        }
//        memberService.save(infoDto);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @GetMapping("/list")
    @Transactional
    public String list(Model model, @AuthenticationPrincipal Member member) {
        model.addAttribute("memberList", memberService.getMemberList());
        return "/member/list";
    }

    @GetMapping("/find")
    public String findMember(@RequestParam(value = "email") String email, Model model) {
        List<Member> memberList = memberService.findMembers(email);
        model.addAttribute("memberList", memberList);
        return "/member/list";
    }

    @GetMapping("/member/update/{id}")
    public String edit(Model model, @PathVariable("id") Long memberId) {
        model.addAttribute("member", memberService.findMember(memberId).get());
        System.out.println("비밀번호 변경페이지로 이동!!!");
        return "password";
    }
}
