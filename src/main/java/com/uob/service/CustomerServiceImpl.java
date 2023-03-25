package com.uob.service;

import com.uob.model.Manager;
import com.uob.repo.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    ManagerRepository managerRepository;

    @Override
    @Cacheable("MANAGER1")
    public List<Manager> getManagers() {
        return managerRepository.findAll();
    }
}
