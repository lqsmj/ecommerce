package com.java.teste.ecommerce.service;

import com.java.teste.ecommerce.consumer.ProdutoConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;

@Service
public class DeadLetterService {

    private static final Logger log = LoggerFactory.getLogger(ProdutoConsumer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;


    @Value("${topic.deadletter}")
    private String topicoDeadletter;

    public DeadLetterService(KafkaTemplate<String,String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    /**
     * Metodo para enviar informações para a DeadLetter Queue
     *
     * TODO Necessario criar o tratamento dos registros enviados para o DLQ
     * @param record
     * @param topicoOriginal
     * @param msgErro
     * @return
     */
    public boolean enviarParaDLQ(String record, String topicoOriginal, String msgErro) {
        var recordDeadLetter = new ProducerRecord<String,  String>(topicoDeadletter, record);
        recordDeadLetter.headers().add(KafkaHeaders.DLT_ORIGINAL_TOPIC, topicoOriginal.getBytes());
        recordDeadLetter.headers().add(KafkaHeaders.DLT_EXCEPTION_MESSAGE, msgErro.getBytes());
        try {
            log.warn("Enviando {} para DLQ no tópico {}", record, topicoDeadletter);
            kafkaTemplate.send(recordDeadLetter).get();
            return true;
        } catch (Exception e) {
            log.error("Ocorreram erros ao tentar enviar para o DLQ", e);
            return false;
        }
    }
}
