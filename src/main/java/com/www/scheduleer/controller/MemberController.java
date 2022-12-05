package com.www.scheduleer.controller;

import com.www.scheduleer.config.auth.LoginUser;
import com.www.scheduleer.web.domain.MemberInfo;
import com.www.scheduleer.web.dto.member.MemberInfoDto;
import com.www.scheduleer.service.Board.BoardService;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final BoardService boardService;

    private final HttpSession httpSession;

    @PostMapping("/login")
    public String login(String email, String password) {
        System.out.println(email + ":" + password);
        return "/login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder
                .getContext().getAuthentication());
        return "/main";
    }

    @PostMapping("/signup")
    public ResponseEntity signup(MemberInfoDto infoDto) {
        Optional<MemberInfo> m = memberService.getMember(infoDto.getEmail());
        if (m.isPresent()) {
            return ResponseEntity.badRequest().body("해당 계정은 중복됩니다.");
        }
        memberService.save(infoDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //현재 로그인된 멤버의 정보
    @GetMapping("/info")
    public String memberInfo(@LoginUser MemberInfo memberInfo, Model model) {
        if (memberInfo == null) {
            Optional<MemberInfo> loginGoogleInfo = memberService.findMemberInfoFromMemberInfoDTO(boardService.getLoginGoogle().getEmail());
            if (loginGoogleInfo.isPresent()) {
                model.addAttribute("member", loginGoogleInfo.get());
                if (boardService.findBoardInfoByWriter(loginGoogleInfo.get()).isPresent()) {
                    model.addAttribute("boardList", boardService.findBoardInfoByWriter(loginGoogleInfo.get()).get());
                }
            }
        } else {
            Optional<MemberInfo> member = memberService.getMember(memberInfo.getEmail());
            if (member.isPresent()) {
                model.addAttribute("member", member.get());
                if (boardService.findBoardInfoByWriter(memberInfo).isPresent()) {
                    model.addAttribute("boardList", boardService.findBoardInfoByWriter(memberInfo).get());
                }
            }
        }
        return "/member/info";
    }

    @GetMapping("/list")
    @Transactional
    public String list(Model model, @AuthenticationPrincipal MemberInfo memberInfo) {
        model.addAttribute("memberList", memberService.getMemberList());
        return "/member/list";
    }

    @GetMapping("/find")
    public String findMember(@RequestParam(value = "email") String email, Model model) {
        List<MemberInfo> memberList = memberService.findMembers(email);
        model.addAttribute("memberList", memberList);
        return "/member/list";
    }

    @GetMapping("/member/update/{id}")
    public String edit(Model model, @PathVariable("id") Long memberId) {
        model.addAttribute("member", memberService.findMember(memberId).get());
        System.out.println("비밀번호 변경페이지로 이동!!!");
        return "password";
    }

    @PostMapping("/member/update/{id}")
    public String checkPw(String pw, @AuthenticationPrincipal MemberInfo memberInfo, Model model) {
        System.out.println("비밀번호 확인 요청 발생");

        String result = null;
        MemberInfo member = null;

        if (memberInfo == null) {
            if (boardService.getLoginGoogle() != null) {
                member = memberService.findMemberInfoFromMemberInfoDTO(boardService.getLoginGoogle().getEmail()).get();
            }
        } else {
            member = memberInfo;
        }
        System.out.println("DB 회원의 비밀번호 : " + member.getPassword());
        System.out.println("폼에서 받아온 비밀번호 : " + pw);

        if (memberService.bc().matches(pw, member.getPassword())) {
            result = "pwConfirmOK";
        } else {
            result = "pwConfirmNO";

        }
        model.addAttribute("result", result);
        System.out.println(result);
        return "/main";
    }

    @PostMapping("/pw-change")
    public String pwChange(@RequestBody MemberInfoDto memberInfoDto) {
        System.out.println("비밀번호 변경 요청 발생!!!");
//        memberService.modifyPw(memberInfoDto);
        return "changeSuccess";
    }
}
