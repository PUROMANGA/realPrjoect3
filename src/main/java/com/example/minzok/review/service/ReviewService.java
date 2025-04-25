package com.example.minzok.review.service;

import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import com.example.minzok.order.entity.Order;
import com.example.minzok.review.dto.response.ReviewResponseDto;
import com.example.minzok.review.entity.Review;
import com.example.minzok.review.repository.ReviewRepository;
import com.example.minzok.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    public ReviewResponseDto saveReview(Member member, Store store, Order order, String contents, int rating) {

        Review review = new Review(member, store, order, contents, rating);

        Review saveReview = reviewRepository.save(review);

        return new ReviewResponseDto(saveReview.getId(), saveReview.getContents(), saveReview.getRating(), saveReview.getCreatTime(), saveReview.getModifiedTime());

    }

    public List<ReviewResponseDto> findAll() {
        return reviewRepository.findAll().stream().map(ReviewResponseDto::toDto).toList();
    }

    public List<ReviewResponseDto> findAllByStoreId(Long storeId) {
        return reviewRepository.findAllByStoreId(storeId).stream().map(ReviewResponseDto::toDto).toList();
    }

    public List<ReviewResponseDto> searchFindByRating(int min, int max) {
        return reviewRepository.searchFindByRating(min, max).stream().map(ReviewResponseDto::toDto).toList();
    }

    public ReviewResponseDto findById(Long id) {

        Review findReview = reviewRepository.findByIdOrElseThrow(id);

        return new ReviewResponseDto(findReview.getId(), findReview.getContents(), findReview.getRating(), findReview.getCreatTime(), findReview.getModifiedTime());

    }

    @Transactional
    public ReviewResponseDto updateReview(Long id, String contents, int rating, String memberEmail) {


        Review findReview = reviewRepository.findByIdOrElseThrow(id);
        Optional<Member> findMember = memberRepository.findMemberByEmail(memberEmail);

        if(!findReview.getMember().getEmail().equals(memberEmail)) {
            throw new CustomRuntimeException(ExceptionCode.REVIEW_UPDATE_UNAUTHORIZED);
        }

        findReview.updateReview(id, contents, rating);

        return new ReviewResponseDto(id, contents, rating, findReview.getCreatTime(), findReview.getModifiedTime());
    }

    public void deleteReview(Long id, String memberEmail) {
        Review findReview = reviewRepository.findByIdOrElseThrow(id);
        Optional<Member> findMember = memberRepository.findMemberByEmail(memberEmail);

        if(!findReview.getMember().getEmail().equals(memberEmail)) {
            throw new CustomRuntimeException(ExceptionCode.REVIEW_DELETE_UNAUTHORIZED);
        }

        reviewRepository.delete(findReview);
    }

}
