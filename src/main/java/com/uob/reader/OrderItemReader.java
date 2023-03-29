package com.uob.reader;


import com.uob.repo.OrderLineItemRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;


import java.util.Iterator;

public class OrderItemReader<T> implements ItemReader<T> {

    private final OrderLineItemRepository repository;
    private Iterator<T> iterator;

    public OrderItemReader(OrderLineItemRepository repository) {
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

