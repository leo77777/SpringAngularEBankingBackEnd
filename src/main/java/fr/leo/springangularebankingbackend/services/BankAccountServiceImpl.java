package fr.leo.springangularebankingbackend.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.leo.springangularebankingbackend.entities.AccountOperation;
import fr.leo.springangularebankingbackend.entities.BankAccount;
import fr.leo.springangularebankingbackend.entities.CurrentAccount;
import fr.leo.springangularebankingbackend.entities.Customer;
import fr.leo.springangularebankingbackend.entities.SavingAccount;
import fr.leo.springangularebankingbackend.enums.EnumOperationType;
import fr.leo.springangularebankingbackend.exceptions.BalanceNotSuficientException;
import fr.leo.springangularebankingbackend.exceptions.BankAccountNotFoundException;
import fr.leo.springangularebankingbackend.exceptions.CustomerNotFoundException;
import fr.leo.springangularebankingbackend.repositories.AccountOperationRepository;
import fr.leo.springangularebankingbackend.repositories.BankAccountRepository;
import fr.leo.springangularebankingbackend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Fanny
 * Ici toutes les transactions sont transactionnelles !
 * Si il n'y a pas d'excption on fait "commit".
 * Si une exception est générée, on fait "rollback"
 */

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
	
	private CustomerRepository customerRepository;
	private BankAccountRepository bankAccountRepository;
	private AccountOperationRepository accountOperationRepository;	
	
	// Ci dessous, déjà créer par Lombok !
	//Logger log=LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public Customer saveCustomer(Customer customer) {
		log.info("Saving new customer ...");
		Customer savedCustomer =  customerRepository.save(customer);
		return savedCustomer;
	}
	
	@Override
	public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
			throws CustomerNotFoundException {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not found");
		}
		CurrentAccount currentAccount = new CurrentAccount();
		currentAccount.setId(UUID.randomUUID().toString());
		currentAccount.setCreationDate(new Date());
		currentAccount.setBalance(initialBalance);
		currentAccount.setCustomer(customer);
		currentAccount.setOverDraft(overDraft);
		CurrentAccount savedBankAccount =  bankAccountRepository.save(currentAccount);		
		return savedBankAccount;
	}

	@Override
	public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
			throws CustomerNotFoundException {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not found");
		}
		SavingAccount savingAccount = new SavingAccount();
		savingAccount.setId(UUID.randomUUID().toString());
		savingAccount.setCreationDate(new Date());
		savingAccount.setBalance(initialBalance);
		savingAccount.setCustomer(customer);
		savingAccount.setInterestRate(interestRate);
		SavingAccount savedBankAccount =  bankAccountRepository.save(savingAccount);		
		return savedBankAccount;
	}

	@Override
	public List<Customer> listCustomers() {		
		return customerRepository.findAll();
	}

	@Override
	public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
		BankAccount bankAccount = bankAccountRepository.findById(accountId)
				.orElseThrow( ()->new BankAccountNotFoundException("BankAccount not found"));
		return bankAccount;

	}

	@Override
	public void debit(String accountI, double amount, String description) throws BankAccountNotFoundException, BalanceNotSuficientException {
		BankAccount bankAccount = getBankAccount(accountI);
		if (bankAccount.getBalance() < amount) {
			throw new BalanceNotSuficientException("Balance not suficient");
		}
		AccountOperation accountOperation = new AccountOperation();
		accountOperation.setType(EnumOperationType.DEBIT);
		accountOperation.setAmount(amount);
		accountOperation.setDescription(description);
		accountOperation.setOperationDate(new Date());
		accountOperationRepository.save(accountOperation); 
		bankAccount.setBalance(bankAccount.getBalance() - amount);
		bankAccountRepository.save(bankAccount);
	}

	@Override
	public void credit(String accountI, double amount, String description) throws BankAccountNotFoundException {
		BankAccount bankAccount = getBankAccount(accountI);		
		AccountOperation accountOperation = new AccountOperation();
		accountOperation.setType(EnumOperationType.CREDIT);
		accountOperation.setAmount(amount);
		accountOperation.setDescription(description);
		accountOperation.setOperationDate(new Date());
		accountOperationRepository.save(accountOperation); 
		bankAccount.setBalance(bankAccount.getBalance() + amount);
		bankAccountRepository.save(bankAccount);
	}

	@Override
	public void transfert(String AccountIdSource, String AccountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSuficientException {
		debit(AccountIdSource, amount, "Transfert to " + AccountIdDestination);
		credit(AccountIdDestination, amount, "Transfert from " + AccountIdSource);
	}
	
	@Override
	public List<BankAccount> bankAccountList(){
		return bankAccountRepository.findAll();
	}
}
