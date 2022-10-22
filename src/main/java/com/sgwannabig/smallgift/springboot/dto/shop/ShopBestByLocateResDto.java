package com.sgwannabig.smallgift.springboot.dto.shop;

import com.sgwannabig.smallgift.springboot.dto.KeyValueDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ShopBestByLocateResDto {
    List<KeyValueDto<Integer,ShopInfoDto>> topShopByLocate;
}