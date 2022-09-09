package com.sgwannabig.smallgift.springboot.dto.shop;

import com.sgwannabig.smallgift.springboot.dto.KeyValueDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ShopAllByLocateResDto {
    List<KeyValueDto<Integer,ShopInfoDto>> shopAllByLocate;
}