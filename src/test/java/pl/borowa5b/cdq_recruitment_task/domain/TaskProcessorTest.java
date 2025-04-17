package pl.borowa5b.cdq_recruitment_task.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static pl.borowa5b.cdq_recruitment_task.domain.model.Person.*;

@ExtendWith(MockitoExtension.class)
class TaskProcessorTest {

    @Mock
    private TaskClassificationManager classificationManager;

    @InjectMocks
    private TaskProcessor taskProcessor;

    @Test
    void shouldProcessTask() {
        // given
        final var taskId = new TaskId("TSK123123123");
        final var personId = new PersonId("PRN123123123");
        final var birthDate = LocalDate.parse("1990-01-01");
        final var personBefore = new Person(personId, "John", "Doe", birthDate, "Google");
        final var currentPerson = new Person(personId, "Andrew", "Doe", birthDate, "Google");

        // when
        taskProcessor.process(taskId, personBefore, currentPerson);

        // then
        verify(classificationManager).beginClassification(taskId, NAME_FIELD, "John", "Andrew");
        verify(classificationManager).beginClassification(taskId, SURNAME_FIELD, "Doe", "Doe");
        verify(classificationManager).beginClassification(taskId, BIRTH_DATE_FIELD, "1990-01-01", "1990-01-01");
        verify(classificationManager).beginClassification(taskId, COMPANY_FIELD, "Google", "Google");
        verifyNoMoreInteractions(classificationManager);
    }
}