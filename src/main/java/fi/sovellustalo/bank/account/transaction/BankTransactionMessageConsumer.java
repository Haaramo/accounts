package fi.sovellustalo.bank.account.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class BankTransactionMessageConsumer {
    private static final Logger log = LoggerFactory.getLogger(BankTransactionMessageConsumer.class);
    private static final ObjectReader jsonReader = new ObjectMapper().readerFor(BankTransferResult.class);

    private final List<BankTransferResult> receivedResults = Collections.synchronizedList(new ArrayList<>());

    @KafkaListener(topics = "${account.transactions.topic}")
    public void receive(ConsumerRecord<String, String> consumerRecord) {
        log.info("received payload='{}'", consumerRecord.toString());
        try {
            BankTransferResult bankTransferResult = jsonReader.readValue(consumerRecord.value());
            receivedResults.add(bankTransferResult);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasReceived(BankTransferResult bankTransferResult) {
        return receivedResults.contains(bankTransferResult);
    }
}
