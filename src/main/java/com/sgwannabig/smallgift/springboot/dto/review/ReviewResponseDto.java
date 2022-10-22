package com.sgwannabig.smallgift.springboot.dto.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReviewResponseDto {
    @ApiModelProperty(value = "1")
    private long listNum;

    @ApiModelProperty(value = "3")
    private long reviewId;

    @ApiModelProperty(value = "베이비 아포가토 후기")
    private String reviewTitle;

    @ApiModelProperty(value = "만족스러웠습니다 ㅎㅎ 잘먹었습니다")
    private String reviewContents;

    @ApiModelProperty(value = "20221209")
    private String reviewDate;

    // private UploadFile imageFile;
}
