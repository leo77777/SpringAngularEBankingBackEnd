package fr.leo.springangularebankingbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.leo.springangularebankingbackend.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
