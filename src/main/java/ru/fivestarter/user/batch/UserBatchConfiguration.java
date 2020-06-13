package ru.fivestarter.user.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.fivestarter.user.controller.UserJson;

@Configuration
@EnableBatchProcessing
public class UserBatchConfiguration {
    Logger LOG = LoggerFactory.getLogger(UserBatchConfiguration.class);


    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    public UserBatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public ItemProcessor<UserJson, UserJson> processor() {
        return new UserItemProcessor();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("Hello step")
                .tasklet((contribution, chunkContext) -> {
                    LOG.info("Hello world!!!");
                    return null;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("By step")
                .tasklet((contribution, chunkContext) -> {
                    LOG.info("Bye!!!");
                    return null;
                })
                .build();
    }

    @Bean
    public Job job(Step step1, Step step2) {
        return jobBuilderFactory.get("Test job")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .next(step2)
                .next(step2)
                .build();
    }
}
