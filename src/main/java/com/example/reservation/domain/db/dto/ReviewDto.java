package com.example.reservation.domain.db.dto;

import com.example.reservation.domain.db.model.Order;
import com.example.reservation.domain.db.model.Review;
import com.example.reservation.domain.db.model.Store;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private int score;

    private String detail;
    private String number;

    private StoreDto storeDto;
    private OrderDto orderDto;

    public static ReviewDto form(Review review){
        return ReviewDto.builder()
            .number(review.getNumber())
            .score(review.getScore())
            .detail(review.getDetail())
            .storeDto(StoreDto.form(review.getStore()))
            .orderDto(OrderDto.form(review.getOrder()))
            .build();
    }
}
