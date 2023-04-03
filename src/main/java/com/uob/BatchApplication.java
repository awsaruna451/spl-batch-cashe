package com.uob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BatchApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(BatchApplication.class);

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private JobLocator jobLocator;


    @Override
    public void run(String... jobNames) throws Exception {
        // Execute the first job



        for (String jobName : jobNames) {
            if (jobName != null && !jobName.equals("ALL")) {

                Job job = getJob(jobName);
                executeJob(job);
               /* JobParameters jobParameters = new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis()).toJobParameters();
                jobLauncher.run(job, jobParameters);*/
            } else {

                for(Job job: getJobs()) {
                    executeJob(job);
                }

            }
        }

    }

    public List<Job> getJobs() throws NoSuchJobException {
        List<Job> jobs = new ArrayList<>();
        String[] jobNames = applicationContext.getBeanNamesForType(Job.class);
        for (String jobName : jobNames) {
            Job job = applicationContext.getBean(jobName, Job.class);;
            if (job != null) {
                jobs.add(job);
            }
        }
        return jobs;
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

    private void executeJob(Job job) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters();
       JobExecution execution = jobLauncher.run(job, jobParameters);
        logger.info("TttttTttttttttttttt::::::"+ execution.getStatus().getBatchStatus());
      ;

    }

    public Job getJob(String jobBeanId) {
        return applicationContext.getBean(jobBeanId, Job.class);
    }
}
