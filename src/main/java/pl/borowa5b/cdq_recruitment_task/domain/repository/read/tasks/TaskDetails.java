package pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks;

import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;

import java.time.OffsetDateTime;
import java.util.List;

public record TaskDetails(String id,
                          Stage stage,
                          int progress,
                          OffsetDateTime creationDate,
                          List<TaskResultDetails> results) {
}
