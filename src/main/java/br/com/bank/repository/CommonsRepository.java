package br.com.bank.repository;

import br.com.bank.expcetion.NoFoundsEnoughExeception;
import br.com.bank.model.AccountWallet;
import br.com.bank.model.Money;
import br.com.bank.model.MoneyAudit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonsRepository {

    public static void  checkFundsForTransaction(final AccountWallet source, final long amount) {
        if (source.getFunds() < amount) {
            throw new NoFoundsEnoughExeception("Sua conta não tem dinheiro suficiente para essa transação");
        }
    }

    public static List<Money> generateMoney(final UUID transactionId, final long funds, final String description) {
        var history = new MoneyAudit(transactionId, ACCOUNT,description, OffsetDateTime.now());
        return Stream.generate(() -> new Money(history)).limit(funds).toList();
        
    }

}
