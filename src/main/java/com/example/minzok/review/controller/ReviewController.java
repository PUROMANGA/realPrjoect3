package com.example.minzok.review.controller;

import com.example.minzok.review.dto.request.ReviewSaveRequestDto;
import com.example.minzok.review.dto.request.ReviewUpdateRequestDto;
import com.example.minzok.review.dto.response.ReviewResponseDto;
import com.example.minzok.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Validated
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> saveReview(@RequestBody ReviewSaveRequestDto request) {

        ReviewResponseDto reviewResponseDto = reviewService.saveReview(request.getContents(), request.getRating());

        return new ResponseEntity<>(reviewResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> findAll() {

        List<ReviewResponseDto> reviewResponseDto = reviewService.findAll();

        return new ResponseEntity<>(reviewResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long id, @RequestBody ReviewUpdateRequestDto request) {

        ReviewResponseDto reviewResponseDto = reviewService.updateReview(id, request.getContents(), request.getRating());

        return new ResponseEntity<>(reviewResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {

        reviewService.deleteReview(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
