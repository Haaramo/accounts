package fi.sovellustalo.bank.account;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatusCode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class BankAccountRestTests {
    private static final int ACCOUNTS_TO_CREATE_IN_LOAD_TEST = 1000;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> P = new PostgreSQLContainer<>(TestUtil.POSTGRES_IMAGE);

    private final Logger log = LoggerFactory.getLogger(BankAccountRestTests.class);


    @Test
    void create() {
        var id = ACCOUNTS_TO_CREATE_IN_LOAD_TEST + 1;
        var account = new BankAccount(id, 2, 21670, TestUtil.EUR);
        restTemplate.postForObject(TestUtil.accountsRestUrl(port), account, URI.class);

        var response = restTemplate.getForObject(TestUtil.accountsRestUrl(port) + "/" + id, BankAccount.class);
        assertEquals(account, response);
    }

    @Test
    void createWithExistingId() {
        var id = ACCOUNTS_TO_CREATE_IN_LOAD_TEST + 2;
        var account = new BankAccount(id, 2, 21670, TestUtil.EUR);
        restTemplate.postForObject(TestUtil.accountsRestUrl(port), account, URI.class);

        var failedResponse = restTemplate.postForEntity(TestUtil.accountsRestUrl(port), account, URI.class);
        assertEquals(HttpStatusCode.valueOf(400), failedResponse.getStatusCode());
    }

    @Test
    void load() {
        warmUp();

        var startMillis = System.currentTimeMillis();
        generateLoad();
        var durationMillis = System.currentTimeMillis() - startMillis;
        log.atInfo().log("Created {} accounts in {} ms", ACCOUNTS_TO_CREATE_IN_LOAD_TEST, durationMillis);

        assertTrue(durationMillis < 2500);
    }

    private void generateLoad() {
        IntStream.rangeClosed(1, ACCOUNTS_TO_CREATE_IN_LOAD_TEST)
                .parallel()
                .forEach(i -> {
                    var account = new BankAccount(i, i + 1, i + 2000, TestUtil.EUR);
                    var response = restTemplate.postForEntity(TestUtil.accountsRestUrl(port), account, URI.class);
                    assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
                    assertEquals(URI.create("/accounts/" + i), response.getHeaders().getLocation());
                });
    }

    private void warmUp() {
        var account = new BankAccount(ACCOUNTS_TO_CREATE_IN_LOAD_TEST + 3, 2, 21670, TestUtil.EUR);
        restTemplate.postForObject(TestUtil.accountsRestUrl(port), account, BankAccount.class);
    }
}
