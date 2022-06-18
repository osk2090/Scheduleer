package com.www.scheduleer.service;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.web.domain.BoardInfo;
import com.www.scheduleer.web.domain.MemberInfo;
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

    @PostConstruct
    @Transactional
    public void insert() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();//암호화
        MemberInfo memberInfo = new MemberInfo("osk", "osk@naver.com", encoder.encode("osk"), null, "ROLE_USER");
        MemberInfo memberInfo1 = new MemberInfo("osk1", "osk1@naver.com", encoder.encode("osk1"), null, "ROLE_USER");

        memberRepository.save(memberInfo);
        memberRepository.save(memberInfo1);
        boardRepository.save(new BoardInfo(1L, "test title", "test content", true, memberInfo));
        boardRepository.save(new BoardInfo(2L, "test1 title", "test1 content", true, memberInfo1));
    }
}
