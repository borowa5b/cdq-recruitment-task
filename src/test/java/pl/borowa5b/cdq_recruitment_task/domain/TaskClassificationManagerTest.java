package pl.borowa5b.cdq_recruitment_task.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.borowa5b.cdq_recruitment_task.domain.generator.TaskResultIdGenerator;
import pl.borowa5b.cdq_recruitment_task.domain.model.Task;
import pl.borowa5b.cdq_recruitment_task.domain.repository.TaskRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Classification;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Pair;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskResultId;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskClassificationManagerTest {

    @Mock
    private TaskClassificator taskClassificator;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskResultIdGenerator taskResultIdGenerator;

    @InjectMocks
    private TaskClassificationManager taskClassificationManager;

    @Test
    void shouldBeginClassification() {
        // given
        final var taskId = new TaskId("TSK123124");
        final var fieldName = "fieldName";
        final var valueBefore = "John";
        final var currentValue = "John Doe";
        when(taskRepository.findBy(taskId)).thenReturn(new Task(taskId));
        when(taskClassificator.classificate(valueBefore, currentValue)).thenReturn(new Pair<>(0.5, Classification.MEDIUM));
        when(taskResultIdGenerator.generate()).thenReturn(new TaskResultId("TKR1232421"));

        // when
        taskClassificationManager.beginClassification(taskId, fieldName, valueBefore, currentValue);

        // then
        verify(taskClassificator).classificate(valueBefore, currentValue);
        verify(taskRepository).findBy(taskId);
        verify(taskResultIdGenerator).generate();
        verify(taskRepository).update(any(Task.class));
        verifyNoMoreInteractions(taskClassificator, taskRepository, taskResultIdGenerator);
    }
}