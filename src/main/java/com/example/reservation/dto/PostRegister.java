package com.example.reservation.dto;

import com.example.reservation.domain.db.dto.StoreDto;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PostRegister {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder

    public static class Request {

        @Size(min = 1)
        private String name;
        private Float lat;
        private Float lnt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String number;
        private String name;
        private Float lat;
        private Float lnt;

        public static Response form(StoreDto storeDto) {
            return Response.builder()
                .number(storeDto.getNumber())
                .name(storeDto.getName())
                .lat(storeDto.getLat())
                .lnt(storeDto.getLnt())
                .build();
        }
    }
}
