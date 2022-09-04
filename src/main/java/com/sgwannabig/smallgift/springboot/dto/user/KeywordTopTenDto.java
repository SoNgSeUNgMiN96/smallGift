package com.sgwannabig.smallgift.springboot.dto.user;


import com.sgwannabig.smallgift.springboot.domain.KeyValueDto;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
@Builder
public class KeywordTopTenDto {
    List<KeyValueDto<Integer,String>> keywordTopTen;
}
