package com.sgwannabig.smallgift.springboot.dto.order;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundDetailsDto {
    long orderNumber;
    String shopName;
    String shopContent;
    String productImage;
    String orderDate;
    int paidAmount;
    int refundAmount;
}
