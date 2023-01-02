package com.www.scheduleer;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.controller.dto.member.ChangePasswdDto;
import com.www.scheduleer.domain.Auth;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.Board.BoardService;
import com.www.scheduleer.service.Member.MemberService;
import com.www.scheduleer.domain.Type;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Log4j2
class ScheduleerApplicationTests {

//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("scheduleer");
//
//    EntityManager em = emf.createEntityManager();
//
//    EntityTransaction tx = em.getTransaction();

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @Test
    void contextLoads() {
    }

    @Test
    void insertMember() {
//        Member member = new Member("osk", "오은석", "osk2090@naver.com", "1111", MemberRole.GENERAL);
//        System.out.println(member.toString());
//        memberRepository.save(member);
    }

    @Test
    void insertBoard() {
//        Board board = new Board(1L, "test1", "test111", false, new Member( "osk", "osk@naver.com", "osk", null, Type.GENERAL, Auth.ROLE_USER.toString()));
//        boardRepository.save(board);
    }

    @Test
    void updateBoard() {
//        BoardInfo b1 = boardRepository.findBoardInfoById(1L);
//        System.out.println(b1.getCheckStar().booleanValue());

//        b1.setCheckStar(false);
//        boardService.save(b1);

//        BoardInfo b2 = boardRepository.findBoardInfoById(1L);
//        System.out.println(b2.getCheckStar().booleanValue());
    }

    @Test
    @Transactional
    void findBoardByWriter() {
        Optional<Member> memberInfo = memberRepository.findByEmail("osk@naver.com");

//        List<BoardInfo> boardInfos = boardService.findBoardInfoByWriter(memberInfo);
//        for (BoardInfo boardInfo : boardInfos) {
//            System.out.println(boardInfo.getTitle());
//        }
    }

    @Transactional
    @Test
    void findMemberAndBoards() {
        List<Member> members = memberService.findMembers("osk@naver.com");
        for (Member member : members) {
            System.out.println(member.getName());
        }
    }

    @Test
    void findMemberById() {
        Optional<Member> member = memberService.findMember(2L);
        if (member.isPresent()) {
            System.out.println(member.get().getEmail());
        }
    }

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Test
    void checkPW() {
        if (passwordEncoder.matches("1111", memberService.getMember("1234@naver.com").get().getPassword())) {
            System.out.println("y");
        } else {
            System.out.println("n");
        }
    }

    @Test
    @DisplayName("패스워드 변경 전후테스트")
    @Transactional
    void changePw() {
        System.out.println("---- 패스워드 수정 전 ----");
        String pw1 = "1234";
        String pw2 = "1111";
        System.out.println("현재 비밀번호: " + pw1);
        assertThat(passwordEncoder.matches(pw1, memberService.getMember("1234@naver.com").get().getPassword())).isTrue();
        ChangePasswdDto dto = new ChangePasswdDto();
        dto.setBeforePasswd(pw1);
        dto.setAfterPasswd(pw2);

        memberService.changePw(dto, memberService.getMember("1234@naver.com").get());

        System.out.println("---- 패스워드 수정 후 ----");
        System.out.println("수정된 비밀번호: " + pw2);
        assertThat(passwordEncoder.matches(pw2, memberService.getMember("1234@naver.com").get().getPassword())).isTrue();

    }
}
