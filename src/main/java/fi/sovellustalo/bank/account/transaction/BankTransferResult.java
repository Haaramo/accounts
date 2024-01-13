package fi.sovellustalo.bank.account.transaction;

import fi.sovellustalo.bank.account.BankAccount;

import java.util.Objects;

public record BankTransferResult(BankAccount fromAccount, BankAccount toAccount) {

    public BankTransferResult truncateTransactions(int maxTransactions) {
        var from = new BankAccount(fromAccount.id, fromAccount.ownerId, fromAccount.balance, fromAccount.currency);
        var limit = Math.min(maxTransactions, fromAccount.transactions.size());
        from.transactions = fromAccount.transactions.subList(0, limit);

        var to = new BankAccount(toAccount.id, toAccount.ownerId, toAccount.balance, toAccount.currency);
        limit = Math.min(maxTransactions, toAccount.transactions.size());
        to.transactions = toAccount.transactions.subList(0, limit);

        return new BankTransferResult(from, to);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BankTransferResult) obj;
        return Objects.equals(this.fromAccount, that.fromAccount) &&
                Objects.equals(this.toAccount, that.toAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromAccount, toAccount);
    }

    @Override
    public String toString() {
        return "BankTransferResult[" +
                "fromAccount=" + fromAccount + ", " +
                "toAccount=" + toAccount + ']';
    }
}
