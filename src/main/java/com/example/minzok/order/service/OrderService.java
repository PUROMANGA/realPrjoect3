package com.example.minzok.order.service;

import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.menu.Entity.Menu;
import com.example.minzok.menu.Repository.MenuRepository;
import com.example.minzok.order.dto.request.OrderRequestDto;
import com.example.minzok.order.dto.response.OrderResponseDto;
import com.example.minzok.order.repository.OrderRepository;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    /**
     주문 생성, 주문 상태 변경, 주문 상세 조회
     */
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    // 주문 생성
    @Transactional
    public OrderResponseDto createOrder(Long memberId, OrderRequestDto orderRequestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Store store = storeRepository.findById(orderRequestDto.getStoreId()).orElseThrow();
        Menu menu = menuRepository.findById(orderRequestDto.getMenuId()).orElseThrow();

        return null;
    }
}
