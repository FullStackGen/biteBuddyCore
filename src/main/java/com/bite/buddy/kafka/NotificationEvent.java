package com.bite.buddy.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    private String eventType;  // e.g., USER_REGISTERED, ORDER_PLACED
    private String name;
    private String email;
    private String mobileNumber;
    private String message;
    private Map<String, Object> metadata;
}
