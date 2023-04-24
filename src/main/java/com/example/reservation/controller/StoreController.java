package com.example.reservation.controller;

import com.example.reservation.domain.db.model.UserRole.ROLES;
import com.example.reservation.dto.GetStoreList;
import com.example.reservation.dto.GetStoreMyShop;
import com.example.reservation.dto.PostRegister;
import com.example.reservation.dto.PostRegister.Response;
import com.example.reservation.service.StoreService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @Secured(ROLES.SHOPKEEPER)
    @PostMapping("/register")
    public ResponseEntity<PostRegister.Response> addStore(
        @Valid @RequestBody PostRegister.Request request) {
        return ResponseEntity.ok(Response.form(
            storeService.addStore(request.getName(), request.getLat(), request.getLnt(),
                SecurityContextHolder.getContext().getAuthentication().getName())));
    }

    /**
     * @param pageable : ?page= & size= & sort= page : 찾고자 하는 페이지 size : 페이지당 최대 갯수 sort : 이름순서 :
     *                 name 별점 낮은 순서 : score 별점 높은 순서 : score, DESC
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<GetStoreList.Response> getStore(Pageable pageable,
        @RequestParam(value = "lat") float lat,
        @RequestParam(value = "lnt") float lnt
    ) {
        return ResponseEntity.ok(GetStoreList.Response.form(storeService.getStore(pageable, lat, lnt)));
    }

    @GetMapping("/my-shop/list")
    @Secured(ROLES.SHOPKEEPER)
    public ResponseEntity<GetStoreMyShop.Response> getStore(Pageable pageable){
        return ResponseEntity.ok(GetStoreMyShop.Response.form(storeService.getMyStore(pageable,
            SecurityContextHolder.getContext().getAuthentication().getName())));
    }

}
