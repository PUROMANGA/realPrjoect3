package com.example.minzok.review.dto.response;

import com.example.minzok.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewResponseDto {

    private Long id;
    private String contents;
    private int rating;
    private LocalDateTime createTime;
    private LocalDateTime modifiedTime;

    public static ReviewResponseDto toDto(Review review) {
        return new ReviewResponseDto(review.getId(), review.getContents(), review.getRating(), review.getCreatTime(), review.getModifiedTime());
    }

}
