package fr.leo.springangularebankingbackend.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import fr.leo.springangularebankingbackend.dtos.CustomerDTO;
import fr.leo.springangularebankingbackend.entities.Customer;

@Service
public class BankAccountMapperImpl {

	public CustomerDTO fromCustomer(Customer customer ) {
		CustomerDTO customerDTO = new CustomerDTO();	
		// Solution Spring :
		BeanUtils.copyProperties(customer, customerDTO);		
		// Solution basique :
		//customerDTO.setId(customer.getId());
		//customerDTO.setEmail(customer.getEmail());
		//customerDTO.setName(customer.getName());
		// Autre solution : frameworks "MapStruct", "JMapper" !
		// Ce code, c'est du code technique, et donc on utilise des frameworks 
		//  qui permettent de génerer ce code !
		return customerDTO;
	}
	
	public Customer fromCustomerDTO( CustomerDTO customerDTO ) {		
		Customer customer = new Customer();
		// Solution Spring :
		BeanUtils.copyProperties(customerDTO, customer);
		// Solution basique :
		//customer.setId(customerDTO.getId());
		//customer.setEmail(customerDTO.getEmail());
		//customer.setName(customerDTO.getName());
		// Autre solution : framework "MapStruct", "JMapper" !
		return customer;	
	}
}
