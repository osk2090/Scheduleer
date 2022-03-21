package com.www.scheduleer.controller;

import com.www.scheduleer.VO.Member;
import com.www.scheduleer.VO.security.MemberInfo;
import com.www.scheduleer.service.Member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(HttpServletRequest req) {
        String referer = req.getHeader("Referer");
        req.getSession().setAttribute("prevPage", referer);
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request,response, SecurityContextHolder
                .getContext().getAuthentication());
        return "redirect:/login";
    }

    @PostMapping("/user")
    public String signup(MemberInfo memberInfo) {
        memberService.save(memberInfo);
        return "redirect:/login";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Member> memberList = memberService.getMemberList();
        model.addAttribute("memberList", memberList);
        return "/member/list";
    }

    @GetMapping("/find")
    public String findMember(@RequestParam(value = "email") String email, Model model) {
        List<Member> memberList = memberService.findMember(email);
        model.addAttribute("memberList", memberList);
        return "/member/list";
    }

}
