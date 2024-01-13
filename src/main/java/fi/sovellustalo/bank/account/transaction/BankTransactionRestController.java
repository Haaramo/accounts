package fi.sovellustalo.bank.account.transaction;

import fi.sovellustalo.bank.account.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
class BankTransactionRestController {
    private final BankAccountService bankAccountService;

    @Autowired
    BankTransactionRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    BankTransferResult transfer(@RequestBody BankTransfer bankTransfer) {
        var result = bankAccountService.transfer(bankTransfer);
        return result.truncateTransactions(50);
    }
}
