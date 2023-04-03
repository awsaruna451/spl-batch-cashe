package com.uob.reader;

import com.uob.model.Customer;
import com.uob.model.Manager;
import com.uob.model.MultiJobObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MultiObjectWriter implements ItemWriter<MultiJobObject> {



    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void write(List<? extends MultiJobObject> list) throws Exception {
        for (MultiJobObject data : list) {
            Map<String, List<Customer>> customerMap  =  data.getCustomerMap();
            Map<String, List<Manager>> managerMap  =  data.getManagerMap();
            log.info("CUSTOMER OBECT {} :::manager map{}", customerMap.get("CUS"),managerMap.get("MA"));
        }
    }
}
