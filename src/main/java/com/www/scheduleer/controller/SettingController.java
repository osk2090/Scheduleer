package com.www.scheduleer.controller;

import com.www.scheduleer.service.Board.BoardService;
import com.www.scheduleer.service.Member.MemberService;
import com.www.scheduleer.service.Member.PasswordFormValidator;
import com.www.scheduleer.web.domain.Member;
import com.www.scheduleer.web.dto.member.PasswordFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SettingController {

    private final BoardService boardService;
    private final MemberService memberService;

    @InitBinder("passwordForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new PasswordFormValidator());
    }

    static final String SETTING_PROFILE_VIEW_NAME = "member/password";
    static final String SETTING_PROFILE_URL = "/member/password";

    @GetMapping(SETTING_PROFILE_URL)
    public String updatePasswordForm(@AuthenticationPrincipal Member memberInfo, Model model) {
        Member member = null;
        if (memberInfo == null) {
            if (boardService.getLoginGoogle() != null) {
                member = memberService.findMemberInfoFromMemberInfoDTO(boardService.getLoginGoogle().getEmail()).get();
            }
        } else {
            member = memberInfo;
        }

        model.addAttribute(member);
        model.addAttribute(new PasswordFormDto());
        return SETTING_PROFILE_VIEW_NAME;
    }

//    @PostMapping(SETTING_PROFILE_URL)
//    public String updatedPassword(@AuthenticationPrincipal Member memberInfo, @Valid PasswordFormDto passwordFormDto, Errors errors,
//                                  Model model, RedirectAttributes attributes) {
//        Member member = null;
//        if (memberInfo == null) {
//            if (boardService.getLoginGoogle() != null) {
//                member = memberService.findMemberInfoFromMemberInfoDTO(boardService.getLoginGoogle().getEmail()).get();
//            }
//        } else {
//            member = memberInfo;
//        }
//
//        //에러
//        if (errors.hasErrors()) {
//            model.addAttribute(member);
//            return SETTING_PROFILE_VIEW_NAME;
//        }
//
//        //변경완료
//        memberService.updatePassword(member, passwordFormDto.getNewPassword());
//        attributes.addFlashAttribute("message", "패스워드를 변경했습니다.");
//        return "redirect:" + SETTING_PROFILE_URL;
//    }
}
