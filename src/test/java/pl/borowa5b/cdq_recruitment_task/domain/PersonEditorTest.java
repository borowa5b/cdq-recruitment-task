package pl.borowa5b.cdq_recruitment_task.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.borowa5b.cdq_recruitment_task.domain.command.EditPersonCommand;
import pl.borowa5b.cdq_recruitment_task.domain.exception.PersonNotFoundException;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.repository.PersonRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Pair;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonEditorTest {

    @Mock
    private PersonRepository repository;

    @Mock
    private TaskCreator taskCreator;

    @Mock
    private TaskProcessor taskProcessor;

    @InjectMocks
    private PersonEditor personEditor;

    @Test
    void shouldThrowExceptionWhenPersonNotExists() {
        // given
        final var personId = new PersonId("PRN123123123");
        final var command = new EditPersonCommand(
                personId,
                new Pair<>(true, "John"),
                new Pair<>(false, null),
                new Pair<>(false, null),
                new Pair<>(false, null)
        );

        // when
        final var result = catchThrowable(() -> personEditor.edit(command));

        // then
        assertThat(result).isExactlyInstanceOf(PersonNotFoundException.class);
    }

    @Test
    void shouldEditPerson() {
        // given
        final var personId = new PersonId("PRN123123123");
        final var taskId = new TaskId("TSK234234234");
        final var person = new Person(
                personId,
                "John",
                "Doe",
                LocalDate.parse("1990-01-01"),
                "Company"
        );
        final var command = new EditPersonCommand(
                personId,
                new Pair<>(true, "Andrew"),
                new Pair<>(false, null),
                new Pair<>(false, null),
                new Pair<>(false, null)
        );
        when(repository.existsBy(personId)).thenReturn(true);
        when(repository.findBy(personId)).thenReturn(person);
        when(taskCreator.create()).thenReturn(taskId);

        // when
        final var result = personEditor.edit(command);

        // then
        assertThat(result).isEqualTo(taskId);

        verify(repository).existsBy(personId);
        verify(repository).findBy(personId);
        verify(repository).update(person);
        verify(taskCreator).create();
        verify(taskProcessor).process(eq(taskId), any(Person.class), any(Person.class));
        verifyNoMoreInteractions(repository, taskCreator, taskProcessor);
    }

    @Test
    void shouldNotEditPerson() {
        // given
        final var personId = new PersonId("PRN123123123");
        final var person = new Person(
                personId,
                "John",
                "Doe",
                LocalDate.parse("1990-01-01"),
                "Company"
        );
        final var command = new EditPersonCommand(
                personId,
                new Pair<>(false, null),
                new Pair<>(false, null),
                new Pair<>(false, null),
                new Pair<>(false, null)
        );
        when(repository.existsBy(personId)).thenReturn(true);
        when(repository.findBy(personId)).thenReturn(person);

        // when
        final var result = personEditor.edit(command);

        // then
        assertThat(result).isNull();

        verify(repository).existsBy(personId);
        verify(repository).findBy(personId);
        verifyNoMoreInteractions(repository, taskCreator, taskProcessor);
    }
}