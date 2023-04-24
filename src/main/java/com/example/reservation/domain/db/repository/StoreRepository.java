package com.example.reservation.domain.db.repository;

import com.example.reservation.domain.db.model.Store;
import com.example.reservation.domain.db.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    boolean existsByNumber(String number);

    List<Store> findAllByUser(User user);

    Optional<Store> findByNameAndUser(String name, User user);

    Optional<Store> findByNumber(String number);

    @Query(value = "SELECT * ,"
        + "(6371*acos(cos(radians(:lat))*cos(radians(lat))*cos(radians(lnt)"
        + "-radians(:lnt))+sin(radians(:lat))*sin(radians(lat))))"
        + "AS distance\n"
        + "FROM store\n"
        , nativeQuery = true)
    Page<Store> findAllByDistances(@Param(value = "lat") float lat,
        @Param(value = "lnt") float lnt,
        Pageable pageable);


}
