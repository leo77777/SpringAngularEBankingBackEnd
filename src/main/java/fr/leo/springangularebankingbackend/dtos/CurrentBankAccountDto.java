package fr.leo.springangularebankingbackend.dtos;

import java.util.Date;

import fr.leo.springangularebankingbackend.enums.EnumAccountStatus;
import lombok.Data;


@Data
public  class CurrentBankAccountDto extends BankAccountDto{	
	private String id;
	private double balance;
	private Date creationDate;
	private EnumAccountStatus status;	
	private CustomerDTO customerDTO;
	protected double overDraft;
}
