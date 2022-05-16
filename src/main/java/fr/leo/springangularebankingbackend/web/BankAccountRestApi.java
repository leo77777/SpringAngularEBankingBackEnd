package fr.leo.springangularebankingbackend.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.leo.springangularebankingbackend.dtos.AccountHistoryDTO;
import fr.leo.springangularebankingbackend.dtos.AccountOperationDTO;
import fr.leo.springangularebankingbackend.dtos.BankAccountDto;
import fr.leo.springangularebankingbackend.exceptions.BankAccountNotFoundException;
import fr.leo.springangularebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BankAccountRestApi {
	
	private BankAccountService bankAccountService;
	
	@GetMapping("/accounts/{accountId}")
	public BankAccountDto getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
		return bankAccountService.getBankAccount(accountId);
	}
	
	@GetMapping("/accounts")
	public List<BankAccountDto> listAccounts(){
		return bankAccountService.bankAccountList();
	}
	
	@GetMapping("/accounts/{accountId}/operations")
	public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
		return bankAccountService.accountHistory(accountId);
	}
	
	@GetMapping("/accounts/{accountId}/pageOperations")
	public AccountHistoryDTO getAccountHistory(
								@PathVariable String accountId,
								@RequestParam(name="page", defaultValue = "0") int page,
								@RequestParam(name="size", defaultValue = "5") int size ) throws BankAccountNotFoundException{
		return bankAccountService.getAccountHistory(accountId,page, size);
	}
	

}
