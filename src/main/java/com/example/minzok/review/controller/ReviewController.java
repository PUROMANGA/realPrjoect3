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

    /**
     * 리뷰 생성
     * @param request
     * @param storeId
     * @param orderId
     * @param myUserDetail
     * @return
     */
    @PostMapping("/{storeId}/{orderId}")
    public ResponseEntity<ReviewResponseDto> saveReview(@RequestBody ReviewSaveRequestDto request, @PathVariable Long storeId, @PathVariable Long orderId, @AuthenticationPrincipal MyUserDetail myUserDetail) {

        Member findMember = memberRepository.findMemberByEmail(myUserDetail.getUsername()).orElseThrow(() -> new CustomRuntimeException(ExceptionCode.CANT_FIND_MEMBER));;
        Store findStore = storeRepository.findById(storeId).orElseThrow(() -> new CustomRuntimeException(ExceptionCode.CANT_FIND_STORE));;
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new CustomRuntimeException(ExceptionCode.CANT_FIND_ORDER));;
        ReviewResponseDto reviewResponseDto = reviewService.saveReview(findMember, findStore, findOrder, request.getContents(), request.getRating());

        return new ResponseEntity<>(reviewResponseDto, HttpStatus.CREATED);
    }

    /**
     * 리뷰 전체 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> findAll() {

        List<ReviewResponseDto> reviewResponseDto = reviewService.findAll();

        return new ResponseEntity<>(reviewResponseDto, HttpStatus.OK);
    }

    /**
     * 상점별 리뷰 조회
     * @param storeId
     * @return
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<List<ReviewResponseDto>> findAllByStoreId(@PathVariable Long storeId) {

        List<ReviewResponseDto> reviewResponseDto = reviewService.findAllByStoreId(storeId);

        return new ResponseEntity<>(reviewResponseDto, HttpStatus.OK);

    }

    /**
     * 리뷰 평점 검색 조회
     * @param min
     * @param max
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<List<ReviewResponseDto>> searchFindByRating(
            @RequestParam(defaultValue = "1") int min,
            @RequestParam(defaultValue = "5") int max
    ) {
        List<ReviewResponseDto> reviewResponseDto = reviewService.searchFindByRating(min, max);

        return new ResponseEntity<>(reviewResponseDto, HttpStatus.OK);
    }

    /**
     * 리뷰 수정
     * @param id
     * @param request
     * @param myUserDetail
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long id, @RequestBody ReviewUpdateRequestDto request, @AuthenticationPrincipal MyUserDetail myUserDetail) {

//        Optional<Member> findMember = memberRepository.findMemberByEmail(myUserDetail.getUsername());

        ReviewResponseDto reviewResponseDto = reviewService.updateReview(id, request.getContents(), request.getRating(), myUserDetail.getUsername());


        return new ResponseEntity<>(reviewResponseDto, HttpStatus.OK);
    }

    /**
     * 리뷰 삭제
     * @param id
     * @param myUserDetail
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id, @AuthenticationPrincipal MyUserDetail myUserDetail) {

        reviewService.deleteReview(id, myUserDetail.getUsername());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
