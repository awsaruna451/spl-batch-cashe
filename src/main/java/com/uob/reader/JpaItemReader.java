package com.uob.reader;

import com.uob.repo.CustomerRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;

public class JpaItemReader<T> implements ItemReader<T> {

    private final CustomerRepository repository;
    private Iterator<T> iterator;

    public JpaItemReader(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (iterator == null) {
            iterator = (Iterator<T>) repository.findAll().iterator();
        }

        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }
}
