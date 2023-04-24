package com.example.reservation.controller;

import com.example.reservation.domain.db.model.UserRole.ROLES;
import com.example.reservation.dto.PostReview;
import com.example.reservation.service.ReviewService;
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
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @Secured({ROLES.SHOPKEEPER, ROLES.CUSTOMER})
    public ResponseEntity<PostReview.Response> addReview(@RequestBody PostReview.Request request) {
        return ResponseEntity.ok(PostReview.Response.form(reviewService.addReview(
            SecurityContextHolder.getContext().getAuthentication().getName(),
            request.getScore(),
            request.getDetail(),
            request.getOrderNumber())));
    }
}
