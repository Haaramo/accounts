package fi.sovellustalo.bank.account.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.sovellustalo.bank.account.BankAccount;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class BankTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne
    @JoinColumn(name="bank_account_id", nullable = false)
    @JsonIgnore
    public BankAccount bankAccount;

    public int amount;
    public Instant time;

    public BankTransaction() {
    }

    public BankTransaction(BankAccount bankAccount, int amount) {
        this.bankAccount = bankAccount;
        this.amount = amount;
    }
}
