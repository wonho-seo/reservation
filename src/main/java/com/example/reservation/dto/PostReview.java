package com.example.reservation.dto;


import com.example.reservation.domain.db.dto.ReviewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PostReview {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        private int score;
        private String detail;
        private String orderNumber;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String storeName;

        public static Response form(ReviewDto reviewDto) {
            return Response.builder()
                .storeName(reviewDto.getStoreDto().getName())
                .build();
        }
    }
}
