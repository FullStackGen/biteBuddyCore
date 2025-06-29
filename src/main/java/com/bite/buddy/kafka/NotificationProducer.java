package com.bite.buddy.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class NotificationProducer {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public void sendNotification(String topic, NotificationEvent message) {
        CompletableFuture<SendResult<String, NotificationEvent>> future = kafkaTemplate.send(topic, message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Headers: " + result.getProducerRecord().headers());
                System.out.println("✅ Notification event sent successfully to topic: " + topic);
            } else {
                System.err.println("❌ Failed to send notification event to topic: " + topic);
                ex.printStackTrace();
            }
        });

        // Main thread returns immediately
    }
}
