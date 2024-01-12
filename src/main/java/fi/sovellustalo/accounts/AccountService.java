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

    Account save(Account account) {
        return accountRepository.save(account);
    }

    Optional<Account> findById(int id) {
        return accountRepository.findById(id);
    }
}
