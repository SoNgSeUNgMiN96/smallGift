package com.sgwannabig.smallgift.springboot.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllRefundDefailsDto {
    List<RefundDetailsDto> refundDetailsDtoList;
}
