package com.sgwannabig.smallgift.springboot.service;

import com.sgwannabig.smallgift.springboot.domain.Product;
import com.sgwannabig.smallgift.springboot.dto.settlement.SalesInfo;
import com.sgwannabig.smallgift.springboot.dto.settlement.SalesStatusRequestDto;
import com.sgwannabig.smallgift.springboot.dto.settlement.SalesStatusResponseDto;
import com.sgwannabig.smallgift.springboot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SalesStatusService {
    private final ProductRepository productRepository;

    private int listNum;
    private long productPriceSum;
    private long discountPriceSum;

    // 판매 현황 + 내역 반환
    public SalesStatusResponseDto salesStatus(SalesStatusRequestDto salesStatusRequestDto) throws ParseException {
        String start_dt = salesStatusRequestDto.getStartDate();
        String end_dt = salesStatusRequestDto.getEndDate();

        // String to DateTime
        SimpleDateFormat dateParser = new SimpleDateFormat("yy/MM/dd");
        Date st_date = dateParser.parse(start_dt);
        Date ed_sate = dateParser.parse(end_dt);

        List<Product> salesDetails = productRepository.findByCreateDateBetween(st_date, ed_sate);
        if(salesDetails == null) {
            return null;
        }

        SalesStatusResponseDto result = new SalesStatusResponseDto();
        initSalesDetails();
        result = saveSalesDetails(result, salesDetails);

        return result;
    }

    // 판매 내역 저장
    private SalesStatusResponseDto saveSalesDetails(SalesStatusResponseDto result, List<Product> salesDetails) {

        List<SalesInfo> infoList = new ArrayList<>();

        for(Product product : salesDetails) {
            listNum++;
            productPriceSum += product.getProductPrice();
            discountPriceSum += product.getDiscountPrice();

            SalesInfo salesInfo = new SalesInfo();
            salesInfo.setSalesDate(product.getCreateDate());
            salesInfo.setProductName(product.getProductName());
            salesInfo.setProductPrice(product.getProductPrice());
            salesInfo.setProductBuyer(product.getProductBuyer());
            infoList.add(salesInfo);
        }

        result.setSalesCount(listNum);
        result.setSalesPrice(productPriceSum);
        result.setDiscountPrice(discountPriceSum);
        result.setSalesList(infoList);

        return result;
    }

    // 매 조회시 초기화 후 다시 카운트
    private void initSalesDetails() {
        listNum = 0;
        productPriceSum = 0;
        discountPriceSum = 0;
    }
}
