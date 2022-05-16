package fr.leo.springangularebankingbackend.dtos;

import java.util.Date;

import fr.leo.springangularebankingbackend.enums.EnumOperationType;
import lombok.Data;

@Data
public class AccountOperationDTO {
	private Long id;
	private Date operationDate;
	private double amount;	
	private EnumOperationType type;	
	private String description;
}
