package fi.sovellustalo.accounts;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Currency;

@Entity
public record Account(@Id int id, int ownerId, int balance, Currency currency) {
}
