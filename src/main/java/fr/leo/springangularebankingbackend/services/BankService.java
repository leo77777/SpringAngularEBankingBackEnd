package fr.leo.springangularebankingbackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.leo.springangularebankingbackend.entities.BankAccount;
import fr.leo.springangularebankingbackend.entities.CurrentAccount;
import fr.leo.springangularebankingbackend.entities.SavingAccount;
import fr.leo.springangularebankingbackend.repositories.BankAccountRepository;

@Service
@Transactional
public class BankService {
	
	@Autowired
	private BankAccountRepository accountRepository;
	
	public void consulter() {
		
		List<BankAccount> list=  accountRepository.findAll();
		
		System.out.println("\n************************************");
		System.out.println("Caractéristique du premier Compte dans la bdd : ");
		BankAccount account = list.get(0);
		System.out.println(account.getClass().getSimpleName());
		System.out.println(account.getId());
		System.out.println(account.getBalance());
		System.out.println(account.getStatus());
		System.out.println(account.getCreationDate());
		System.out.println(account.getCustomer().getName());
		if (account instanceof SavingAccount) {
			System.out.println("Taux d'interet : " + ((SavingAccount) account).getInterestRate());
		}else {
			System.out.println("Decouvert autorisé : " + (( CurrentAccount) account).getOverDraft());
		}			
		
		System.out.println("\nOpérations sur le compte " + account.getId() +": ");
		account.getAccountOperations().forEach(op->{
			System.out.print(op.getAmount() + "\t");
			System.out.print(op.getBankAccount() + "\t");
			System.out.println(op.getType());
		});

	}
}
