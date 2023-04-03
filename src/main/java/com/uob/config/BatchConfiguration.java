package com.uob.config;

import com.uob.model.*;
import com.uob.reader.*;
import com.uob.repo.CustomerRepository;
import com.uob.repo.ManagerRepository;
import com.uob.repo.OrderLineItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableBatchProcessing
@EnableJpaRepositories(basePackages = "com.uob.repo")
public class BatchConfiguration {

    final static Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    MyCustomWriter myCustomWriter;
    @Autowired
    OrderItemBatchWriter orderItemBatchWriter;
    @Autowired
    MyCustomProcessor myCustomProcessor;
    @Autowired
    OrderLineItemProcessor lineItemProcessor;

    @Autowired
    @Qualifier("orderLineRepo")
    OrderLineItemRepository orderLineItemRepository;

    @Autowired
    @Qualifier("customerRepo")
    private CustomerRepository myEntityRepository;
    @Autowired
    @Qualifier("mangerRepo")
    private ManagerRepository managerRepository;;

    @Autowired
    private DataSource dataSource;


    @Bean(name = "customerReader")
    public CustomerManagerItemReader multiJpaItemReader() {
        return new CustomerManagerItemReader(myEntityRepository,managerRepository);
    }

    @Bean(name = "multiJPAReader")
    public JpaItemReader<Customer> jpaItemReader() {
        return new JpaItemReader<>(myEntityRepository);
    }

    @Bean(name = "orderLineItemReader")
    public OrderItemReader<OrderLineItems> jpaOrderLineItemReader() {
        return new OrderItemReader<>(orderLineItemRepository);
    }
    @Bean(name = "multiJpaWriter")
    public MultiObjectWriter multiJpaWriter() {
        return new MultiObjectWriter();
    }


    @Bean(name = "orderItemJob")
    public Job secondJob() {
        logger.info("SECOND LOGGERR::::");
        return jobBuilderFactory.get("orderItemJob")
                .flow(secondStep()).end().build();
    }

    @Bean(name = "customerJob")
    public Job createJob() {
        logger.info("MYSTEP LOGGERR::::");
        return jobBuilderFactory.get("customerJob")
                .flow(createStep()).end().build();
    }
    @Bean(name = "multiJPAJob")
    public Job multiJPAJob() {
        logger.info("MYSTEP LOGGERR::::");
        return jobBuilderFactory.get("multiJPAJob")
                .flow(multiJPAStep()).end().build();
    }

    @Bean
    public Step createStep() {
        return stepBuilderFactory.get("MyStep")
                .<Customer, Manager> chunk(10)
                .reader(jpaItemReader())
                .processor(myCustomProcessor)
                .writer(myCustomWriter)
                .build();
    }
    @Bean
    public Step multiJPAStep() {
        return stepBuilderFactory.get("multiJPAStep")
                .<MultiJobObject, MultiJobObject> chunk(1)
                .reader(multiJpaItemReader())
                .writer(multiJpaWriter())
                .build();
    }

    @Bean
    public Step secondStep() {
        return stepBuilderFactory.get("secondStep")
                .<OrderLineItems, OrderLineItemsBatch> chunk(100)
                .reader(jpaOrderLineItemReader() )
                .processor(lineItemProcessor)
                .writer(orderItemBatchWriter)
                .build();
    }
}

