package com.uob.config;

import com.uob.model.Customer;
import com.uob.model.Manager;
import com.uob.reader.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

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
    MySecondCustomWriter mySecondCustomWriter;

    @Autowired
    MyCustomProcessor myCustomProcessor;

    @Autowired
    private JpaRepository<Customer, Integer> myEntityRepository;

    @Autowired
    private DataSource dataSource;


    @Bean(name = "jpa")
    public JpaItemReader<Customer> jpaItemReader() {
        return new JpaItemReader<>(myEntityRepository);
    }


    @Bean(name = "second")
    public Job secondJob() {
        logger.info("SECOND LOGGERR::::");
        return jobBuilderFactory.get("second")
                .incrementer(new RunIdIncrementer())
                .flow(secondStep()).end().build();
    }

    @Bean(name = "MyJob")
    public Job createJob() {
        logger.info("MYSTEP LOGGERR::::");
        return jobBuilderFactory.get("MyJob")
                .incrementer(new RunIdIncrementer())
                .flow(createStep()).end().build();
    }

    @Bean
    public Step createStep() {
        return stepBuilderFactory.get("MyStep")
                .<Customer, Manager> chunk(100)
                .reader(new MyCustomReader(dataSource))
               // .processor(myCustomProcessor)
                .writer(myCustomWriter)
                .build();
    }

    @Bean
    public Step secondStep() {
        return stepBuilderFactory.get("secondStep")
                .<Customer, Manager> chunk(100)
                .reader(jpaItemReader() )
                .writer(mySecondCustomWriter)
                .build();
    }
}

