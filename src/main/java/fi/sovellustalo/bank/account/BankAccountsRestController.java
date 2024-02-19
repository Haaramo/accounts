package fi.sovellustalo.bank.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
class BankAccountsRestController {

    private final Logger log = LoggerFactory.getLogger(BankAccountsRestController.class);
    private final BankAccountService bankAccountService;

    BankAccountsRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    ResponseEntity<URI> create(@RequestBody BankAccount bankAccount) {
        try {
            bankAccountService.create(bankAccount);
        } catch (IllegalArgumentException e) {
            log.atWarn().setCause(e).log("Failed to create account {}", bankAccount);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (bankAccount.id == 1)
            log.info("Thread {}", Thread.currentThread());
        return ResponseEntity.created(URI.create("/accounts/" + bankAccount.id))
                .build();
    }

    @GetMapping("/{id}")
    Optional<BankAccount> findById(@PathVariable int id) {
        return bankAccountService.findById(id);
    }

}
