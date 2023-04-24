package com.example.reservation.domain.db.dto;

import com.example.reservation.domain.db.model.Store;
import com.example.reservation.domain.db.model.User;
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
public class StoreDto {

    private String number;

    private String name;
    private Float lat;
    private Float lnt;
    private Float score;
    private int reviewCount;

    private UserDto userDto;

    private Float distance;

    public static StoreDto form(Store store){
        return StoreDto.builder()
            .number(store.getNumber())
            .name(store.getName())
            .lat(store.getLat())
            .lnt(store.getLnt())
            .score(store.getScore())
            .reviewCount(store.getReviewCount())
            .userDto(UserDto.form(store.getUser()))
            .distance(store.getDistance())
            .build();
    }
}
