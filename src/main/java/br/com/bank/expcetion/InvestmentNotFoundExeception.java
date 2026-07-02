package br.com.bank.expcetion;

public class InvestmentNotFoundExeception extends RuntimeException {
    public InvestmentNotFoundExeception(String message) {
        super(message);
    }
}
