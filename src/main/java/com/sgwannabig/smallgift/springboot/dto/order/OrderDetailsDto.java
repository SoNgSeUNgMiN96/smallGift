package com.sgwannabig.smallgift.springboot.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {

    //주문 아이디.
    @ApiModelProperty(example = "3", dataType = "long")
    private long orderDetailsId;

    //어떤 상품을 샀는지 (동일 상품을 다음에 또 주문할 수 있음.)

    @ApiModelProperty(example = "6", dataType = "long")
    private long productId;

    @ApiModelProperty(example = "6", dataType = "String")
    private String buyDate;

    @ApiModelProperty(example = "스타벅스", dataType = "String")
    private String shopId;


    @ApiModelProperty(example = "아이스 아메리카노", dataType = "String")
    private String productName;

    @ApiModelProperty(example = "http://~", dataType = "String")
    private String productImage;

    @ApiModelProperty(example = "~~원두입니다", dataType = "String")
    private String productContent;

    //누가 샀는지.
    @ApiModelProperty(example = "7", dataType = "long")
    private long userId;


    //주문내역을 근거할 결제번호.
    @ApiModelProperty(example = "10385934", dataType = "long")
    private long paymentId;


    //주문한 주문수량
    @ApiModelProperty(example = "1", dataType = "int")
    int quantity;

    //해당 상품의 가격
    @ApiModelProperty(example = "7800", dataType = "int")
    int productPrice;

    @ApiModelProperty( value = "주문시간",example = "2022-11-11", dataType = "int")
    String orderDate;

    //총 주문 가격
    @ApiModelProperty(example = "7800", dataType = "int")
    int totalAmount;

}
