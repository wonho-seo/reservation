package com.example.reservation.dto;

import com.example.reservation.domain.db.model.Store;
import com.example.reservation.dto.GetStoreList.Response;
import com.example.reservation.dto.GetStoreList.Response.Content;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GetStoreMyShop {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private List<Response.Content> contents;

        public static Response form(List<Store> stores) {
            return Response.builder()
                .contents(stores.stream().map(Response.Content::form).collect(
                    Collectors.toList()))
                .build();
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class Content {

            private String number;
            private String storeName;
            private float lat;
            private float lnt;
            private float score;
            private float reviewCount;

            public static Response.Content form(Store store) {
                return Response.Content.builder()
                    .number(store.getNumber())
                    .storeName(store.getName())
                    .lat(store.getLat())
                    .lnt(store.getLnt())
                    .score(store.getScore())
                    .reviewCount(store.getReviewCount())
                    .build();
            }
        }
    }
}
