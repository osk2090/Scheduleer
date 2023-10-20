package com.www.scheduleer.member;

import com.www.scheduleer.controller.dto.member.BoardInfoDto;
import com.www.scheduleer.controller.dto.member.MemberInfoDto;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.controller.dto.member.UploadReqDto;
import com.www.scheduleer.service.board.BoardService;
import com.www.scheduleer.service.member.S3Uploader;
import com.www.scheduleer.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class memberServiceTests {
    @Autowired
    private S3Uploader s3Uploader;
    @Autowired
    private MemberService memberService;
    @Autowired
    private BoardService boardService;

    @Test
    @Transactional
    @DisplayName("GCS 업로드 테스트")
    public void uploadProfile() throws IOException {
        long stime = System.currentTimeMillis();

        UploadReqDto dto = new UploadReqDto();
        dto.setBucketName("scheduleer");
        dto.setUploadFileName("uploadGCS/profile/profile-test-file.png");// 유저 email별로 파일 받기
        dto.setLocalFileLocation("/Users/osk2090/Documents/Git/Scheduleer/src/main/resources/static/image/profile-test-file.png");
//        BlobInfo blobInfo = gcsService.uploadFileToGCS(dto);
//        System.out.println(blobInfo.getMediaLink());
        System.out.println("소요시간:"+(System.currentTimeMillis()-stime)+"ms");
    }

    @Test
    @Transactional
    @DisplayName("/member/myInfo")
    public void getMemberInfo() {
        long stime = System.currentTimeMillis();

        String email = "1234@naver.com";
        Optional<Member> m = memberService.getMember(email);
        List<Board> b = boardService.findBoardInfoByWriter(m.orElse(null));
        Member getMember = null;
        List<BoardInfoDto> boardInfoDtoList = new ArrayList<>();

        if (b.size() > 0) {
            b.forEach(data -> {
                boardInfoDtoList.add(BoardInfoDto.builder()
                        .title(data.getTitle())
                        .createDate(data.getRegDate())
                        .isCheck(data.getCheckStar())
                        .build());
            });
        }

        if (m.isPresent()) {
            getMember = m.get();
        }
        System.out.println("소요시간:"+(System.currentTimeMillis()-stime)+"ms");
        System.out.println(
                MemberInfoDto.builder().name(getMember.getName()).email(getMember.getEmail()).password(getMember.getPassword()).picture(getMember.getPicture()));
    }
}
