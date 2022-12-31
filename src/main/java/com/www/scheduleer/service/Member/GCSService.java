package com.www.scheduleer.service.Member;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.www.scheduleer.domain.UploadReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class GCSService {

    private final Storage storage;

    @Value("${spring.cloud.gcp.credentials.location}")
    String keyFileName;

    public BlobInfo uploadFileToGCS(UploadReqDto uploadReqDto){
        try {
            InputStream keyFile = ResourceUtils.getURL("classpath:" + keyFileName).openStream();

            Storage storage = StorageOptions.newBuilder().setProjectId(uploadReqDto.getBucketName())
                    // Key 파일 수동 등록
                    .setCredentials(GoogleCredentials.fromStream(keyFile))
                    .build().getService();

            BlobId blobId = BlobId.of(uploadReqDto.getBucketName(), uploadReqDto.getUploadFileName());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                    .build();

            Blob blob = storage
                    .create(blobInfo, new FileInputStream(uploadReqDto.getLocalFileLocation()));

            return blob;
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public String upload(MultipartFile file) {
        try {
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder("[Bucket_name]", file.getOriginalFilename()).build(), //get original file name
                    file.getBytes(), // the file
                    Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ) // Set file permission
            );
            return blobInfo.getMediaLink(); // Return file url
        }catch(IllegalStateException | IOException e){
            throw new RuntimeException(e);
        }
    }
}
