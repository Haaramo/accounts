package fi.sovellustalo.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class BankAccountsTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
			"postgres:16.1-alpine"
	);

	@Test
	void save() {
		var account = new BankAccount(1, 2, 21670, Currency.getInstance("EUR"));
		restTemplate.postForObject("http://localhost:" + port + "/accounts", account, BankAccount.class);

		var response = restTemplate.getForObject("http://localhost:" + port + "/accounts/" + 1, BankAccount.class);
		assertEquals(account, response);
	}
}
