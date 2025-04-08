package pl.borowa5b.cdq_recruitment_task.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.borowa5b.cdq_recruitment_task.domain.exception.PersonNotFoundException;
import pl.borowa5b.cdq_recruitment_task.domain.repository.PersonRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonRemoverTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonRemover personRemover;

    @Test
    void shouldRemovePerson() {
        // given
        final var personId = new PersonId("PRN123124");
        when(repository.existsBy(personId)).thenReturn(true);

        // when
        personRemover.remove(personId);

        // then
        verify(repository).remove(personId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenPersonDoesNotExist() {
        // given
        final var personId = new PersonId("PRN123124");
        when(repository.existsBy(personId)).thenReturn(false);

        // when
        final var result = catchThrowable(() -> personRemover.remove(personId));

        // then
        assertThat(result).isExactlyInstanceOf(PersonNotFoundException.class);

        verify(repository).existsBy(personId);
        verifyNoMoreInteractions(repository);
    }
}