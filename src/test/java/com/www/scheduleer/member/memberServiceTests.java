package com.www.scheduleer.member;

import com.google.cloud.storage.BlobInfo;
import com.www.scheduleer.domain.UploadReqDto;
import com.www.scheduleer.service.Member.GCSService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Log4j2
public class memberServiceTests {
    @Autowired
    private GCSService gcsService;

    @Test
    public void uploadProfile() throws IOException {
        UploadReqDto dto = new UploadReqDto();
        dto.setBucketName("scheduleer");
        dto.setUploadFileName("uploadGCS/profile/profile-test-file.png");
        dto.setLocalFileLocation("/Users/osk2090/Documents/Git/Scheduleer/src/main/resources/static/image/profile-test-file.png");
        BlobInfo fileFromGCS = gcsService.uploadFileToGCS(dto);
        assertThat("--- 프로필 업로드 완료! ---");
    }
}
