package com.sgwannabig.smallgift.springboot.dto.shop;

import com.sgwannabig.smallgift.springboot.dto.KeyValueDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;



@Data
@AllArgsConstructor
public class MenuRandomByLocateResDto {
    List<KeyValueDto<Integer,RandomMenuDto>> menuRandomByLocateResDto;
}