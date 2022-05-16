package fr.leo.springangularebankingbackend.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.leo.springangularebankingbackend.dtos.AccountOperationDTO;
import fr.leo.springangularebankingbackend.entities.AccountOperation;
import fr.leo.springangularebankingbackend.entities.BankAccount;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
	
	public List<AccountOperation> findByBankAccountId(String accountId);
	
	public Page<AccountOperation>  findByBankAccountId(String accountId , Pageable pageable);

}	
