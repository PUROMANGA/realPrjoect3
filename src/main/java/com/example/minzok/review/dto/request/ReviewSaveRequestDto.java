package com.example.minzok.review.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewSaveRequestDto {

    @NotNull(message = "평점은 필수 입니다.")
    @Min(value = 1, message = "최소 평점은 1 입니다.")
    @Max(value = 5, message = "최대 평점은 5 입니다.")
    private int rating;

    @NotBlank(message = "리뷰 내용을 입력 해 주세요.")
    private String contents;
    private Long orderId;
    private Long storeId;

}
