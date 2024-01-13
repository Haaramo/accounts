package fi.sovellustalo.accounts;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Arrays;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("tc")
@ContextConfiguration(initializers = {AccountsTests.Initializer.class})
class AccountsTests {

	@ClassRule
	public static PostgreSQLContainer<AccountsPostgresqlContainer> postgreSQLContainer = AccountsPostgresqlContainer.instance();

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void save() {
	//	postgreSQLContainer.start();
		var account = new Account(1, 2, 21670, Currency.getInstance("EUR"));
		restTemplate.postForObject("http://localhost:" + port + "/accounts", account, Account.class);
	}

	@Test
	void rest() {
		var response = restTemplate.getForObject("http://localhost:" + port + "/accounts", Account[].class);
		var expected = new Account[]{new Account(1, 2, 21670, Currency.getInstance("EUR"))};

		assertEquals(Arrays.asList(expected), Arrays.asList(response));
	}

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			postgreSQLContainer.start();
			TestPropertyValues.of( //
					"spring.datasource.url="  + postgreSQLContainer.getJdbcUrl(),
					"spring.datasource.username=" + postgreSQLContainer.getUsername(),
					"spring.datasource.password=" + postgreSQLContainer.getPassword()
			).applyTo(configurableApplicationContext.getEnvironment());
		}

		// postgreSQLContainer.getJdbcUrl(),
	}

}
