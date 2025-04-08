package pl.borowa5b.cdq_recruitment_task.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.borowa5b.cdq_recruitment_task.domain.command.AddPersonCommand;
import pl.borowa5b.cdq_recruitment_task.domain.generator.PersonIdGenerator;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.repository.PersonRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonAdderTest {

    @Mock
    private PersonIdGenerator personIdGenerator;

    @Mock
    private PersonRepository repository;

    @Mock
    private TaskCreator taskCreator;

    @Mock
    private TaskProcessor taskProcessor;

    @InjectMocks
    private PersonAdder personAdder;

    @Test
    void shouldAddPerson() {
        // given
        final var command = new AddPersonCommand(
                "John",
                "Doe",
                LocalDate.parse("1990-01-01"),
                "company"
        );
        final var personId = new PersonId("PRN123123123");
        final var taskId = new TaskId("TSK234234234");
        when(personIdGenerator.generate()).thenReturn(personId);
        when(taskCreator.create()).thenReturn(taskId);

        // when
        final var result = personAdder.add(command);

        // then
        assertThat(result.first()).isEqualTo(personId);
        assertThat(result.second()).isEqualTo(taskId);

        verify(repository).save(any(Person.class));
        verify(personIdGenerator).generate();
        verify(taskCreator).create();
        verify(taskProcessor).process(eq(taskId), eq(null), any(Person.class));
        verifyNoMoreInteractions(personIdGenerator, repository, taskCreator, taskProcessor);
    }
}