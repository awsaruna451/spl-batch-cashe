package com.uob.repo;


import com.uob.model.OrderLineItemsBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("orderLineBatchRepo")
public interface OrderLineItemBatchRepository extends JpaRepository<OrderLineItemsBatch, Long> {
}
