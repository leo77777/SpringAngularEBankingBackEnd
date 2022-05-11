package fr.leo.springangularebankingbackend.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import fr.leo.springangularebankingbackend.enums.EnumOperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperation {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date operationDate;
	private double amount;
	
	@Enumerated(EnumType.STRING)
	private EnumOperationType type;
	
	@ManyToOne
	private BankAccount bankAccount;
	
	private String description;
}
