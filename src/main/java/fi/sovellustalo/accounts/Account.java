package fi.sovellustalo.accounts;

import java.util.Currency;

public record Account(int id, int ownerId, int balance, Currency currency) {
}
