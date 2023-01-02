package com.www.scheduleer.service.Member;

import com.google.cloud.storage.*;
import com.www.scheduleer.controller.dto.member.UploadReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class GCSService {
    @Autowired
    private Storage storage;
    @Value("${spring.cloud.gcp.credentials.location}")
    String keyFileName;

    @SuppressWarnings("deprecation")
    public BlobInfo uploadFileToGCS(UploadReqDto uploadReqDto) throws IOException {

        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(uploadReqDto.getBucketName(), uploadReqDto.getUploadFileName())
                        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.READER))))
                        .build(),
                new FileInputStream(uploadReqDto.getLocalFileLocation()));

        return blobInfo;
    }
}
