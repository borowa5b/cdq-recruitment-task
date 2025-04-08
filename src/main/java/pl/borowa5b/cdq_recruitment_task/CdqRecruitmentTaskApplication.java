package pl.borowa5b.cdq_recruitment_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CdqRecruitmentTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdqRecruitmentTaskApplication.class, args);
    }
}
