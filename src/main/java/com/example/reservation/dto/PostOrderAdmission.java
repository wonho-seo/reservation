package com.example.reservation.dto;

import com.example.reservation.domain.db.dto.OrderDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PostOrderAdmission {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder

    public static class Request {

        private String storeNumber;
        private String orderNumber;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private LocalDateTime orderTime;
        private String orderNumber;
        private String storeName;
        private String customerName;
        private String phone;

        public static Response form(OrderDto orderDto){
            return Response.builder()
                .orderTime(orderDto.getOrderAt())
                .orderNumber(orderDto.getNumber())
                .storeName(orderDto.getStoreDto().getName())
                .customerName(orderDto.getUserDto().getName())
                .phone(orderDto.getPhone())
                .build();
        }
    }
}
