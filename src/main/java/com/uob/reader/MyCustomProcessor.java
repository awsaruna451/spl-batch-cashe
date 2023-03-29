package com.uob.reader;

import com.uob.model.Customer;
import com.uob.model.Manager;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyCustomProcessor implements ItemProcessor<Customer, Manager> {

    @Override
    public Manager process(Customer emp) throws Exception {
        System.out.println("MyBatchProcessor : Processing data : "+emp);
        Manager manager = new Manager();
        manager.setName(emp.getFirstName().toUpperCase());
        manager.setContactNo(emp.getContactNo());
        return manager;
    }
}
