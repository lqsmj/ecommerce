package com.java.teste.ecommerce.config;

import com.java.teste.ecommerce.dto.ProdutoDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProdutoConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.group-id}")
    private String groupId;

    /**
     * @return
     */
    @Bean
    public ConsumerFactory<String, ProdutoDto> produtoConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(ProdutoDto.class, false));
    }

    /**
     * @return
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProdutoDto> produtoKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProdutoDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(produtoConsumerFactory());
        return factory;
    }

}
