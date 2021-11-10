package com.java.teste.ecommerce.consumer;

import com.java.teste.ecommerce.dto.ItemCarrinhoDto;
import com.java.teste.ecommerce.service.DeadLetterService;
import com.java.teste.ecommerce.service.ItemCarrinhoService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ItemCarrinhoConsumer {

    private static final Logger log = LoggerFactory.getLogger(ItemCarrinhoConsumer.class);

    @Value(value = "${spring.kafka.group-id}")
    private String groupId;

    @Value(value = "${topic.name.carrinho}")
    private String topic;

    @Autowired
    private ItemCarrinhoService itemCarrinhoService;

    @Autowired
    private DeadLetterService deadLetterService;


    /**
     * Método para receber dados do ItemCarrinho via Kafka
     * @param record
     */
    @KafkaListener(topics = "${topic.name.carrinho}", groupId = "${spring.kafka.group-id}", containerFactory = "itemCarrinhoKafkaListenerContainerFactory")
    public void listenTopicProduto(ConsumerRecord<String, ItemCarrinhoDto> record){
       try{
           log.info("Recived Message"+record.partition());
           log.info("Recived Message" + record.value());
           itemCarrinhoService.prepararItemCarrinho(record.value());
       } catch (Exception e) {
           log.error("Erro desconhecido ao tentar salvar", e);
           deadLetterService.enviarParaDLQ(record.value().toString(), topic, e.getMessage());

       }

    }

}
