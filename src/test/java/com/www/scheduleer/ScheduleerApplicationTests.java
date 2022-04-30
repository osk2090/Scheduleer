package com.www.scheduleer;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.VO.BoardInfo;
import com.www.scheduleer.VO.MemberInfo;
import com.www.scheduleer.service.Board.BoardService;
import com.www.scheduleer.service.Member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

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
        BoardInfo boardInfo = new BoardInfo(1L, "test1", "test111", false, new MemberInfo("osk", "osk@naver.com", "osk", "admin"));
        boardRepository.save(boardInfo);
    }

    @Test
    void updateBoard() {
        BoardInfo b1 = boardRepository.findBoardInfoById(1L);
        System.out.println(b1.getCheckStar().booleanValue());

        b1.setCheckStar(false);
//        boardService.save(b1);

        BoardInfo b2 = boardRepository.findBoardInfoById(1L);
        System.out.println(b2.getCheckStar().booleanValue());
    }

    @Test
    @Transactional
    void findBoardByWriter() {
        Optional<MemberInfo> memberInfo = memberRepository.findByEmail("osk@naver.com");

//        List<BoardInfo> boardInfos = boardService.findBoardInfoByWriter(memberInfo);
//        for (BoardInfo boardInfo : boardInfos) {
//            System.out.println(boardInfo.getTitle());
//        }
    }

    @Transactional
    @Test
    void findMemberAndBoards() {
        List<MemberInfo> members = memberService.findMembers("osk@naver.com");
        for (MemberInfo memberInfo : members) {
            System.out.println(memberInfo.getName());
        }
    }
}
