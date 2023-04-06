package com.sgwannabig.smallgift.springboot.controller.user;


import com.sgwannabig.smallgift.springboot.domain.*;
import com.sgwannabig.smallgift.springboot.domain.product.Product;
import com.sgwannabig.smallgift.springboot.domain.shop.Shop;
import com.sgwannabig.smallgift.springboot.dto.order.*;
import com.sgwannabig.smallgift.springboot.repository.*;
import com.sgwannabig.smallgift.springboot.service.ResponseService;
import com.sgwannabig.smallgift.springboot.service.result.SingleResult;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
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


    @Autowired
    RefundDetailsRepository refundDetailsRepository;


    @ApiOperation(value = "/order", notes = "유저의 주문 처리 후 주문내역을 반환합니다.")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "memberId", value = "멤버 아이디", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "잘못된 productId"),
            //@ApiResponse(code = 408, message = "유저 ID에 매치되는 userInfo가 없습니다. 기본주소를 사용해주세요."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @PostMapping("/order")
    public SingleResult<OrderDetailsDto> createOrder(@RequestBody OrderDto orderDto) {


        User orderer = userRepository.findByMemberId(orderDto.getMemberId());


        List<OrderDetailsDto> orderDetailsList = new ArrayList<>();

        int amountSum=0;


        long productId = orderDto.getProductId();
        Optional<Product> product = productRepository.findById(productId);


        if(product.isEmpty()){
            return responseService.getfailResult(401, new OrderDetailsDto());
        }

        amountSum = product.get().getProductPrice();


        // 결제를 찍고
        Payment payment = Payment.builder()
                .payCheck(true)
                .payPrice(amountSum)
                .payMethod("신용카드")
                .build();

        paymentRepository.save(payment);


        OrderDetails orderDetails = OrderDetails.builder()
                .payment(payment)
                .product(product.get())
                .user(orderer)
                .quantity(1)
                .totalAmount(amountSum)
                .productPrice(product.get().getProductPrice())
                .isRefund(false)
                .build();

        orderdetailsRepository.save(orderDetails);


        OrderDetailsDto orderDetailsDto =  OrderDetailsDto.builder()
                .productId(payment.getId())
                .orderDetailsId(orderDetails.getId())
                .productId(product.get().getId())
                .userId(orderer.getId())
                .productName(orderDetails.getProduct().getProductName())
                .productContent(orderDetails.getProduct().getProductContent())
                .productImage(orderDetails.getProduct().getProductImage())
                .quantity(1)
                .totalAmount(amountSum)
                .productPrice(product.get().getProductPrice())
                .build();
    
        //couponNumber 12자리

        String couponNumber ="";
        while (true) {
            couponNumber = getCoupon();
            if (!ecouponRepository.existsByCouponNumber(couponNumber)) {
                break;
            }
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        int year = currentDateTime.getYear();
        int month = currentDateTime.getMonthOfYear();
        int day = currentDateTime.getDayOfMonth();
        month +=3;
        if (month > 12) {
            month -= 12;
            year++;
        }

        java.time.LocalDateTime expirationTime = java.time.LocalDateTime.of(year, month, day, 0, 0,0,0);

        // 여기도 second,nanoSecond 매개변수는 필수가 아닌 선택입니다.



        Ecoupon ecoupon = Ecoupon.builder()
                .couponNumber(couponNumber)
                .orderDetails(orderDetails)
                .expirationTime(expirationTime)
                .user(orderer)
                .payment(payment)
                .product(product.get())
                .useState("N")
                .build();

        ecouponRepository.save(ecoupon);




        //상품별 주문내역들을 남기고

        //주문내역별 (수량을 기준으로 여러개의 쿠폰을 만들고

        return responseService.getSingleResult(orderDetailsDto);
    }

    private String getCoupon() {
        long digit = 9000000000000L;
        double random = Math.random() * digit;
        long randomNumber = (long) random + digit/9;
        String couponNumber = String.valueOf(randomNumber);
        return couponNumber;
    }


    /*
        유저의 주문내역 조회
     */

    @ApiOperation(value = "/order/all", notes = "유저의 모든 주문 내역을 반환합니다. ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "멤버 아이디", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 408, message = "멤버 아이디에 매치되는 유저가 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("/order/all")
    public SingleResult<AllOrderDetailsDto> gerAllOrders(@RequestParam long memberId) {

        User findUser = userRepository.findByMemberId(memberId);

        if(findUser==null){
            return responseService.getfailResult(408, null);
        }

        List<OrderDetails> userOrderDetailsList = orderdetailsRepository.findAllByUserId(findUser.getId());

        AllOrderDetailsDto allOrderDetailsDto = AllOrderDetailsDto.builder().orderDetailsDtoList(new ArrayList<>()).build();


        userOrderDetailsList.stream().forEach(orderDetails -> {

            if (!orderDetails.isRefund()) {
                OrderDetailsDto userOrderDetailsDto = OrderDetailsDto.builder()
                        .orderDetailsId(orderDetails.getId())
                        .shopId(orderDetails.getProduct().getShop().getShopName())
                        .paymentId(orderDetails.getPayment().getId())
                        .productId(orderDetails.getProduct().getId())
                        .productName(orderDetails.getProduct().getProductName())
                        .productContent(orderDetails.getProduct().getProductContent())
                        .productImage(orderDetails.getProduct().getProductImage())
                        .quantity(orderDetails.getQuantity())
                        .orderDate(orderDetails.getCreateDate().toString())
                        .productPrice(orderDetails.getProductPrice())
                        .totalAmount(orderDetails.getTotalAmount())
                        .build();

                allOrderDetailsDto.getOrderDetailsDtoList().add(userOrderDetailsDto);
            }
        });


        return responseService.getSingleResult(allOrderDetailsDto);
    }


    /*
          주문내역의 쿠폰조회
     */
    @ApiOperation(value = "/order/coupon", notes = "유저의 쿠폰을 봅니다 , 해당 맴버아이디와, 해당 orderDetailsId를 통해 조회.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "멤버 아이디", required = true),
            @ApiImplicitParam(name = "orderDetailsId", value = "orderDetailsId 아이디", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 408, message = "멤버 아이디에 매치되는 유저가 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("/order/coupon")
    public SingleResult<EcouponDto> getCouponByOrderId(@RequestParam long memberId, @RequestParam long orderDetailsId) {

        User findUser = userRepository.findByMemberId(memberId);

        if(findUser==null){
            return responseService.getfailResult(408, null);
        }


        List<Ecoupon> ecoupons = ecouponRepository.findByUserId(findUser.getId());

        Optional<Ecoupon> optionalEcoupon = ecoupons.stream().filter(ecoupon -> ecoupon.getOrderDetails().getId() == orderDetailsId).findAny();


        Ecoupon ecouponParse = optionalEcoupon.get();


        EcouponDto ecouponDto = EcouponDto.builder()
                .couponNumber(ecouponParse.getCouponNumber())
                .expirationTime(ecouponParse.getExpirationTime())
                .productName(ecouponParse.getProduct().getProductName())
                .productImage(ecouponParse.getProduct().getProductImage())
                .useState(ecouponParse.getUseState())
                .paymentId(ecouponParse.getPayment().getId())
                .usedTime(ecouponParse.getUseState().equals("Y") ? ecouponParse.getUsedTime() : null)
                .build();

        return responseService.getSingleResult(ecouponDto);
    }


    @ApiOperation(value = "/order", notes = "유저의 모든 주문 내역을 반환합니다. ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "멤버 아이디", required = true),
            @ApiImplicitParam(name = "memberId", value = "멤버 아이디", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 407, message = "orderDetailsId 매치되는 ecoupon이 없습니다."),
            @ApiResponse(code = 407, message = "memberId 매치 X"),
            @ApiResponse(code = 408, message = "orderDetailsId 매치되는 주문내역이 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @DeleteMapping("/order")
    public SingleResult<Boolean> refundOrder(@RequestParam long memberId, @RequestParam long orderDetailsId) {


        Ecoupon ecouponRepositoryByOrderDetailsId = ecouponRepository.findByOrderDetailsId(orderDetailsId);

        if (ecouponRepositoryByOrderDetailsId == null) {
            return responseService.getfailResult(406, false);
        }

        ecouponRepositoryByOrderDetailsId.setUseState("R");

        ecouponRepository.save(ecouponRepositoryByOrderDetailsId);


        User userByMemberId = userRepository.findByMemberId(memberId);

        if (userByMemberId == null) {
            return responseService.getfailResult(408, false);
        }

        RefundDetails refundDetails =RefundDetails.builder()
                .ecoupon(ecouponRepositoryByOrderDetailsId)
                .user(userByMemberId)
                .refundStatus(true)
                .build();

        refundDetailsRepository.save(refundDetails);

        Optional<OrderDetails> orderdetailsById = orderdetailsRepository.findById(orderDetailsId);

        if (orderdetailsById.isEmpty()) {
            return responseService.getfailResult(408, false);
        }

        orderdetailsById.get().setRefund(true);

        orderdetailsRepository.save(orderdetailsById.get());


        return responseService.getSingleResult(true);
    }



    @ApiOperation(value = "/order/refund/all", notes = "유저의 모든 주문 내역을 반환합니다. ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "멤버 아이디", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 408, message = "멤버 아이디에 매치되는 유저가 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("/order/refund/all")
    public SingleResult<AllRefundDefailsDto> gerAllRefund(@RequestParam long memberId) {

        User findUser = userRepository.findByMemberId(memberId);

        if(findUser==null){
            return responseService.getfailResult(408, null);
        }

        List<RefundDetails> userRefundDetailsList = refundDetailsRepository.findAllByUserId(findUser.getId());

        AllRefundDefailsDto allRefundDefailsDto = AllRefundDefailsDto.builder().refundDetailsDtoList(new ArrayList<>()).build();


        userRefundDetailsList.stream().forEach(refundDetails -> {

            Ecoupon ecoupon = refundDetails.getEcoupon();
            java.time.LocalDateTime createDate = ecoupon.getCreateDate();
            Product product = ecoupon.getProduct();
            Shop shop = product.getShop();

            RefundDetailsDto refundDetailsDto = RefundDetailsDto.builder()
                    .shopName(shop.getShopName())
                    .shopContent(product.getProductName())
                    .productImage(product.getProductImage())
                    .orderNumber(ecoupon.getOrderDetails().getId())
                    .orderDate(createDate.toString())
                    .refundAmount(product.getProductPrice())
                    .paidAmount(product.getProductPrice())
                    .build();

            allRefundDefailsDto.getRefundDetailsDtoList().add(refundDetailsDto);
        });


        return responseService.getSingleResult(allRefundDefailsDto);
    }



}
