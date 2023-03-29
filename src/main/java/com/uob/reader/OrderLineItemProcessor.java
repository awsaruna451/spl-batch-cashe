package com.uob.reader;

import com.uob.model.OrderLineItems;
import com.uob.model.OrderLineItemsBatch;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class OrderLineItemProcessor implements ItemProcessor<OrderLineItems, OrderLineItemsBatch> {

    @Override
    public OrderLineItemsBatch process(OrderLineItems emp) throws Exception {
        System.out.println("MyBatchProcessor : Processing data : "+emp);
        OrderLineItemsBatch manager = new OrderLineItemsBatch();
        manager.setPrice(emp.getPrice());
        manager.setSkuCode(emp.getSkuCode());
        manager.setQuantity(emp.getQuantity());
        return manager;
    }
}
