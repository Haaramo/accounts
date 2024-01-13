package fi.sovellustalo.bank.account.transaction;

public record BankTransfer(int fromAccountId, int toAccountId, int amount) {
}
