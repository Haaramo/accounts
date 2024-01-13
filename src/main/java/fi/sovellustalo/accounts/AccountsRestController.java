package fi.sovellustalo.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/accounts")
class AccountsRestController {
    private final AccountService accountService;

    @Autowired
    AccountsRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    void save(@RequestBody BankAccount bankAccount) {
        accountService.save(bankAccount);
    }

    @GetMapping("/{id}")
    Optional<BankAccount> findById(@PathVariable int id) {
        return accountService.findById(id);
    }

}
