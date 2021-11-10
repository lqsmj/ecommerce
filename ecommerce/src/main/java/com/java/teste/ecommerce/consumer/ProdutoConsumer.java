package com.java.teste.ecommerce.consumer;

import com.java.teste.ecommerce.dto.ItemCarrinhoDto;
import com.java.teste.ecommerce.dto.ProdutoDto;
import com.java.teste.ecommerce.entity.Produto;
import com.java.teste.ecommerce.service.DeadLetterService;
import com.java.teste.ecommerce.service.ProdutoService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
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

    @Autowired
    private DeadLetterService deadLetterService;


    /**
     * Método para receber dados do produto via Kafka
     * @param record
     */
    @KafkaListener(topics = "${topic.name}", groupId = "${spring.kafka.group-id}", containerFactory = "produtoKafkaListenerContainerFactory")
    public void listenTopicProduto(ConsumerRecord<String, ProdutoDto> record){
        try{
            produtoService.prepararProduto(record.value());
            //log.info("Recived Message"+record.partition());
            //log.info("Recived Message" + record.value());
        } catch (Exception e) {
            log.error("Erro desconhecido ao tentar salvar", e);
            deadLetterService.enviarParaDLQ(record.value().toString(), topic, e.getMessage());

        }

    }

}