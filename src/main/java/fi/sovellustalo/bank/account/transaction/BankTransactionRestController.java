package fi.sovellustalo.bank.account.transaction;

import fi.sovellustalo.bank.account.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
class BankTransactionRestController {
    private final BankAccountService bankAccountService;
    private final BankTransactionMessageProducer bankTransactionMessageProducer;

    @Autowired
    BankTransactionRestController(BankAccountService bankAccountService, BankTransactionMessageProducer bankTransactionMessageProducer) {
        this.bankAccountService = bankAccountService;
        this.bankTransactionMessageProducer = bankTransactionMessageProducer;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    BankTransferResult transfer(@RequestBody BankTransfer bankTransfer) {
        var result = bankAccountService.transfer(bankTransfer)
                .truncateTransactions(50);
        bankTransactionMessageProducer.send(result.truncateTransactions(1));
        return result;
    }
}
