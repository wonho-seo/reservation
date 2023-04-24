package com.example.reservation.dto;

import com.example.reservation.domain.db.dto.OrderDto;
import java.time.LocalDateTime;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PostOrderReservation {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder

    public static class Request{
        private String storeName;
        private String storeKeeper;
        private LocalDateTime orderTime;

        @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$")
        private String phone;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private String number;
        private LocalDateTime orderTime;
        private String storeName;
        private String phone;
        private Float lat;
        private Float lnt;

        public static Response form(OrderDto orderDto){
            return Response.builder()
                .number(orderDto.getNumber())
                .orderTime(orderDto.getOrderAt())
                .storeName(orderDto.getStoreDto().getName())
                .phone(orderDto.getPhone())
                .lat(orderDto.getStoreDto().getLat())
                .lnt(orderDto.getStoreDto().getLnt())
                .build();
        }
    }
}
