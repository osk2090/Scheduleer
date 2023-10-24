package com.www.scheduleer.Repository;

import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllBy();//모든 사용자의 글을 가져오기

    List<Board> findBoardByWriter(Member member);//글쓴이의 작성글만 가져오기

//    @Query("select b from Board b join fetch b.writer")
    Optional<Board> findBoardInfoById(Long boardId);//상세보기

}
