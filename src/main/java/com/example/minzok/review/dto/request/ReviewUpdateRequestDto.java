package com.example.minzok.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateRequestDto {

    @NotBlank(message = "리뷰 내용을 작성 해 주세요")
    private String contents;

    @Min(value = 1, message = "최소 평점은 1 입니다.")
    @Max(value = 2, message = "최대 평점은 5 입니다.")
    private int rating;

}
