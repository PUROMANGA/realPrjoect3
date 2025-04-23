package com.example.minzok.review.service;

import com.example.minzok.review.dto.response.ReviewResponseDto;
import com.example.minzok.review.entity.Review;
import com.example.minzok.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewResponseDto saveReview(String contents, int rating) {

        Review review = new Review(contents, rating);

        Review saveReview = reviewRepository.save(review);

        return new ReviewResponseDto(saveReview.getId(), saveReview.getContents(), saveReview.getRating(), saveReview.getCreatTime(), saveReview.getModifiedTime());

    }

    public List<ReviewResponseDto> findAll() {
        return reviewRepository.findAll().stream().map(ReviewResponseDto::toDto).toList();
    }

    public ReviewResponseDto findById(Long id) {

        Review findReview = reviewRepository.findByIdOrElseThrow(id);

        return new ReviewResponseDto(findReview.getId(), findReview.getContents(), findReview.getRating(), findReview.getCreatTime(), findReview.getModifiedTime());

    }

    @Transactional
    public ReviewResponseDto updateReview(Long id, String contents, int rating) {
        Review findReview = reviewRepository.findByIdOrElseThrow(id);

        return new ReviewResponseDto(id, contents, rating, findReview.getCreatTime(), findReview.getModifiedTime());
    }

    public void deleteReview(Long id) {
        Review findReview = reviewRepository.findByIdOrElseThrow(id);

        reviewRepository.delete(findReview);
    }

}
