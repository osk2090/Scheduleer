package com.www.scheduleer.Repository;

import com.www.scheduleer.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByBoardId(Long boardId);
}
