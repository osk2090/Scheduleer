package com.www.scheduleer.domain;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class UploadReqDto {
    private String bucketName;
    private String uploadFileName;
    private String localFileLocation;
}
