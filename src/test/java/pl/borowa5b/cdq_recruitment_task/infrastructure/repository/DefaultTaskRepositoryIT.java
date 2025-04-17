package pl.borowa5b.cdq_recruitment_task.infrastructure.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.model.TaskStatus;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.helper.IntegrationTest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@IntegrationTest
class DefaultTaskRepositoryIT {

    @Autowired
    private DefaultTaskRepository repository;

    @Test
    void shouldFindByTaskId() {
        // given
        final var taskId = new TaskId("TSK1231331");
        final var task = new Task(taskId);
        repository.save(task);

        // when
        final var result = repository.findBy(taskId);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("creationDate")
                .isEqualTo(task);
    }

    @Test
    void shouldSaveTask() {
        // given
        final var taskId = new TaskId("TSK1231331");
        final var task = new Task(taskId);

        // when
        repository.save(task);

        // then
        assertThat(repository.findBy(taskId))
                .usingRecursiveComparison()
                .ignoringFields("creationDate")
                .isEqualTo(task);
    }

    @Test
    void shouldUpdateTask() {
        // given
        final var taskId = new TaskId("TSK1231331");
        final var task = new Task(taskId, new TaskStatus(Stage.CREATED, 0), new ArrayList<>(), OffsetDateTime.now(ZoneOffset.UTC));
        repository.save(task);
        task.setProgress(50);

        // when
        repository.update(task);

        // then
        assertThat(repository.findBy(taskId))
                .usingRecursiveComparison()
                .ignoringFields("creationDate")
                .isEqualTo(task);
    }
}