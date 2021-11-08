package com.java.teste.ecommerce.consumer;

import com.java.teste.ecommerce.dto.ProdutoDto;
import com.java.teste.ecommerce.entity.Produto;
import com.java.teste.ecommerce.service.ProdutoService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProdutoConsumer {

    private static final Logger log = LoggerFactory.getLogger(ProdutoConsumer.class);

    @Value(value = "${spring.kafka.group-id}")
    private String groupId;

    @Value(value = "${topic.name}")
    private String topic;

    @Autowired
    private ProdutoService produtoService;

    /**
     * Método para receber dados do produto via Kafka
     * @param record
     */
    @KafkaListener(topics = "${topic.name}", groupId = "${spring.kafka.group-id}", containerFactory = "produtoKafkaListenerContainerFactory")
    public void listenTopicProduto(ConsumerRecord<String, ProdutoDto> record){

       produtoService.prepararProduto(record.value());
        log.info("Recived Message"+record.partition());
        log.info("Recived Message" + record.value());
    }

}