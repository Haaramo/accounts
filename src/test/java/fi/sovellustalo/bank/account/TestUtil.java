package fi.sovellustalo.bank.account;

import java.util.Currency;

class TestUtil {
    static final String POSTGRES_IMAGE = "postgres:16.1-alpine";
    static final Currency EUR = Currency.getInstance("EUR");

    static String restBaseUrl(int port) {
        return "http://localhost:" + port;
    }

    static String accountsRestUrl(int port) {
        return TestUtil.restBaseUrl(port) + "/accounts";
    }
}
