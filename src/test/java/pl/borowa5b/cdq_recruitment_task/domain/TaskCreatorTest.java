package pl.borowa5b.cdq_recruitment_task.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.borowa5b.cdq_recruitment_task.domain.generator.TaskIdGenerator;
import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.repository.TaskRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskCreatorTest {

    @Mock
    private TaskIdGenerator taskIdGenerator;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskCreator taskCreator;

    @Test
    void shouldCreateTask() {
        // given
        final var taskId = new TaskId("TSK123123123");
        when(taskIdGenerator.generate()).thenReturn(taskId);
        when(taskRepository.save(any(Task.class))).thenReturn(new Task(taskId));

        // when
        final var result = taskCreator.create();

        // then
        assertThat(result).isEqualTo(taskId);

        verify(taskIdGenerator).generate();
        verify(taskRepository).save(any(Task.class));
        verifyNoMoreInteractions(taskIdGenerator, taskRepository);
    }
}