package com.www.scheduleer.controller;

import com.www.scheduleer.VO.MemberInfo;
import com.www.scheduleer.VO.security.MemberInfoDto;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

//    @GetMapping("/login")
//    public String loginForm(HttpServletRequest req) {
//        String referer = req.getHeader("Referer");
//        req.getSession().setAttribute("prevPage", referer);
//        return "login";
//    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request,response, SecurityContextHolder
                .getContext().getAuthentication());
        return "redirect:/login";
    }

    @PostMapping("/user")
    public String signup(MemberInfoDto infoDto) {
        memberService.save(infoDto);
        return "redirect:/login";
    }

//    @GetMapping("/list")
//    public String list(Model model) {
//        List<MemberInfo> memberList = memberService.getMemberList();
//        model.addAttribute("memberList", memberList);
//        return "/member/list";
//    }
//
//    @GetMapping("/find")
//    public String findMember(@RequestParam(value = "email") String email, Model model) {
//        List<MemberInfo> memberList = memberService.findMember(email);
//        model.addAttribute("memberList", memberList);
//        return "/member/list";
//    }
}
