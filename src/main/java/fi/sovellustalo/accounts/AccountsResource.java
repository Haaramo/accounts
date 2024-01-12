package fi.sovellustalo.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;
import java.util.Set;

@RestController
@RequestMapping("/persons")
class AccountsResource {
    private final AccountService accountService;

    @Autowired
    AccountsResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    Account save(Account account) {
        return accountService.save(account);
    }

    @GetMapping
    Set<Account> getAccounts() {
        var account = new Account(1, 2, 21670, Currency.getInstance("EUR"));
        return Set.of(account);
    }

}
