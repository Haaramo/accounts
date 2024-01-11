package fi.sovellustalo.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Arrays;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountsTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void rest() {
		var response = restTemplate.getForObject("http://localhost:" + port + "/accounts", Account[].class);
		var expected = new Account[]{new Account(1, 2, 21670, Currency.getInstance("EUR"))};

		assertEquals(Arrays.asList(expected), Arrays.asList(response));
	}

}
