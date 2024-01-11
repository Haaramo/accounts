package fi.sovellustalo.accounts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;
import java.util.Set;

@RestController
class AccountsResource {

    @GetMapping("/accounts")
    Set<Account> getProducts() {
        var account = new Account(1, 2, 21670, Currency.getInstance("EUR"));
        return Set.of(account);
    }

}
