package com.sgwannabig.smallgift.springboot.dto.user;

import com.sgwannabig.smallgift.springboot.dto.KeyValueDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;




@Data
@Builder
public class RecommendationDto {
    List<KeyValueDto<Integer,String>> recommendationTopTen;
}
