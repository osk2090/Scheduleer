package com.www.scheduleer.Repository;

import com.www.scheduleer.web.domain.Board;
import com.www.scheduleer.web.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

//    List<BoardInfo> findAllBy();//모든 사용자의 글을 가져오기

    Optional<Board> findBoardInfoByWriter(Member member);//글쓴이의 작성글만 가져오기

    Optional<Board> findBoardInfoById(Long boardId);//상세보기

}
