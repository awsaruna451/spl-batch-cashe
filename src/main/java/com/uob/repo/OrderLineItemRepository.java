package com.uob.repo;


import com.uob.model.OrderLineItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("orderLineRepo")
public interface OrderLineItemRepository extends JpaRepository<OrderLineItems, Long> {
}
