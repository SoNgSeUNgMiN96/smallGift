package com.sgwannabig.smallgift.springboot.dto.user;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class WishProductDto {

    long wishListId;

    long productId;

    String category;

    String productName;

    String productImage;

    int productPrice;

    int discountPrice;

    long productStock;

    int status;
}
