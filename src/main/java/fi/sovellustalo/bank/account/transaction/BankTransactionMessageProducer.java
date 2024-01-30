package fi.sovellustalo.bank.account.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;

@Component
class BankTransactionMessageProducer {
    private static final Logger log = LoggerFactory.getLogger(BankTransactionMessageProducer.class);
    private static final ObjectWriter jsonWriter = new ObjectMapper().writerFor(BankTransferResult.class);

    @Value("${account.transactions.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public BankTransactionMessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(BankTransferResult payload) {
        try {
            var json = jsonWriter.writeValueAsString(payload);
            log.info("sending to topic {} payload: {}", "account-transactions", json);
            kafkaTemplate.send(topic,  json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
