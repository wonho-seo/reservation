package com.example.reservation.domain.db.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum UserRole implements GrantedAuthority {
    CUSTOMER("ROLE_CUSTOMER"),
    SHOPKEEPER("ROLE_SHOPKEEPER");


    private final String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public static class ROLES{
        public static final String CUSTOMER = "ROLE_CUSTOMER";
        public static final String SHOPKEEPER = "ROLE_SHOPKEEPER";
    }
}
