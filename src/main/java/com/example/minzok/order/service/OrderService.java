package com.example.minzok.order.service;

import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.menu.Entity.Menu;
import com.example.minzok.menu.Repository.MenuRepository;
import com.example.minzok.order.aop.OrderLogging;
import com.example.minzok.order.dto.request.OrderRequestDto;
import com.example.minzok.order.dto.request.OrderStatusRequestDto;
import com.example.minzok.order.dto.response.OrderDetailResponseDto;
import com.example.minzok.order.dto.response.OrderResponseDto;
import com.example.minzok.order.dto.response.OrderStatusResponseDto;
import com.example.minzok.order.entity.Order;
import com.example.minzok.order.entity.OrderMenu;
import com.example.minzok.order.entity.OrderStatus;
import com.example.minzok.order.repository.OrderRepository;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    /**
     * 주문 생성, 주문 상태 변경, 주문 상세 조회
     */
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    // 주문 생성
    @OrderLogging
    @Transactional
    public OrderResponseDto createOrder(Long memberId, OrderRequestDto orderRequestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomRuntimeException(ExceptionCode.CANT_FIND_MEMBER));

        Store store = storeRepository.findById(orderRequestDto.getStoreId())
                .orElseThrow(()-> new CustomRuntimeException(ExceptionCode.CANT_FIND_STORE));

        Menu menu = menuRepository.findById(orderRequestDto.getMenuId())
                .orElseThrow(() -> new CustomRuntimeException(ExceptionCode.CANT_FIND_MENU));

        int quantity = orderRequestDto.getQuantity();
        Long totalPrice = menu.getPrice() * quantity;

        // 1. 최소 주문 금액 예외처리
        if (totalPrice < store.getMinimum_order_amount()) {
            throw new CustomRuntimeException(ExceptionCode.MINIMUM_ORDER_AMOUNT);
        }

        // 2. 가게 오픈/마감 시간 예외처리
        LocalTime now = LocalTime.now();
        if (now.isBefore(store.getOpenTime()) || now.isAfter(store.getCloseTime())) {
            throw new CustomRuntimeException(ExceptionCode.NOT_STORE_TIME);
        }

        // 주문 메뉴 생성
        OrderMenu orderMenu = OrderMenu.builder()
                .menu(menu)
                .quantity(orderRequestDto.getQuantity())
                .build();
        List<OrderMenu> orderMenus = new ArrayList<>();
        orderMenus.add(orderMenu);

        Order order = new Order(member, store, orderMenus);
        orderRepository.save(order);

        // 응답 DTO 생성
        // 메뉴에서 가격(getPrice())을 갖고와야 함.
        OrderResponseDto.OrderMResponseDto menuDto = OrderResponseDto.OrderMResponseDto.builder()
                .menuName(menu.getName())
                .quantity(orderMenu.getQuantity())
                .price(menu.getPrice())
                .build();

        return OrderResponseDto.builder()
                .orderId(order.getId())
                .storeId(store.getId())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus().name())
                .orderTime(order.getOrderTime())
                .menus(List.of(menuDto))
                .build();

    }

    // 주문 상태 변경
    @OrderLogging
    @Transactional
    public OrderStatusResponseDto changeOrderStatus(Long orderId, OrderStatusRequestDto dto){
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.changeStatus(OrderStatus.valueOf((dto.getOrderStatus())));
        return OrderStatusResponseDto.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus().name())
                .statusChangedTime(order.getStatusChangedTime())
                .build();
    }

    // 주문 상세 조회
    /*
     MenuDetailDto(주문에 포함된 각 메뉴의 상세정보)
    에 해당하는 클래스를 정의해야 함. menuName, quantity, price.
    */

    @Transactional
    public OrderDetailResponseDto getOrderDetail(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow();

        return OrderDetailResponseDto.builder()
                .orderId(order.getId())
                .storeId(order.getStore().getId())
                .totalPrice(Long.valueOf(order.getTotalPrice()))
                .orderStatus(order.getOrderStatus().name())
                .orderTime(order.getOrderTime())
                .menus(order.getOrderMenus().stream()
                        .map(om -> OrderDetailResponseDto.MenuDetailDto.builder()
                                .menuName(om.getMenu().getName())
                                .quantity(om.getQuantity())
                                .price(om.getMenu().getPrice())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
