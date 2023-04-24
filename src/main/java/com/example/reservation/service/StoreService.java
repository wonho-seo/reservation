package com.example.reservation.service;


import com.example.reservation.domain.db.dto.StoreDto;
import com.example.reservation.domain.db.model.Store;
import com.example.reservation.domain.db.model.User;
import com.example.reservation.domain.db.repository.StoreRepository;
import com.example.reservation.domain.db.repository.UserRepository;
import com.example.reservation.exception.CustomException;
import com.example.reservation.exception.ErrorCode;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    /**
     * 1. 유저 아이디를 통해 유저 정보를 얻는다 2. 점장 계정이 아닐경우 에러표시 3. 점장이 가진 가계중 같은 이름이 있을경우 중복으로 간주하여 에러표시 4. 가게
     * 이름, 위도, 경도, 유저정보를 저장
     *
     * @param name   가게 이름
     * @param lat    위도
     * @param lnt    경도
     * @param userId 유저 아이디
     */
    @Transactional
    public StoreDto addStore(String name, Float lat, Float lnt, String userId) {
        User user = userRepository.findByUsername(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID));

        List<Store> list = storeRepository.findAllByUser(user);
        for (Store store : list) {
            if (store.getName().equals(name)) {
                throw new CustomException(ErrorCode.ALREADY_REGISTERED_STORE);
            }
        }

        String uuid = UUID.randomUUID().toString();
        Store store = Store.builder()
            .name(name)
            .lat(lat)
            .lnt(lnt)
            .score(0F)
            .user(user)
            .build();

        for (int i = 0; i < 10; i++) {
            if (!storeRepository.existsByNumber(uuid)) {
                store.setNumber(uuid);
                break;
            }
        }

        storeRepository.save(store);
        return StoreDto.form(store);
    }

    public List<Store> getStore(Pageable pageable, float lat, float lnt) {
        return storeRepository.findAllByDistances(lat, lnt, pageable).getContent();
    }
    public List<Store> getMyStore(Pageable pageable, String keeperName) {
        User user = userRepository.findByUsername(keeperName)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID));
        return storeRepository.findAllByUser(user);
    }

}
