package com.www.scheduleer.service.Board;

import com.www.scheduleer.Repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

}
