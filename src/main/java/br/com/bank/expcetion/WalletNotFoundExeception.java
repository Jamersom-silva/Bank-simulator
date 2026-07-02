package br.com.bank.expcetion;

public class WalletNotFoundExeception extends RuntimeException {
    public WalletNotFoundExeception(String message) {
        super(message);
    }
}
