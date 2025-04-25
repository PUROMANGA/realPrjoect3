package com.example.minzok.order.controller;

import com.example.minzok.auth.entity.MyUserDetail;
import com.example.minzok.order.dto.request.OrderRequestDto;
import com.example.minzok.order.dto.request.OrderStatusRequestDto;
import com.example.minzok.order.dto.response.OrderDetailResponseDto;
import com.example.minzok.order.dto.response.OrderResponseDto;
import com.example.minzok.order.dto.response.OrderStatusResponseDto;
import com.example.minzok.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private  final OrderService orderService;

    // 주문 생성
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody OrderRequestDto dto,
            @AuthenticationPrincipal MyUserDetail myUserDetail
            ){
      
        OrderResponseDto response = orderService.createOrder(myUserDetail.getMemberId(),dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 주문 상태 변경
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderStatusResponseDto> changeOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusRequestDto dto
    ){
        OrderStatusResponseDto response = orderService.changeOrderStatus(orderId, dto);
        return ResponseEntity.ok(response);
    }

    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponseDto> getOrderDetail(@PathVariable Long orderId) {
        OrderDetailResponseDto response = orderService.getOrderDetail(orderId);
        return ResponseEntity.ok(response);
    }

}
