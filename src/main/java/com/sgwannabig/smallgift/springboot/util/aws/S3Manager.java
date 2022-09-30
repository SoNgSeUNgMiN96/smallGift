package com.sgwannabig.smallgift.springboot.util.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class S3Manager {

  private final AmazonS3 amazonS3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  @Transactional
  public String uploadFile(MultipartFile file, ObjectMetadata objectMetadata, String fileName) {
    try (InputStream inputStream = file.getInputStream()) {
      amazonS3Client.putObject(
          new PutObjectRequest(bucket, fileName, inputStream, objectMetadata).withCannedAcl(
              CannedAccessControlList.PublicReadWrite));
    } catch (IOException e) {
      throw new IllegalArgumentException(
          String.format("파일 변환 중 에러가 발생했습니다 (%s)", file.getOriginalFilename()));
      // custom exception 정의 필요
    }

    return amazonS3Client.getUrl(bucket, fileName).toString();
  }

}
