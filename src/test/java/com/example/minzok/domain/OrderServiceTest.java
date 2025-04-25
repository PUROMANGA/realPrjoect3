package com.example.minzok.domain;

import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.enums.UserRole;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.menu.Entity.Menu;
import com.example.minzok.menu.Repository.MenuRepository;
import com.example.minzok.order.dto.request.OrderRequestDto;
import com.example.minzok.order.dto.request.OrderStatusRequestDto;
import com.example.minzok.order.dto.response.OrderDetailResponseDto;
import com.example.minzok.order.dto.response.OrderResponseDto;
import com.example.minzok.order.dto.response.OrderStatusResponseDto;
import com.example.minzok.order.entity.Order;
import com.example.minzok.order.entity.OrderMenu;
import com.example.minzok.order.entity.OrderStatus;
import com.example.minzok.order.repository.OrderRepository;
import com.example.minzok.order.service.OrderService;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Member member;
    private Store store;
    private Menu menu;
    private OrderRequestDto orderRequestDto;

    @BeforeEach
    void setUp() {
        // Member 생성 (팩토리 메서드 사용)
        member = Member.of(
                "example@email.com",
                "pw1234",
                UserRole.USER,
                "SAM",
                "nickname",
                LocalDate.of(1995, 4, 24)
        );

        // Store 생성 (StoreRequestDto와 유사한 구조)
        store = new Store(
                "둘이먹다 하나가 죽은 호식이치킨",
                "정말 죽어서 경찰서 갔다왔습니다.",
                LocalTime.parse("09:00"),
                LocalTime.parse("23:00"),
                15000,
                member
        );

        // Menu 생성
        menu = new Menu(
                "후라이드 치킨",
                15000L,
                "바삭한 치킨",
                store
        );

        // OrderRequestDto 생성
        orderRequestDto = new OrderRequestDto(1L, 1L, 2);
    }

    // 주문 생성 성공 테스트
    @Test
    void createOrder_성공() {
        // Given
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            ReflectionTestUtils.setField(order, "id", 1L);
            order.calculateTotalPrice();

            return order;
        });

        // When
        OrderResponseDto response = orderService.createOrder(1L, orderRequestDto);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getOrderId());
        assertEquals(30000L, response.getTotalPrice());
        assertEquals(OrderStatus.WAITING.name(), response.getOrderStatus());

    }

    // 최소 주문 금액 미달 테스트
    @Test
    void createOrder_최소주문금액() {
        // Given
        store.setMinimumOrderAmount(30001); // 최소 금액 30,000원 설정, 30,001원 설정.
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        // When & Then
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class,
                () -> orderService.createOrder(1L, orderRequestDto));
        assertEquals(ExceptionCode.MINIMUM_ORDER_AMOUNT, exception.getExceptionCode());
    }

    // 영업 시간이 아닐 때 주문 테스트
    @Test
    void createOrder_영업시간이아님() {
        // Given
        store.setOpenTime(LocalTime.of(10, 0)); // 오전 10시
        store.setCloseTime(LocalTime.of(18, 0)); // 오후 6시
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        // When & Then
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class,
                () -> orderService.createOrder(1L, orderRequestDto));
        assertEquals(ExceptionCode.NOT_STORE_TIME, exception.getExceptionCode());
    }

    // 주문 상태 변경 테스트
    @Test
    void changeOrderStatus_성공() {
        // Given
        Order order = new Order();
        ReflectionTestUtils.setField(order, "id", 1L); // ID 강제 세팅
        ReflectionTestUtils.setField(order, "orderStatus", OrderStatus.WAITING);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // When
        OrderStatusResponseDto response = orderService.changeOrderStatus(
                1L,
                new OrderStatusRequestDto("CONFIRM")
        );

        // Then
        assertEquals(OrderStatus.CONFIRM.name(), response.getOrderStatus());
        assertNotNull(response.getStatusChangedTime());
    }
    // 주문 상세 조회 테스트
    @Test
    void getOrderDetail_성공() {
        // Given
        Order order = new Order();
        ReflectionTestUtils.setField(order, "id", 1L); // ID 강제 세팅
        ReflectionTestUtils.setField(order, "store", store);
        ReflectionTestUtils.setField(order, "totalPrice", 20000L);
        ReflectionTestUtils.setField(order, "orderStatus", OrderStatus.WAITING);

        OrderMenu orderMenu = new OrderMenu();
        ReflectionTestUtils.setField(orderMenu, "menu", menu);
        ReflectionTestUtils.setField(orderMenu, "quantity", 2);

        ReflectionTestUtils.setField(order, "orderMenus", List.of(orderMenu));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // When
        OrderDetailResponseDto response = orderService.getOrderDetail(1L);

        // Then
        assertEquals(1, response.getMenus().size());
        assertEquals("후라이드 치킨", response.getMenus().get(0).getMenuName());
        assertEquals(15000L, response.getMenus().get(0).getPrice());
    }
}
