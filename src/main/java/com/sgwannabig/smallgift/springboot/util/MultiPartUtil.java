//package com.sgwannabig.smallgift.springboot.util;
//
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import java.util.UUID;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//@Component
//public class MultiPartUtil {
//
//  private String createFileId() {
//    return UUID.randomUUID().toString();
//  }
//
//  public String createFilePath(FileDir fileDir, String contentType) {
//    return String.format("%s/%s.%s", fileDir.getDir(), createFileId(), getFormat(contentType));
//  }
//
//  private String getFormat(String contentType) {
//    return contentType.substring(contentType.lastIndexOf("/") + 1);
//  }
//
//  public ObjectMetadata setObjectMetaData(MultipartFile file) {
//    ObjectMetadata objectMetadata = new ObjectMetadata();
//    objectMetadata.setContentLength(file.getSize());
//    objectMetadata.setContentType(file.getContentType());
//    return objectMetadata;
//  }
//
//}
