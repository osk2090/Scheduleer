package com.www.scheduleer.member;

import com.google.cloud.storage.BlobInfo;
import com.www.scheduleer.domain.UploadReqDto;
import com.www.scheduleer.service.Member.GCSService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Log4j2
public class memberServiceTests {
    @Autowired
    private GCSService gcsService;

    @Test
    @DisplayName("GCS 업로드 테스트")
    public void uploadProfile() throws IOException {
        long stime = System.currentTimeMillis();

        UploadReqDto dto = new UploadReqDto();
        dto.setBucketName("scheduleer");
        dto.setUploadFileName("uploadGCS/profile/profile-test-file.png");// 유저 email별로 파일 받기
        dto.setLocalFileLocation("/Users/osk2090/Documents/Git/Scheduleer/src/main/resources/static/image/profile-test-file.png");
        BlobInfo blobInfo = gcsService.uploadFileToGCS(dto);
        System.out.println(blobInfo.getMediaLink());
        System.out.println("소요시간:"+(System.currentTimeMillis()-stime)+"ms");
    }
}
