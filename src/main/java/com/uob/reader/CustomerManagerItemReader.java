package com.uob.reader;

import com.uob.model.Customer;
import com.uob.model.Manager;
import com.uob.model.MultiJobObject;
import com.uob.repo.CustomerRepository;
import com.uob.repo.ManagerRepository;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.*;

public class CustomerManagerItemReader implements ItemReader<MultiJobObject> , StepExecutionListener {

    private final CustomerRepository customerRepository;
    private final ManagerRepository managerRepository;
    private List<Customer> customerIterator;
    private List<Manager> managerIterator;
    private MultiJobObject iterator = null;

    public CustomerManagerItemReader(CustomerRepository customerRepository, ManagerRepository managerRepository) {

        this.customerRepository = customerRepository;
        this.managerRepository =  managerRepository;
    }

    @Override
    public MultiJobObject read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (iterator != null) {
            MultiJobObject result = iterator;
            iterator = null;
            return result;
        }
        return null;

    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
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


        iterator = MultiJobObject.builder()
                .customerMap(cuMap)
                .managerMap(maMap).build();

    }



    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}

