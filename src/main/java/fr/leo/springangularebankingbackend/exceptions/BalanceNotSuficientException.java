package fr.leo.springangularebankingbackend.exceptions;

public class BalanceNotSuficientException extends Exception {
	
	public BalanceNotSuficientException(String message) {
		super(message);
	}

}
