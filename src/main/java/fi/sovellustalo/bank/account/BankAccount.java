package fi.sovellustalo.bank.account;

import fi.sovellustalo.bank.account.transaction.BankTransaction;
import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

@Entity
public class BankAccount {
    @Id
    public int id;
    public int ownerId;
    public int balance;
    public Currency currency;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    @OrderBy("time DESC")
    @BatchSize(size = 50)
    public List<BankTransaction> transactions = new ArrayList<>();

    public BankAccount(int id, int ownerId, int balance, Currency currency) {
        this.id = id;
        this.ownerId = ownerId;
        this.balance = balance;
        this.currency = currency;
    }

    public BankAccount() {
    }

    public void withdraw(int amount) {
        transactions.add(new BankTransaction(this, -amount));
        balance -= amount;
    }

    public void deposit(int amount) {
        transactions.add(new BankTransaction(this, amount));
        balance += amount;
    }

    public boolean currencyEquals(BankAccount otherAccount) {
        return currency.equals(otherAccount.currency);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BankAccount) obj;
        return this.id == that.id &&
                this.ownerId == that.ownerId &&
                this.balance == that.balance &&
                Objects.equals(this.currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerId, balance, currency);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", balance=" + balance +
                ", currency=" + currency +
                '}';
    }
}
