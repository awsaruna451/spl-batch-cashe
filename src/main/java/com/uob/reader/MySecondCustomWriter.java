package com.uob.reader;

import com.uob.model.Manager;
import com.uob.repo.ManagerRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MySecondCustomWriter implements ItemWriter<Manager> {

    @Autowired
    ManagerRepository managerRepository ;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void write(List<? extends Manager> list) throws Exception {
        int id=0;

        for (Manager data : list) {
            System.out.println("MyCustomWriter    : Writing data    : " + data.getId()+" : "+data.getName()+" : "+data.getContactNo());
            Manager manager = managerRepository.save(data);
            //redisTemplate.opsForValue().set("MANAGER", manager);
            redisTemplate.opsForHash().delete("MANAGER1", manager.getId());
            redisTemplate.opsForHash().put("MANAGER1", manager.getId(), manager);
            id = manager.getId();
        }
      //  redisTemplate.opsForHash().get("MANAGER", id);
    }
}
