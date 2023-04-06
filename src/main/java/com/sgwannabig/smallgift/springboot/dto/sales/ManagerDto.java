package com.sgwannabig.smallgift.springboot.dto.sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ManagerDto {
    @ApiModelProperty(value = "13")
    private long managerId;

    @ApiModelProperty(example = "홍길동")
    private String username;

    @ApiModelProperty(example = "홍짜장")
    private String businessName;

    @ApiModelProperty(example = "경기도 안양시 만안구 XXX")
    private String address;

    @ApiModelProperty(example = "999-99-000949")
    private String businessTel;

    @ApiModelProperty(example = "음식점")
    private String businessType;

    @ApiModelProperty(example = "우리은행")
    private String bankName;

    @ApiModelProperty(example = "110-464-062460")
    private String bankAccount;

    @ApiModelProperty(example = "/test/manager.png")
    private MultipartFile managerAttachFile;

    @ApiModelProperty(example = "/test/sales.png")
    private MultipartFile salesAttachFile;
}

