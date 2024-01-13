package fi.sovellustalo.bank.account;

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
class BankAccountRestTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Container
	@ServiceConnection
	private static final PostgreSQLContainer<?> P = new PostgreSQLContainer<>(TestUtil.POSTGRES_IMAGE);

	@Test
	void create() {
		var account = new BankAccount(1, 2, 21670, TestUtil.EUR);
		restTemplate.postForObject(TestUtil.accountsRestUrl(port), account, BankAccount.class);

		var response = restTemplate.getForObject(TestUtil.accountsRestUrl(port) + "/" + 1, BankAccount.class);
		assertEquals(account, response);
	}
}
