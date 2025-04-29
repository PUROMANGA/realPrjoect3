package com.example.minzok.domain;

import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.order.entity.Order;
import com.example.minzok.order.repository.OrderRepository;
import com.example.minzok.review.repository.ReviewRepository;
import com.example.minzok.review.service.ReviewService;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.repository.StoreRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private OrderRepository orderRepository;

}
