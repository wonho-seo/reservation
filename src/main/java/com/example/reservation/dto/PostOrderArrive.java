package com.example.reservation.dto;

import com.example.reservation.domain.db.dto.OrderDto;
import com.example.reservation.dto.PostOrderAdmission.Response;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PostOrderArrive {
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
        private String customerName;

        public static Response form(OrderDto orderDto){
            return Response.builder()
                .orderTime(orderDto.getOrderAt())
                .customerName(orderDto.getUserDto().getName())
                .build();
        }
    }
}
