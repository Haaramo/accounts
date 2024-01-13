package fi.sovellustalo.bank.account;

import fi.sovellustalo.bank.account.transaction.BankTransfer;
import fi.sovellustalo.bank.account.transaction.BankTransferResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class BankTransactionRestTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> P = new PostgreSQLContainer<>(TestUtil.POSTGRES_IMAGE);

    @Test
    void testTransfer() {
        var result = transfer();
        assertResult(result);
    }

    private BankTransferResult transfer() {
        var fromAccount = new BankAccount(1, 2, 20000, TestUtil.EUR);
        var toAccount = new BankAccount(2, 3, 10000, TestUtil.EUR);

        restTemplate.postForObject(TestUtil.accountsRestUrl(port), fromAccount, BankAccount.class);
        restTemplate.postForObject(TestUtil.accountsRestUrl(port), toAccount, BankAccount.class);

        var transfer = new BankTransfer(fromAccount.id, toAccount.id, 12);
        return restTemplate.postForObject(TestUtil.restBaseUrl(port) + "/transactions", transfer, BankTransferResult.class);
    }

    private void assertResult(BankTransferResult result) {
        assertEquals(19988, result.fromAccount().balance);
        assertEquals(10012, result.toAccount().balance);

        var fromAccount = restTemplate.getForObject(TestUtil.accountsRestUrl(port) + '/' + 1, BankAccount.class);
        var toAccount = restTemplate.getForObject(TestUtil.accountsRestUrl(port) + '/' + 2, BankAccount.class);

        assertEquals(19988, fromAccount.balance);
        assertEquals(10012, toAccount.balance);

        assertEquals(-12, fromAccount.transactions.getFirst().amount);
        assertEquals(12, toAccount.transactions.getFirst().amount);
    }
}