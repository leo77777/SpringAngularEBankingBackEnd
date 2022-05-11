package fr.leo.springangularebankingbackend.exceptions;

public class BankAccountNotFoundException extends Exception {
	
	public BankAccountNotFoundException(String message) {
		super(message);
	}

}
