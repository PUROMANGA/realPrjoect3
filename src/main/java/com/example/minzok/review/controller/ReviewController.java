package com.example.minzok.review.controller;

import com.example.minzok.auth.entity.MyUserDetail;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.order.entity.Order;
import com.example.minzok.order.repository.OrderRepository;
import com.example.minzok.review.dto.request.ReviewSaveRequestDto;
import com.example.minzok.review.dto.request.ReviewUpdateRequestDto;
import com.example.minzok.review.dto.response.ReviewResponseDto;
import com.example.minzok.review.service.ReviewService;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Validated
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    @PostMapping("/{storeId}/{orderId}")
    public ResponseEntity<ReviewResponseDto> saveReview(@RequestBody ReviewSaveRequestDto request, @PathVariable Long storeId, @PathVariable Long orderId, @AuthenticationPrincipal MyUserDetail myUserDetail) {

        Member findMember = memberRepository.findMemberByEmail(myUserDetail.getUsername()).orElseThrow(() -> new CustomRuntimeException(ExceptionCode.REVIEW_MEMBER_NOT_FOUND));;
        Store findStore = storeRepository.findById(storeId).orElseThrow(() -> new CustomRuntimeException(ExceptionCode.REVIEW_STORE_NOT_FOUND));;
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new CustomRuntimeException(ExceptionCode.REVIEW_ORDER_NOT_FOUND));;
        ReviewResponseDto reviewResponseDto = reviewService.saveReview(findMember, findStore, findOrder, request.getContents(), request.getRating());

        return new ResponseEntity<>(reviewResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> findAll() {

        List<ReviewResponseDto> reviewResponseDto = reviewService.findAll();

        return new ResponseEntity<>(reviewResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<List<ReviewResponseDto>> findAllByStoreId(@PathVariable Long storeId) {

        List<ReviewResponseDto> reviewResponseDto = reviewService.findAllByStoreId(storeId);

        return new ResponseEntity<>(reviewResponseDto, HttpStatus.OK);

    }

    @GetMapping("/search")
    public ResponseEntity<List<ReviewResponseDto>> searchFindByRating(
            @RequestParam(defaultValue = "1") int min,
            @RequestParam(defaultValue = "5") int max
    ) {
        List<ReviewResponseDto> reviewResponseDto = reviewService.searchFindByRating(min, max);

        return new ResponseEntity<>(reviewResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long id, @RequestBody ReviewUpdateRequestDto request, @AuthenticationPrincipal MyUserDetail myUserDetail) {

//        Optional<Member> findMember = memberRepository.findMemberByEmail(myUserDetail.getUsername());

        ReviewResponseDto reviewResponseDto = reviewService.updateReview(id, request.getContents(), request.getRating(), myUserDetail.getUsername());


        return new ResponseEntity<>(reviewResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id, @AuthenticationPrincipal MyUserDetail myUserDetail) {

        reviewService.deleteReview(id, myUserDetail.getUsername());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
