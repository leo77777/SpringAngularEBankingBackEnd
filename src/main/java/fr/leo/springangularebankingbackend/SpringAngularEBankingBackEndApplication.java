package fr.leo.springangularebankingbackend;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.leo.springangularebankingbackend.dtos.BankAccountDto;
import fr.leo.springangularebankingbackend.dtos.CurrentBankAccountDto;
import fr.leo.springangularebankingbackend.dtos.CustomerDTO;
import fr.leo.springangularebankingbackend.dtos.SavingBankAccountDto;
import fr.leo.springangularebankingbackend.entities.AccountOperation;
import fr.leo.springangularebankingbackend.entities.BankAccount;
import fr.leo.springangularebankingbackend.entities.CurrentAccount;
import fr.leo.springangularebankingbackend.entities.Customer;
import fr.leo.springangularebankingbackend.entities.SavingAccount;
import fr.leo.springangularebankingbackend.enums.EnumAccountStatus;
import fr.leo.springangularebankingbackend.enums.EnumOperationType;
import fr.leo.springangularebankingbackend.exceptions.BalanceNotSuficientException;
import fr.leo.springangularebankingbackend.exceptions.BankAccountNotFoundException;
import fr.leo.springangularebankingbackend.exceptions.CustomerNotFoundException;
import fr.leo.springangularebankingbackend.repositories.AccountOperationRepository;
import fr.leo.springangularebankingbackend.repositories.BankAccountRepository;
import fr.leo.springangularebankingbackend.repositories.CustomerRepository;
import fr.leo.springangularebankingbackend.services.BankAccountService;
import fr.leo.springangularebankingbackend.services.BankAccountServiceImpl;
import fr.leo.springangularebankingbackend.services.BankService;

@SpringBootApplication
public class SpringAngularEBankingBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAngularEBankingBackEndApplication.class, args);
	}
	
	
	// CREATION DES DONNEES:
	//@Bean
	CommandLineRunner start1(CustomerRepository customerRepository,
							AccountOperationRepository accountOperationRepository,
							BankAccountRepository accountRepository) {
		return args->{
			Stream.of("Joe","Averel","Rantanplan").forEach(name->{
				Customer customer = new Customer(null, name, name+"@free.fr", null);
				customerRepository.save(customer);
				});
			
			customerRepository.findAll().forEach(cust->{
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setCustomer(cust);
				currentAccount.setBalance( Math.random()*100000);
				currentAccount.setCreationDate(new Date());
				currentAccount.setStatus(EnumAccountStatus.CREATED);
				currentAccount.setOverDraft(10000);
				accountRepository.save(currentAccount);
				
				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setCustomer(cust);
				savingAccount.setBalance( Math.random()*100000);
				savingAccount.setCreationDate(new Date());
				savingAccount.setStatus(EnumAccountStatus.CREATED);
				savingAccount.setInterestRate(5.5);
				accountRepository.save(savingAccount);
			}); 
			
			accountRepository.findAll().forEach( account->{
				for (int i = 0; i < 5; i++) {
					AccountOperation accountOperation = new AccountOperation(
								null, 
								new Date(),
								Math.random() * 120000,
								Math.random()>0.5 ? EnumOperationType.DEBIT : EnumOperationType.CREDIT  ,
								account,
								"Nouveau compte !");
					accountOperationRepository.save(accountOperation);
				}	
			});			
		};
	}	
	
	// DISPLAY DES DONNEES:
	// @Bean
	CommandLineRunner start(BankService bankService) {
		return args->{	
			bankService.consulter();
			};
	}
	
	// CREATION DES DONNEES: COUCHE SERVICE
	// @Bean
	CommandLineRunner start(BankAccountService  bankAccountService) {
		return args->{	
			Stream.of("Joe2","Averel2","Rantanplan2").forEach(name->{
				CustomerDTO customerDto = new CustomerDTO();
				customerDto.setName(name);
				customerDto.setEmail( name+"@free.fr");
				bankAccountService.saveCustomer(customerDto);
			});
			
			bankAccountService.listCustomers().forEach(customer->{
				try {
					bankAccountService.saveCurrentBankAccount(
							Math.random()*100000,
							9000, 
							customer.getId());
					bankAccountService.saveSavingBankAccount(
								Math.random()*100000,
								5.5,
								customer.getId());
					
//					bankAccountService.bankAccountList().forEach((account)->{
//						for (int i = 0; i < 10; i++) {
//							// On est obligé de faire un Try/Catch, car on est à l'intérieur
//							//  d'une expression Lambda ! On ne peut pas ajouter une exception
//							//  à notre try/catch externe déja existant !!!
//							try {
//								bankAccountService.credit(account.getId() ,
//										10000 + Math.random()*120000, "Credit");
//							} catch (BankAccountNotFoundException e) {
//								e.printStackTrace();
//							}							
//						}
//					});		
				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				} 
			});
			
			List<BankAccountDto> bankAccounts = bankAccountService.bankAccountList(); 
			for(BankAccountDto bankAccount : bankAccounts) {
				for (int i = 0; i < 10; i++) {
					String accountId;
					if (bankAccount instanceof SavingBankAccountDto) {
						accountId = ((SavingBankAccountDto) bankAccount).getId();
					}else {
						accountId = ((CurrentBankAccountDto) bankAccount).getId();
					}
					bankAccountService.credit(accountId ,
							10000 + Math.random()*120000, "Credit");
					bankAccountService.debit(accountId ,
							1000 + Math.random()*12000, "Debit");
				}
			}			
		};
	}
}
