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

    String shopName;

    String productName;

    String productContent;

    String productImage;

    int productPrice;

    int discountPrice;

    long productStock;

    int status;
}
