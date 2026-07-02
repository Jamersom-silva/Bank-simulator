package br.com.bank.expcetion;

public class AccountNotFoundExeception extends RuntimeException {
    public AccountNotFoundExeception(String message) {
        super(message);
    }
}
