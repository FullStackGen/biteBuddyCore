package com.bite.buddy.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, NotificationEvent> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // Do not set VALUE_SERIALIZER_CLASS_CONFIG in config map here

        // Explicitly create JsonSerializer and disable type headers
        JsonSerializer<NotificationEvent> serializer = new JsonSerializer<>();
        serializer.setAddTypeInfo(false);

        DefaultKafkaProducerFactory<String, NotificationEvent> factory = new DefaultKafkaProducerFactory<>(config);
        factory.setValueSerializer(serializer);

        return factory;
    }

    @Bean
    public KafkaTemplate<String, NotificationEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
