package com.example.reservation.domain.db.dto;

import com.example.reservation.domain.db.model.Order;
import java.time.LocalDateTime;
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
public class OrderDto {

    private String number;
    private LocalDateTime orderAt;
    private Integer admission;
    private Integer arrive;
    private String phone;

    private StoreDto storeDto;
    private UserDto userDto;

    public static OrderDto form(Order order){
        return OrderDto.builder()
            .number(order.getNumber())
            .orderAt(order.getOrderAt())
            .admission(order.getAdmission())
            .arrive(order.getArrive())
            .phone(order.getPhone())
            .storeDto(StoreDto.form(order.getStore()))
            .userDto(UserDto.form(order.getUser()))
            .build();
    }
}
