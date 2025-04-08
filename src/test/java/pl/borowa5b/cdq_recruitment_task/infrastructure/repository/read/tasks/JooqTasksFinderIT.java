package pl.borowa5b.cdq_recruitment_task.infrastructure.repository.read.tasks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.model.TaskStatus;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Page;
import pl.borowa5b.cdq_recruitment_task.domain.repository.TaskRepository;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TaskDetails;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TaskQuery;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.helper.IntegrationTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class JooqTasksFinderIT {

    @Autowired
    private JooqTasksFinder jooqTasksFinder;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldFindTasks() {
        // given
        final var task = new Task(
                new TaskId("TSK1423522533"),
                new TaskStatus(Stage.IN_PROGRESS, 50),
                new ArrayList<>()
        );
        final var task2 = new Task(
                new TaskId("TSK1423622533"),
                new TaskStatus(Stage.CREATED, 0),
                new ArrayList<>()
        );
        final var task3 = new Task(
                new TaskId("TSK1423527533"),
                new TaskStatus(Stage.IN_PROGRESS, 50),
                new ArrayList<>()
        );
        taskRepository.save(task);
        taskRepository.save(task2);
        taskRepository.save(task3);
        final var query = new TaskQuery(null, Stage.IN_PROGRESS);
        final var page = new Page(1, 10, new Sort.Order(Sort.Direction.ASC, "creationDate"));

        // when
        final var result = jooqTasksFinder.findBy(query, page);

        // then
        final var data = result.data();
        assertThat(data).hasSize(2);
        assertThat(data.stream().map(TaskDetails::id).toList()).contains(task.getId().value(), task3.getId().value());

        final var pagination = result.pagination();
        assertThat(pagination.size()).isEqualTo(page.size());
        assertThat(pagination.number()).isEqualTo(page.number());
        assertThat(pagination.hasPrevious()).isFalse();
        assertThat(pagination.hasNext()).isFalse();
    }
}