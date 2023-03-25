package com.uob.config;

import com.uob.model.Customer;
import com.uob.model.Manager;
import com.uob.reader.MyCustomProcessor;
import com.uob.reader.MyCustomReader;
import com.uob.reader.MyCustomWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    MyCustomWriter myCustomWriter;

    @Autowired
    MyCustomProcessor myCustomProcessor;

    @Autowired
    private DataSource dataSource;

    @Bean
    public Job createJob() {
        return jobBuilderFactory.get("MyJob")
                .incrementer(new RunIdIncrementer())
                .flow(createStep()).end().build();
    }

    @Bean
    public Step createStep() {
        return stepBuilderFactory.get("MyStep")
                .<Customer, Manager> chunk(100)
                .reader(new MyCustomReader(dataSource))
                .processor(myCustomProcessor)
                .writer(myCustomWriter)
                .build();
    }
}

