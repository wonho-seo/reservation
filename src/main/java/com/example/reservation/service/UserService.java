package com.example.reservation.service;


import com.example.reservation.domain.db.model.User;
import com.example.reservation.domain.db.model.UserRole;
import com.example.reservation.domain.db.repository.UserRepository;
import com.example.reservation.exception.CustomException;
import com.example.reservation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("not found user"));
    }

    @Transactional
    public void signUp(String userId, int role, String password, String nickname) {
        if (userRepository.existsByUsername(userId)) {
            throw new CustomException(ErrorCode.ALREADY_IN_USE_ID);
        }

        User user = User.builder()
            .username(userId)
            .password(passwordEncoder.encode(password))
            .nickname(nickname)
            .build();

        user.setRoles(UserRole.CUSTOMER);

        if (role == 1) {
            user.setRoles(UserRole.SHOPKEEPER);
        }

        userRepository.save(user);
    }

    public User authenticate(String userId, String password) {
        User user = userRepository.findByUsername(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID));

        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }

        return user;
    }
}
