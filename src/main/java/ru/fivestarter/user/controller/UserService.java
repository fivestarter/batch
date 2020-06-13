package ru.fivestarter.user.controller;

import ru.fivestarter.user.dao.User;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final Job job;
    private final JobLauncher jobLauncher;

    @Autowired
    public UserService(Job job, JobLauncher jobLauncher) {
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

    public User process() throws org.springframework.batch.core.JobExecutionException {
        Map<String, JobParameter> parameters = new HashMap<>();
        parameters.put("jobId", new JobParameter(1L, true));
        JobParameters jobParameters = new JobParameters(parameters);
        JobExecution execution = jobLauncher.run(job, jobParameters);
        LOG.info("Job Status : " + execution.getStatus());
        LOG.info("Job completed");
        return null;
    }

}
