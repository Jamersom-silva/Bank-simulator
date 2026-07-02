package br.com.bank.repository;

import br.com.bank.model.AccountWallet;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

import static br.com.bank.repository.CommonsRepository.checkFundsForTransaction;

public class AccountRepository {

    private List <AccountWallet>  accounts;

    public AccountWallet create (final List<String> pix, final long initialFunds){
        var newAccount = new AccountWallet(initialFunds, pix);
        return newAccount;
    }

    public void deposit (final String pix, final long fundsAmount){
        var target = findByPix(pix);
        target.addMoney(fundsAmount, description "deposito");
    }

    public long withdraw (final String pix, final long Amount){
        var source = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceMoney(amount);
        return amount;
    }

    public void transferMoney (final String sourcePix, final String targetPix,final long amount){
        var source = findByPix(sourcePix);
        checkFundsForTransaction(source, amount);
        var target = findByPix(targetPix);
        var message = "pix enviado de " + sourcePix + " para " + targetPix;
        target.addMoney(source.reduceMoney(amount), source.getService(), message);
    }

    public AccountWallet findByPix(final String pix) {
        return accounts.stream().filter( AccountWallet a -> a.getPix().contains(pix)).findFirst().orElseThrow() -> new AccountNotFoundException("a conta com a chave pix' " + pix "não existe"'ou foi encerrada ');
    }

    public List<AccountWallet> list () {
        return this.accounts;
    }
}
