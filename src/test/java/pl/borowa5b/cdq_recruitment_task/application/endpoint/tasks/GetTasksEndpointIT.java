package pl.borowa5b.cdq_recruitment_task.application.endpoint.tasks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.borowa5b.cdq_recruitment_task.application.request.filter.GetTasksFilter;
import pl.borowa5b.cdq_recruitment_task.application.request.filter.PageFilter;
import pl.borowa5b.cdq_recruitment_task.application.response.TaskResponse;
import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.repository.TaskRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.helper.IntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class GetTasksEndpointIT {

    @Autowired
    private GetTasksEndpoint endpoint;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldGetPeople() {
        // given
        final var taskId = "TSK123";
        final var taskId2 = "TSK456";
        taskRepository.save(new Task(new TaskId(taskId)));
        taskRepository.save(new Task(new TaskId(taskId2)));
        final var filter = new GetTasksFilter(null, null);
        final var pageFilter = new PageFilter();

        // when
        final var result = endpoint.getTasks(filter, pageFilter);

        // then
        final var data = result.data();
        assertThat(data).hasSize(2);
        assertThat(data.stream().map(TaskResponse::id).toList()).contains(taskId, taskId2);

        final var pagination = result.pagination();
        assertThat(pagination.hasNext()).isFalse();
        assertThat(pagination.hasPrevious()).isFalse();
        assertThat(pagination.number()).isEqualTo(1);
        assertThat(pagination.size()).isEqualTo(100);
    }
}