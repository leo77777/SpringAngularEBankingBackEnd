package fr.leo.springangularebankingbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.leo.springangularebankingbackend.entities.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

}	
