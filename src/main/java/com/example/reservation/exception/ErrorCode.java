package com.example.reservation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_IN_USE_ID(409, "아이디가 이미 사용중입니다."),
    NOT_FOUND_ID(404, "아이디가 존재하지 않습니다."),
    NOT_MATCH_PASSWORD(404, "패스워드가 틀립니다."),
    ALREADY_REGISTERED_STORE(409, "등록된 매장입니다."),
    NOT_SHOPKEEPER(400, "점장 계정이 아닙니다."),
    NOT_FOUND_STORE(404, "상점이 존재하지 않습니다"),
    TIME_IS_TOO_CLOSE(404, "같은 가게에서 1시간이내 예약이 있습니다."),
    NOT_YOUR_STORE(404, "사용자의 가게가 아닙니다."),
    NOT_FOUND_ORDER_NUMBER(404, "예약을 찾을수 없습니다."),
    NOT_YOUR_CUSTOMER(404, "예약을 원하는 가게가 아닙니다."),
    LESS_THAN_10_MINUTES(400, "10분전에 예약할수 없습니다."),
    NOT_ADMISSION(404, "승인되지 않은 예약입니다."),
    TOO_LATE(404, "도착이 너무 늦었습니다."),
    NOT_YOUR_ORDER(404, "사용자의 예약이 아닙니다."),
    NO_USE_NO_REVIEW(400, "이용하지 않은 가게는 리뷰를 작성할수 없습니다."),
    ONLY_ONE_REVIEW(404, "리뷰는 하나만 가능합니다.");

    private final int code;
    private final String message;
}
