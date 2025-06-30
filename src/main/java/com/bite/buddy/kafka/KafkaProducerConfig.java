package com.bite.buddy.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaUrl;

    @Value("${spring.kafka.properties.sasl.jaas.config}")
    private String saslConfig;

    @Value("${spring.kafka.properties.sasl.mechanism}")
    private String saslMech;

    @Value("${spring.kafka.properties.security.protocol}")
    private String secProtocol;


    @Bean
    public ProducerFactory<String, NotificationEvent> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put("sasl.jaas.config", saslConfig);
        config.put("sasl.mechanism", saslMech);
        config.put("security.protocol", secProtocol);

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
