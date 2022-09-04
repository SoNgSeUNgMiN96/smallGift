package com.sgwannabig.smallgift.springboot.dto.user;


import com.sgwannabig.smallgift.springboot.domain.KeyValueDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserkeywordResponseDto {
    List<KeyValueDto<Integer,String>> userKeywords;
}
