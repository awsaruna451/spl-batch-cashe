package com.uob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BatchApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(BatchApplication.class);

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public void run(String... jobNames) throws Exception {
        // Execute the first job
        logger.info("TttttTttttttttttttt::::::"+jobNames);


        for (String jobName : jobNames) {
            Job job = getJob(jobName);
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(job, jobParameters);
        }

    }

/*
    private Job getJob(String jobName) {
        switch (jobName) {
            case "orderItemJob":
                return job;
            case "customerJob":
                return job2;
            default:
                throw new IllegalArgumentException("No such job: " + jobName);
        }
    }
*/

    public Job getJob(String jobBeanId) {
        return applicationContext.getBean(jobBeanId, Job.class);
    }
}
