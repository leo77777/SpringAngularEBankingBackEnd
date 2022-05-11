package fr.leo.springangularebankingbackend.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.leo.springangularebankingbackend.entities.Customer;
import fr.leo.springangularebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor // Injection des dépendances
@Slf4j
public class CustomerRestController {
	
	private BankAccountService bankAccountService;
	
	@GetMapping("/customers")
	public List<Customer> customers(){
		return bankAccountService.listCustomers();
	}

}
