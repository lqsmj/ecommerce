package com.java.teste.ecommerce.consumer;

import com.java.teste.ecommerce.dto.CarrinhoRequestDto;
import com.java.teste.ecommerce.service.CarrinhoService;
import com.java.teste.ecommerce.service.DeadLetterService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CarrinhoConsumer {

    private static final Logger log = LoggerFactory.getLogger(CarrinhoConsumer.class);

    @Value(value = "${spring.kafka.group-id}")
    private String groupId;

    @Value(value = "${topic.name.carrinho}")
    private String topic;

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private DeadLetterService deadLetterService;


    /**
     * Método para receber dados do Carrinho via Kafka
     * @param record
     */
    @KafkaListener(topics = "${topic.name.carrinho}", groupId = "${spring.kafka.group-id}", containerFactory = "carrinhoKafkaListenerContainerFactory")
    public void listenTopicCarrinho(ConsumerRecord<String, CarrinhoRequestDto> record){
       try{
           log.info("Recived Message"+record.partition());
           log.info("Recived Message" + record.value());
           carrinhoService.prepararCarrinho(record.value());
       } catch (Exception e) {
           log.error("Erro desconhecido ao tentar salvar", e);
           deadLetterService.enviarParaDLQ(record.value().toString(), topic, e.getMessage());

       }

    }

}
