package com.sgwannabig.smallgift.springboot.dto.shop;

import com.sgwannabig.smallgift.springboot.dto.KeyValueDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class ShopAllMenuDto {
    List<KeyValueDto<Integer,ProductInfoDto>> shopAllMenuList;
}
