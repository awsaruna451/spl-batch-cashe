package com.uob.reader;

import com.uob.model.Customer;
import com.uob.model.Manager;
import com.uob.model.MultiJobObject;
import com.uob.repo.CustomerRepository;
import com.uob.repo.ManagerRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.*;


public class CustomerManagerItemReader implements ItemReader<MultiJobObject> {

    private final CustomerRepository customerRepository;
    private final ManagerRepository managerRepository;
    private List<Customer> customerIterator;
    private List<Manager> managerIterator;
    private Iterator<MultiJobObject> iterator;

    public CustomerManagerItemReader(CustomerRepository customerRepository, ManagerRepository managerRepository) {

        this.customerRepository = customerRepository;
        this.managerRepository =  managerRepository;
    }

    @Override
    public MultiJobObject read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (customerIterator == null || customerIterator.isEmpty()) {
            customerIterator = customerRepository.findAll();
        }
        if (managerIterator == null) {
            managerIterator = managerRepository.findAll();
        }
        Map<String, List<Customer>> cuMap = new HashMap<>();
        cuMap.put("CUS", customerIterator);
        Map<String, List<Manager>> maMap = new HashMap<>();
        maMap.put("MA", managerIterator);
        MultiJobObject multiJobObject = MultiJobObject.builder()
                .customerMap(cuMap)
                .managerMap(maMap).build();
        return multiJobObject;


    }
}

