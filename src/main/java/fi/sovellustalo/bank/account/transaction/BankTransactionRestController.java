package fi.sovellustalo.bank.account.transaction;

import fi.sovellustalo.bank.account.BankAccountService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
class BankTransactionRestController {
    private final BankAccountService bankAccountService;
    private final BankTransactionMessageProducer bankTransactionMessageProducer;

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
