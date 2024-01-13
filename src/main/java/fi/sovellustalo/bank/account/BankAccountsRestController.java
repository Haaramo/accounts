package fi.sovellustalo.bank.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/accounts")
class BankAccountsRestController {
    private final BankAccountService bankAccountService;

    @Autowired
    BankAccountsRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    void save(@RequestBody BankAccount bankAccount) {
        bankAccountService.save(bankAccount);
    }

    @GetMapping("/{id}")
    Optional<BankAccount> findById(@PathVariable int id) {
        return bankAccountService.findById(id);
    }

}
