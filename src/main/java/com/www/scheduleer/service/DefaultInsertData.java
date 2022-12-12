package com.www.scheduleer.service;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.web.domain.Auth;
import com.www.scheduleer.web.domain.Board;
import com.www.scheduleer.web.domain.Member;
import com.www.scheduleer.web.domain.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
public class DefaultInsertData {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

//    @PostConstruct
    @Transactional
    public void insert() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();//암호화
        Member member = new Member("osk", "osk@naver.com", encoder.encode("osk"), null, Type.GENERAL, Auth.USER);
        Member member1 = new Member("osk1", "osk1@naver.com", encoder.encode("osk1"), null, Type.GENERAL, Auth.USER);

        memberRepository.save(member);
        memberRepository.save(member1);
        boardRepository.save(new Board(1L, "test title", "test content", true, member));
        boardRepository.save(new Board(2L, "test1 title", "test1 content", true, member1));
    }
}
