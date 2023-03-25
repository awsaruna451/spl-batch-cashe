package com.uob.reader;

import javax.sql.DataSource;

import com.uob.model.Customer;
import com.uob.model.Employee;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;





@Component
public class MyCustomReader extends JdbcCursorItemReader<Customer> implements ItemReader<Customer>{


    public MyCustomReader(DataSource dataSource) {
        setDataSource(dataSource);
        setSql("SELECT customer_id, FIRST_NAME, CONTACT FROM customers_info");
        setFetchSize(100);
        setRowMapper(new EmployeeRowMapper());
    }

    public class EmployeeRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer employee  = new Customer();
            employee.setId(rs.getInt("CUSTOMER_ID"));
            employee.setFirstName(rs.getString("FIRST_NAME"));
            employee.setContactNo(rs.getString("CONTACT"));
            return employee;
        }
    }
}
