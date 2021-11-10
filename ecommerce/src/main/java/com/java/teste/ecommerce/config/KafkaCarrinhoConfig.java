package com.java.teste.ecommerce.config;

import com.java.teste.ecommerce.dto.CarrinhoRequestDto;
import com.java.teste.ecommerce.dto.CarrinhoResponseDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaCarrinhoConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.group-id}")
    private String groupId;

    @Value(value = "${topic.name.carrinho}")
    private String topic;


    /**
     * @return
     */
    @Bean
    public ConsumerFactory<String, CarrinhoRequestDto> carrinhoConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(CarrinhoRequestDto.class, false));
    }


    /**
     * @return
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CarrinhoRequestDto> carrinhoKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CarrinhoRequestDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(carrinhoConsumerFactory());
        return factory;
    }


    @Bean
    public NewTopic createNewTopic() {
        return new NewTopic(topic, 3,(short) 1) ;
    }


    @Bean
    public ProducerFactory<String, CarrinhoResponseDto> CarrinhoProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, CarrinhoResponseDto> produtoKafkaTemplate() {
        return new KafkaTemplate<>(CarrinhoProducerFactory());
    }

}
