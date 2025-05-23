package com.sixmycat.catchy.feature.notification.query.controller;

import com.sixmycat.catchy.common.dto.ApiResponse;
import com.sixmycat.catchy.common.dto.PageResponse;
import com.sixmycat.catchy.feature.notification.query.dto.NotificationDTO;
import com.sixmycat.catchy.feature.notification.query.service.NotificationQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
@Tag(name = "알림 API", description = "알림 조회")
public class NotificationQueryController {
    private final NotificationQueryService notificationQueryService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<PageResponse<NotificationDTO>>> getNotificationList(
            @AuthenticationPrincipal String memberId,
            @RequestParam(defaultValue = "1") int page, // 기본값 0
            @RequestParam(defaultValue = "10") int size // 기본값 10
    ) {
        PageResponse<NotificationDTO> notifications = notificationQueryService.getNotifications(Long.parseLong(memberId), page, size);
        return ResponseEntity.ok(ApiResponse.success(notifications));
    }
}