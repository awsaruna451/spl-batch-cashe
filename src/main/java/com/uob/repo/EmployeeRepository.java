package com.uob.repo;

import com.uob.model.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("employeeRepo")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}