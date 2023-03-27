package com.uob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BatchApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(BatchApplication.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("MyJob")
    private Job job;
    @Autowired
    @Qualifier("second")
    private Job job2;

    @Override
    public void run(String... jobNames) throws Exception {
        // Execute the first job
/*        logger.info("TttttTttttttttttttt::::::"+args[0]);
        JobParameters jobParameters1 = new JobParametersBuilder()
                .addString("jobName", args[0])
                .toJobParameters();
        JobExecution jobExecution1 = jobLauncher.run(job, jobParameters1);
        System.out.println(jobExecution1.getStatus());*/
        for (String jobName : jobNames) {
            Job job = getJob(jobName);
            JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
            jobLauncher.run(job, jobParameters);
        }

    /*    // Execute the second job
        JobParameters jobParameters2 = new JobParametersBuilder()
                .addString("jobName", job2.getName())
                .toJobParameters();
        JobExecution jobExecution2 = jobLauncher.run(job2, jobParameters2);
        System.out.println(jobExecution2.getStatus());*/

        //System.exit(0);
    }

    private Job getJob(String jobName) {
        switch (jobName) {
            case "MyJob":
                return job;
            case "second":
                return job2;
            default:
                throw new IllegalArgumentException("No such job: " + jobName);
        }
    }
}
