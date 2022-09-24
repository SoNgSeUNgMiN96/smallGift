package com.sgwannabig.smallgift.springboot.controller.user;


import com.sgwannabig.smallgift.springboot.domain.*;
import com.sgwannabig.smallgift.springboot.dto.order.CreateOrderDto;
import com.sgwannabig.smallgift.springboot.dto.order.OrderDetailsDto;
import com.sgwannabig.smallgift.springboot.dto.order.OrderDto;
import com.sgwannabig.smallgift.springboot.dto.order.OrderedProduct;
import com.sgwannabig.smallgift.springboot.repository.*;
import com.sgwannabig.smallgift.springboot.service.ResponseService;
import com.sgwannabig.smallgift.springboot.service.result.SingleResult;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    OrderdetailsRepository orderdetailsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EcouponRepository ecouponRepository;


    @Autowired
    ResponseService responseService;




    @ApiOperation(value = "/order", notes = "유저의 주문 처리 후 주문내역을 반환합니다. <- Get임. 헷갈리지 않기")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "memberId", value = "멤버 아이디", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            //@ApiResponse(code = 408, message = "유저 ID에 매치되는 userInfo가 없습니다. 기본주소를 사용해주세요."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @PostMapping("/order")
    public SingleResult<CreateOrderDto> createOrder(@RequestBody OrderDto orderDto) {


        Optional<User> orderer = userRepository.findById(orderDto.getUserId());

        List<OrderedProduct> orderedProductList = orderDto.getOrderedProductList();

        List<OrderDetailsDto> orderDetailsList = new ArrayList<>();

        int amountSum=0;

        for (OrderedProduct orderedProduct : orderedProductList) {
            long productId = orderedProduct.getProductId();

            Optional<Product> product = productRepository.findById(productId);


            int amount = product.get().getDiscountPrice() * orderedProduct.getQty();

            amountSum += amount;
        }


        // 결제를 찍고
        Payment payment = Payment.builder()
                .payCheck(true)
                .payPrice(amountSum)
                .payMethod("신용카드")
                .build();

        paymentRepository.save(payment);



        for (OrderedProduct orderedProduct : orderedProductList) {
            long productId = orderedProduct.getProductId();

            Optional<Product> product = productRepository.findById(productId);


            int amount = product.get().getDiscountPrice() * orderedProduct.getQty();



            OrderDetails orderDetails = OrderDetails.builder()
                    .payment(payment)
                    .product(product.get())
                    .user(orderer.get())
                    .quantity(orderedProduct.getQty())
                    .totalAmount(amount)
                    .productPrice(product.get().getDiscountPrice())
                    .build();


            OrderDetailsDto orderDetailsDto =  OrderDetailsDto.builder()
                    .productId(payment.getId())
                    .productId(product.get().getId())
                    .userId(orderer.get().getId())
                    .quantity(orderedProduct.getQty())
                    .totalAmount(amount)
                    .productPrice(product.get().getDiscountPrice())
                    .build();


            orderDetailsList.add(orderDetailsDto);

            orderdetailsRepository.save(orderDetails);

            for (int i = 0; i < orderedProduct.getQty(); i++) {
                Ecoupon ecoupon = Ecoupon.builder()
                        .couponNumber("000000000")
                        .user(orderer.get())
                        .payment(payment)
                        .product(product.get())
                        .useState("N")
                        .build();

                ecouponRepository.save(ecoupon);
            }

            amountSum += amount;
        }


        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .orderDetailsList(orderDetailsList).build();




        //상품별 주문내역들을 남기고

        //주문내역별 (수량을 기준으로 여러개의 쿠폰을 만들고

        return responseService.getSingleResult(createOrderDto);
    }


}
