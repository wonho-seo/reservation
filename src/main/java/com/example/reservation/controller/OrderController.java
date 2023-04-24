package com.example.reservation.controller;

import com.example.reservation.domain.db.model.UserRole.ROLES;
import com.example.reservation.dto.PostOrderAdmission;
import com.example.reservation.dto.PostOrderArrive;
import com.example.reservation.dto.PostOrderReservation;
import com.example.reservation.dto.PostOrderReservation.Response;
import com.example.reservation.service.OrderService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    //1.내 가게 리스트 뽑는다 and 2.내 가게 들어온 오더 리스트
    private final OrderService orderService;

    @PostMapping("/reservation")
    @Secured({ROLES.SHOPKEEPER, ROLES.CUSTOMER})
    public ResponseEntity<PostOrderReservation.Response> postReservation(
        @RequestBody @Valid PostOrderReservation.Request request) {
        return ResponseEntity.ok(Response.form(orderService.reserveOrder(request.getStoreName(),
            request.getStoreKeeper(),
            request.getOrderTime(),
            request.getPhone(),
            SecurityContextHolder.getContext().getAuthentication().getName())));
    }

    @PostMapping("/admission")
    @Secured(ROLES.SHOPKEEPER)
    public ResponseEntity<PostOrderAdmission.Response> postAdmission(
        @RequestBody PostOrderAdmission.Request request) {
        return ResponseEntity.ok(PostOrderAdmission.Response.form(orderService.setAdmission(
            SecurityContextHolder.getContext().getAuthentication().getName(),
            request.getStoreNumber(),
            request.getOrderNumber()
        )));
    }


    @PostMapping("/arrive")
    @Secured(ROLES.SHOPKEEPER)
    public ResponseEntity<PostOrderArrive.Response> postArrive(
        @RequestBody PostOrderArrive.Request request
    ){
        return ResponseEntity.ok(PostOrderArrive.Response.form(orderService.setArrive(
            SecurityContextHolder.getContext().getAuthentication().getName(),
            request.getStoreNumber(),
            request.getOrderNumber()
        )));
    }
}
