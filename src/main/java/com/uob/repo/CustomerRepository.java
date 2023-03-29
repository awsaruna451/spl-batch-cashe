package com.uob.repo;

import com.uob.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("customerRepo")
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}