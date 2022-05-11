package fr.leo.springangularebankingbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.leo.springangularebankingbackend.entities.AccountOperation;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

}	
