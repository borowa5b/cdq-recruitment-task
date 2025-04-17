package pl.borowa5b.cdq_recruitment_task.infrastructure.repository.read;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import pl.borowa5b.cdq_recruitment_task.domain.exception.TaskNotFoundException;
import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.model.TaskStatus;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Page;
import pl.borowa5b.cdq_recruitment_task.domain.repository.TaskRepository;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TaskQuery;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.helper.IntegrationTest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@IntegrationTest
class DefaultTasksFinderIT {

    @Autowired
    private DefaultTasksFinder finder;

    @Autowired
    private TaskRepository repository;

    @Test
    void shouldFindTasks() {
        // given
        final var task = new Task(
                new TaskId("TSK1423522533"),
                new TaskStatus(Stage.IN_PROGRESS, 50),
                new ArrayList<>(),
                OffsetDateTime.now(ZoneOffset.UTC)
        );
        final var task2 = new Task(
                new TaskId("TSK1423622533"),
                new TaskStatus(Stage.CREATED, 0),
                new ArrayList<>(),
                OffsetDateTime.now(ZoneOffset.UTC)
        );
        final var task3 = new Task(
                new TaskId("TSK1423527533"),
                new TaskStatus(Stage.IN_PROGRESS, 50),
                new ArrayList<>(),
                OffsetDateTime.now(ZoneOffset.UTC)
        );
        repository.save(task);
        repository.save(task2);
        repository.save(task3);
        final var query = new TaskQuery(null, Stage.IN_PROGRESS);
        final var page = new Page(1, 10, new Sort.Order(Sort.Direction.ASC, "creationDate"));

        // when
        final var result = finder.findBy(query, page);

        // then
        final var data = result.data();
        assertThat(data).hasSize(2);
        assertThat(data.stream().map(Task::getId).toList()).contains(task.getId(), task3.getId());

        final var pagination = result.pagination();
        assertThat(pagination.size()).isEqualTo(page.size());
        assertThat(pagination.number()).isEqualTo(page.number());
        assertThat(pagination.hasPrevious()).isFalse();
        assertThat(pagination.hasNext()).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        // given
        final var taskId = new TaskId("TSK1423527533");

        // when
        final var result = catchThrowable(() -> finder.findBy(taskId));

        // then
        assertThat(result).isExactlyInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void shouldFindTaskById() {
        // given
        final var taskId = new TaskId("TSK1423527533");
        final var task = new Task(
                taskId,
                new TaskStatus(Stage.IN_PROGRESS, 50),
                new ArrayList<>(),
                OffsetDateTime.now(ZoneOffset.UTC)
        );
        repository.save(task);

        // when
        final var result = finder.findBy(taskId);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("creationDate")
                .isEqualTo(task);
    }
}