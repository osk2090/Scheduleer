package com.www.scheduleer.service;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.VO.BoardInfo;
import com.www.scheduleer.VO.MemberInfo;
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
        MemberInfo memberInfo = new MemberInfo("osk", "osk@naver.com", encoder.encode("osk"), "ROLE_USER");

        memberRepository.save(memberInfo);
        boardRepository.save(new BoardInfo("test title", "test content", false, memberInfo));
    }
}
