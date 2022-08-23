package com.sgwannabig.smallgift.springboot.domain.file;

import lombok.Data;
import lombok.NoArgsConstructor;
import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
public class UploadFile {
    // 서로 다른 클라이언트가 파일 이름을 업로드 하는 경우 충돌 가능성 대비
    // 별도의 파일명을 서버에 저장

    private String uploadFileName;
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}