package fr.leo.springangularebankingbackend.services;

import java.util.List;

import fr.leo.springangularebankingbackend.dtos.CustomerDTO;
import fr.leo.springangularebankingbackend.entities.BankAccount;
import fr.leo.springangularebankingbackend.entities.CurrentAccount;
import fr.leo.springangularebankingbackend.entities.Customer;
import fr.leo.springangularebankingbackend.entities.SavingAccount;
import fr.leo.springangularebankingbackend.exceptions.BalanceNotSuficientException;
import fr.leo.springangularebankingbackend.exceptions.BankAccountNotFoundException;
import fr.leo.springangularebankingbackend.exceptions.CustomerNotFoundException;

public interface BankAccountService {	

	CurrentAccount saveCurrentBankAccount( double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
	SavingAccount saveSavingBankAccount( double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
	List<CustomerDTO> listCustomers();
	BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
	void debit(String accountI, double amount,String description ) throws BankAccountNotFoundException, BalanceNotSuficientException;
	void credit(String accountI, double amount,String description ) throws BankAccountNotFoundException;
	void transfert(String AccountIdSource, String AccountIdDestination, double amount ) throws BankAccountNotFoundException, BalanceNotSuficientException;
	List<BankAccount> bankAccountList();
	CustomerDTO getCustomer(Long idCustomer) throws CustomerNotFoundException;
	CustomerDTO saveCustomer(CustomerDTO customerDto);
	//Customer saveCustomer(Customer customer);
}
