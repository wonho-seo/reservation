package com.example.reservation.service;

import com.example.reservation.domain.db.dto.OrderDto;
import com.example.reservation.domain.db.model.Order;
import com.example.reservation.domain.db.model.Store;
import com.example.reservation.domain.db.model.User;
import com.example.reservation.domain.db.repository.OrderRepository;
import com.example.reservation.domain.db.repository.StoreRepository;
import com.example.reservation.domain.db.repository.UserRepository;
import com.example.reservation.exception.CustomException;
import com.example.reservation.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    /**
     * 예약 1. 가게 점주 이름, 가게 이름을 통해 예약할 가게를 특정한다 2. 예약자의 정보를 얻어낸다 3. 현재 사용자가 예약할 가게에 1시간 이내에 예약한 경우
     * 에러(중복 예약 방지) 4. 위 과정에 문제가 없을시 성공적으로 예약한다
     *
     * @param storeName   가게이름
     * @param storeKeeper 가게 점주 이름
     * @param orderTime   예약 시간
     * @param phone       핸드폰 번호
     * @param userName    예약자 이름(SecurityContextHolder)
     */
    @Transactional
    public OrderDto reserveOrder(String storeName,
        String storeKeeper,
        LocalDateTime orderTime,
        String phone,
        String userName) {
        if (orderTime.isBefore(LocalDateTime.now().plusMinutes(10))){
            throw new CustomException(ErrorCode.LESS_THAN_10_MINUTES);
        }

        User keeper = userRepository.findByUsername(storeKeeper)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID));

        Store store = storeRepository.findByNameAndUser(storeName, keeper)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        User customer = userRepository.findByUsername(userName)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID));

        if (orderRepository.existsByOrderAtBetweenAndStoreAndUser(orderTime.minusMinutes(1),
            orderTime.plusMinutes(1), store, customer)) {
            throw new CustomException(ErrorCode.TIME_IS_TOO_CLOSE);
        }
        String uuid = UUID.randomUUID().toString();
        Order order = Order.builder()
            .number(uuid)
            .admission(0)
            .arrive(0)
            .phone(phone)
            .store(store)
            .user(customer)
            .orderAt(orderTime)
            .build();

        for (int i = 0; i < 10; i++){
            if(!orderRepository.existsByNumber(uuid)){
                order.setNumber(uuid);
                break;
            }
        }
        orderRepository.save(order);

        return OrderDto.form(order);
    }

    @Transactional
    public OrderDto setAdmission(String keeperName,String storeNumber, String orderNumber) {
        Store store = storeRepository.findByNumber(storeNumber)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        if (!store.getUser().getUsername().equals(keeperName)){
            throw new CustomException(ErrorCode.NOT_YOUR_STORE);
        }

        Order order = orderRepository.findByNumber(orderNumber)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER_NUMBER));

        if (!order.getStore().getNumber().equals(storeNumber)){
            throw new CustomException(ErrorCode.NOT_YOUR_CUSTOMER);
        }

        order.setAdmission(1);

        return OrderDto.form(order);
    }

    @Transactional
    public OrderDto setArrive(String keeperName,String storeNumber, String orderNumber){
        Store store = storeRepository.findByNumber(storeNumber)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));

        if (!store.getUser().getUsername().equals(keeperName)){
            throw new CustomException(ErrorCode.NOT_YOUR_STORE);
        }

        Order order = orderRepository.findByNumber(orderNumber)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER_NUMBER));

        if (!order.getStore().getNumber().equals(storeNumber)){
            throw new CustomException(ErrorCode.NOT_YOUR_CUSTOMER);
        }

        if (order.getAdmission() == 0){
            throw new CustomException(ErrorCode.NOT_ADMISSION);
        }
        if (order.getOrderAt().isBefore(LocalDateTime.now().plusMinutes(10))){
            throw new CustomException(ErrorCode.TOO_LATE);
        }
        order.setArrive(1);

        return OrderDto.form(order);
    }
}
