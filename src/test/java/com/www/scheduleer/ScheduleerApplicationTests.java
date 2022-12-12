package com.www.scheduleer;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.web.domain.Auth;
import com.www.scheduleer.web.domain.Board;
import com.www.scheduleer.web.domain.Member;
import com.www.scheduleer.service.Board.BoardService;
import com.www.scheduleer.service.Member.MemberService;
import com.www.scheduleer.web.domain.Type;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Board board = new Board(1L, "test1", "test111", false, new Member( "osk", "osk@naver.com", "osk", null, Type.GENERAL, Auth.USER));
        boardRepository.save(board);
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

    @Test
    void checkPW() {
//        if (memberService.bc().matches("dh985622", memberService.getMember("osk@naver.com").get().getPassword())) {
//            System.out.println("y");
//        } else {
//            System.out.println("n");
//        }
    }
}
