package fi.sovellustalo.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    void save(BankAccount account) {
        accountRepository.save(account);
    }

    Optional<BankAccount> findById(int id) {
        return accountRepository.findById(id);
    }
}
