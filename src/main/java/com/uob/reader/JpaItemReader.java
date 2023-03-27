package com.uob.reader;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Iterator;

public class JpaItemReader<T> implements ItemReader<T> {

    private final JpaRepository<T, Integer> repository;
    private Iterator<T> iterator;

    public JpaItemReader(JpaRepository<T, Integer> repository) {
        this.repository = repository;
    }

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (iterator == null) {
            iterator = repository.findAll().iterator();
        }

        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }
}

