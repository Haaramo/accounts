package fi.sovellustalo.accounts;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Currency;
import java.util.Objects;

@Entity
public final class BankAccount {
    @Id
    public int id;
    public int ownerId;
    public int balance;
    public Currency currency;

    public BankAccount(int id, int ownerId, int balance, Currency currency) {
        this.id = id;
        this.ownerId = ownerId;
        this.balance = balance;
        this.currency = currency;
    }

    public BankAccount() {
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
        return "BankAccount[" +
                "id=" + id + ", " +
                "ownerId=" + ownerId + ", " +
                "balance=" + balance + ", " +
                "currency=" + currency + ']';
    }


}
