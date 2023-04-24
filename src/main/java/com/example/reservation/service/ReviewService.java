package com.example.reservation.service;

import com.example.reservation.domain.db.dto.ReviewDto;
import com.example.reservation.domain.db.model.Order;
import com.example.reservation.domain.db.model.Review;
import com.example.reservation.domain.db.model.Store;
import com.example.reservation.domain.db.repository.OrderRepository;
import com.example.reservation.domain.db.repository.ReviewRepository;
import com.example.reservation.exception.CustomException;
import com.example.reservation.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewDto addReview(String userName, int score, String detail, String orderNumber){
        Order order = orderRepository.findByNumber(orderNumber)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER_NUMBER));

        if (!order.getUser().getUsername().equals(userName)){
            throw new CustomException(ErrorCode.NOT_YOUR_ORDER);
        }

        if (order.getArrive() == 0 || order.getOrderAt().isBefore(LocalDateTime.now())){
            throw new CustomException(ErrorCode.NO_USE_NO_REVIEW);
        }


        Review review = Review.builder()
            .score(score)
            .detail(detail)
            .store(order.getStore())
            .order(order)
            .build();

        for (int i = 0; i < 10; i++){
            String uuid = UUID.randomUUID().toString();
            if (!reviewRepository.existsByNumber(uuid)){
                review.setNumber(uuid);
                break;
            }
        }
        Store store = order.getStore();
        float totalScore = store.getScore() * store.getReviewCount();
        int reviewCount = store.getReviewCount() + 1;
        totalScore /= reviewCount;

        store.setReviewCount(reviewCount);
        store.setScore(totalScore);

        reviewRepository.save(review);


        return ReviewDto.form(review);
    }
}
