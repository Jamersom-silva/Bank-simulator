package br.com.bank.repository;

import br.com.bank.model.AccountWallet;
import br.com.bank.model.Money;
import br.com.bank.model.MoneyAudit;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.bank.repository.CommonsRepository.checkFundsForTransaction;

public class AccountRepository {

    private final List<AccountWallet> accounts = new ArrayList<>();

    public AccountWallet create(final List<String> pix, final long initialFunds) {
        var newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix, final long fundsAmount) {
        var target = findByPix(pix);

        var history = new MoneyAudit(
                UUID.randomUUID(),
                target.getService(),
                "deposito",
                OffsetDateTime.now()
        );

        var money = Stream.generate(() -> new Money(history))
                .limit(fundsAmount)
                .toList();

        target.addMoney(money, target.getService(), "deposito");
    }

    public long withdraw(final String pix, final long amount) {
        var source = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceMoney(amount);
        return amount;
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount) {
        var source = findByPix(sourcePix);
        checkFundsForTransaction(source, amount);

        var target = findByPix(targetPix);
        var message = "pix enviado de " + sourcePix + " para " + targetPix;

        target.addMoney(source.reduceMoney(amount), source.getService(), message);
    }

    public AccountWallet findByPix(final String pix) {
        return accounts.stream()
                .filter(account -> account.getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "A conta com a chave pix '" + pix + "' não existe ou foi encerrada."
                ));
    }

    public List<AccountWallet> list() {
        return this.accounts;
    }
}