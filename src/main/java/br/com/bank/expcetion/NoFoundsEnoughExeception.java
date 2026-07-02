package br.com.bank.expcetion;

public class NoFoundsEnoughExeception extends RuntimeException {
    public NoFoundsEnoughExeception(String message) {
        super(message);
    }
}
