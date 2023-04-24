package com.example.reservation.domain.db.repository;

import com.example.reservation.domain.db.model.Order;
import com.example.reservation.domain.db.model.Store;
import com.example.reservation.domain.db.model.User;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByNumber(String number);
    boolean existsByOrderAtBetweenAndStoreAndUser(LocalDateTime start, LocalDateTime end, Store store, User user);
    boolean existsByNumber(String number);
}
