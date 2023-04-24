package com.example.reservation.controller;

import com.example.reservation.domain.db.model.User;
import com.example.reservation.domain.security.TokenProvider;
import com.example.reservation.dto.PostSignIn;
import com.example.reservation.dto.PostSignUp;
import com.example.reservation.service.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final TokenProvider tokenProvider;

    /**
     * 회원가입
     *
     * @param request
     *         userId : 아이디
     *          길이 : 3 ~ 20
     *         password :비밀번호
     *          길이 : 8 ~ 20
     *          특이사항 : 대문자, 특수문자, 소문자 반드시 포함
     *         role : 사용자 권한
     *          특이사항 : 1일경우 사업자
     *                  나머지 손님
     *
     *         name : 이름
     *          길이 : 1 ~
     *         @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$")
     *         private String phone;
     *          형태 : 01x-xxx-xxxx, 01x-xxxx-xxxx
     * @return
     *      성공시 "success" 메세지 표기
     *      실패시 error message 표기
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody PostSignUp.Request request){
        userService.signUp(request.getUserId(),
            request.getRole(),
            request.getPassword(),
            request.getNickname()
            );

        return ResponseEntity.ok("success");
    }

    /**
     * 로그인
     *
     * @param request
     *         userId : 아이디
     *          길이 : 3 ~ 20
     *         password :비밀번호
     *          길이 : 8 ~ 20
     *          특이사항 : 대문자, 특수문자, 소문자 반드시 포함
     * @return
     *  jwt토큰
     */

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@Valid @RequestBody PostSignIn.Request request){
        User user =userService.authenticate(request.getUserId(), request.getPassword());

        List<String> role = new ArrayList<>();
        role.add(user.getRoles().name());
        return ResponseEntity.ok(this.tokenProvider.generateToken(user.getUsername(), role));
    }
}
