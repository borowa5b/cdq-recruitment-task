package pl.borowa5b.cdq_recruitment_task.helper;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.borowa5b.cdq_recruitment_task.CdqRecruitmentTaskApplication;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@ActiveProfiles("test")
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(IntegrationTestLifecycleManager.class)
@SpringBootTest(classes = CdqRecruitmentTaskApplication.class)
public @interface IntegrationTest {
}
