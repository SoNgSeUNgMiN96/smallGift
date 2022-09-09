package com.sgwannabig.smallgift.springboot.dto.user;


import com.sgwannabig.smallgift.springboot.dto.KeyValueDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishListResDto {
    List<KeyValueDto<Integer, WishProductDto>> wishList;
}
