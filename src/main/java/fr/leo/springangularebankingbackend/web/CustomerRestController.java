package fr.leo.springangularebankingbackend.web;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.leo.springangularebankingbackend.dtos.CustomerDTO;
import fr.leo.springangularebankingbackend.exceptions.CustomerNotFoundException;
import fr.leo.springangularebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor // Injection des dépendances
@Slf4j
public class CustomerRestController {
	
	private BankAccountService bankAccountService;
	
	@GetMapping("/customers")
	public List<CustomerDTO> customers(){
		return bankAccountService.listCustomers();
	}
	
	@GetMapping("/customers/{id}")
	public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
		return bankAccountService.getCustomer(customerId);
	}
	
	@PostMapping("/customers/") // on va recevoir le customer dans le corps de la requete au format json
	public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
		return bankAccountService.saveCustomer(customerDTO);
	}

	@PutMapping("/customers/{customerId}")
	public CustomerDTO updateCustomer(@PathVariable Long customerId,
									  @RequestBody CustomerDTO customerDTO){
			customerDTO.setId(customerId);
			return bankAccountService.updateCustomer(customerDTO);
	}
	
	@DeleteMapping("/customers/{Id}")
	public void deleteCustomer(@PathVariable Long Id) {
		bankAccountService.deleteCustomer(Id);
	}
}
