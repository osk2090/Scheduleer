package com.www.scheduleer.service;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.controller.dto.board.BoardSaveDto;
import com.www.scheduleer.domain.Auth;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.domain.Type;
import com.www.scheduleer.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Optional;

@Service
public class DefaultInsertData {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberService memberService;

//    @PostConstruct
    @Transactional
    public void insert() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();//암호화
//        Member member = new Member("osk", "osk@naver.com", encoder.encode("osk"), null, Type.GENERAL, Auth.ROLE_USER.toString());
//        Member member1 = new Member("osk1", "osk1@naver.com", encoder.encode("osk1"), null, Type.GENERAL, Auth.ROLE_USER.toString());
//        memberRepository.save(member);
//        memberRepository.save(member1);
        Optional<Member> member = memberService.getMember("1234@naver.com");
        if (member.isPresent()) {
            Member m = member.get();
            boardRepository.save(Board.createEntity(new BoardSaveDto("test title", "test content"), m));
            boardRepository.save(Board.createEntity(new BoardSaveDto("test title1", "test content1"), m));
        }
    }
}
