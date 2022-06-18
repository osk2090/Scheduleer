package com.www.scheduleer.Repository;

import com.www.scheduleer.web.domain.BoardInfo;
import com.www.scheduleer.web.domain.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardInfo, Long> {

//    List<BoardInfo> findAllBy();//모든 사용자의 글을 가져오기

    Optional<BoardInfo> findBoardInfoByWriter(MemberInfo memberInfo);//글쓴이의 작성글만 가져오기

    Optional<BoardInfo> findBoardInfoById(Long boardId);//상세보기

}
