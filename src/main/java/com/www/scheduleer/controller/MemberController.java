package com.www.scheduleer.controller;

import com.www.scheduleer.VO.MemberInfo;
import com.www.scheduleer.VO.security.MemberInfoDto;
import com.www.scheduleer.service.Board.BoardService;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final BoardService boardService;

    private final HttpSession httpSession;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder
                .getContext().getAuthentication());
        return "/main";
    }

    @PostMapping("/member")
    public String signup(MemberInfoDto infoDto) {
        memberService.save(infoDto);
        return "/main";
    }

    //현재 로그인된 멤버의 정보
    @GetMapping("/info")
    public String memberInfo(@AuthenticationPrincipal MemberInfo memberInfo, Model model) {
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
        return "/member/update";
    }

    @PostMapping("/member/update/{id}")
    public String checkPw(@RequestBody String pw, @AuthenticationPrincipal MemberInfo memberInfo) {
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
//        return result;
        System.out.println(result);
        return "redirect:/";
    }

    @PostMapping("/pw-change")
    public String pwChange(@RequestBody MemberInfoDto memberInfoDto) {
        System.out.println("비밀번호 변경 요청 발생!!!");
        memberService.modifyPw(memberInfoDto);
        return "changeSuccess";
    }
}
