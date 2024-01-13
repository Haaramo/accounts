package fi.sovellustalo.bank.account;

import org.springframework.data.jpa.repository.JpaRepository;

interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
}
