package ru.fivestarter.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.fivestarter.user.batch.UserBatchConfiguration;
import ru.fivestarter.user.dao.User;
import ru.fivestarter.user.dao.UserRepository;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/batch/users")
public class UserController {

    Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;
    private final Job job;
    private final JobLauncher jobLauncher;

    @Autowired
    public UserController(UserRepository userRepository, Job job, JobLauncher jobLauncher) {
        this.userRepository = userRepository;
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

    @GetMapping
    public ResponseEntity<UserJson> getUser() throws org.springframework.batch.core.JobExecutionException {
        Map<String, JobParameter> parameters = new HashMap<>();
        parameters.put("jobId", new JobParameter(1L, true));
        JobParameters jobParameters = new JobParameters(parameters);
        JobExecution execution = jobLauncher.run(job, jobParameters);
        LOG.info("Job Status : " + execution.getStatus());
        LOG.info("Job completed");

        UserJson user = new UserJson();
        user.setFirstName("Mick");
        user.setLastName("Jagger");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserJson> postUser(@RequestBody UserJson user) {
        User savedUser = userRepository.save(user.toUser());
        return new ResponseEntity<>(new UserJson(savedUser), HttpStatus.OK);
    }
}
