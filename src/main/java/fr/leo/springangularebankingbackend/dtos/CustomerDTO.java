package fr.leo.springangularebankingbackend.dtos;

import lombok.Data;

/**
 * Dtos : généralement on a besoin que des Getters/Setters
 * @author Leo
 */
@Data
public class CustomerDTO {	
	private Long id;
	private String name;
	private String email;
}
