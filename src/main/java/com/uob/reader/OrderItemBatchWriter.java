package com.uob.reader;


import com.uob.model.OrderLineItemsBatch;
import com.uob.repo.OrderLineItemBatchRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemBatchWriter implements ItemWriter<OrderLineItemsBatch> {

    @Autowired
    OrderLineItemBatchRepository orderLineItemBatchRepository ;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void write(List<? extends OrderLineItemsBatch> list) throws Exception {
        int id=0;

        for (OrderLineItemsBatch data : list) {
            //System.out.println("MyCustomWriter    : Writing data    : " + data.getId()+" : "+data.getName()+" : "+data.getContactNo());
            OrderLineItemsBatch manager = orderLineItemBatchRepository.save(data);
            //redisTemplate.opsForValue().set("MANAGER", manager);
         //   redisTemplate.opsForHash().delete("MANAGER1", manager.getId());
          //  redisTemplate.opsForHash().put("MANAGER1", manager.getId(), manager);
           // id = manager.getId();
        }
      //  redisTemplate.opsForHash().get("MANAGER", id);
    }
}
