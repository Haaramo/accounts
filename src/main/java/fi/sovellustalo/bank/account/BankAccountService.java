package fi.sovellustalo.bank.account;

import fi.sovellustalo.bank.account.transaction.BankTransfer;
import fi.sovellustalo.bank.account.transaction.BankTransferResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    void save(BankAccount account) {
        bankAccountRepository.save(account);
    }

    Optional<BankAccount> findById(int id) {
        return bankAccountRepository.findById(id);
    }

    @Transactional
    public BankTransferResult transfer(BankTransfer bankTransfer) {
        return bankAccountRepository.findById(bankTransfer.fromAccountId()) // TODO select for update needed
                .flatMap(fromAccount -> bankAccountRepository.findById(bankTransfer.toAccountId())
                        .map(toAccount -> transfer(bankTransfer.amount(), fromAccount, toAccount)))
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    private BankTransferResult transfer(int amount, BankAccount fromAccount, BankAccount toAccount) {
        ensureSameCurrency(fromAccount, toAccount);
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);
        return new BankTransferResult(fromAccount, toAccount);
    }

    private void ensureSameCurrency(BankAccount fromAccount, BankAccount toAccount) {
        if (!fromAccount.currencyEquals(toAccount)) {
            throw new IllegalArgumentException("Accounts must have same currency");
        }
    }
}
