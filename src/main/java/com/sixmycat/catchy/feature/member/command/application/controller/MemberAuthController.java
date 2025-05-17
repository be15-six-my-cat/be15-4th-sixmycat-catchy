package com.sixmycat.catchy.feature.member.command.application.controller;

import com.sixmycat.catchy.common.dto.ApiResponse;
import com.sixmycat.catchy.feature.member.command.application.dto.response.TokenResponse;
import com.sixmycat.catchy.feature.member.command.application.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sixmycat.catchy.common.utils.CookieUtils.createRefreshTokenCookie;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@Tag(name = "", description = "")
@Slf4j
public class MemberAuthController {
    private final MemberService memberService;

    /* 테스트 로그인 */
    @Operation(summary = "테스트용 로그인", description = "테스트용 로그인 후 JWT를 발급합니다.")
    @PostMapping("/login/test")
    public ResponseEntity<ApiResponse<TokenResponse>> login(){
        TokenResponse token = memberService.testLogin();
        log.info(token.toString());
        return buildTokenResponse(token);
    }


    /* accessToken 과 refreshToken을 body와 쿠키에 담아 반환 */
    private ResponseEntity<ApiResponse<TokenResponse>> buildTokenResponse(TokenResponse tokenResponse) {
        ResponseCookie cookie = createRefreshTokenCookie(tokenResponse.getRefreshToken());  // refreshToken 쿠키 생성
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success(tokenResponse));
    }
}
