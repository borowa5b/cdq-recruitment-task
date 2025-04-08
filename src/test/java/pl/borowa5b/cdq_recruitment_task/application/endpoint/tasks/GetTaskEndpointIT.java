package pl.borowa5b.cdq_recruitment_task.application.endpoint.tasks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.repository.TaskRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.helper.IntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class GetTaskEndpointIT {

    @Autowired
    private GetTaskEndpoint endpoint;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldGetTask() {
        // given
        final var taskId = "TSK1242343232";
        taskRepository.save(new Task(new TaskId(taskId)));

        // when
        final var result = endpoint.getTask(taskId);

        // then
        final var taskStatus = result.status();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(taskStatus.progress()).isEqualTo(0);
        assertThat(taskStatus.stage()).isEqualTo(Stage.CREATED);
        assertThat(result.creationDate()).isNotNull();
    }
}