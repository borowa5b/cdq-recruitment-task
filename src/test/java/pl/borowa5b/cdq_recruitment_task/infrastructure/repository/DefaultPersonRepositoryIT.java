package pl.borowa5b.cdq_recruitment_task.infrastructure.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.borowa5b.cdq_recruitment_task.domain.command.EditPersonCommand;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Pair;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;
import pl.borowa5b.cdq_recruitment_task.helper.IntegrationTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class DefaultPersonRepositoryIT {

    @Autowired
    private DefaultPersonRepository repository;

    @Test
    void shouldFindByPersonId() {
        // given
        final var personId = new PersonId("PRN32532");
        final var person = new Person(personId, "Joe", "Doe", LocalDate.parse("2000-01-01"), "CDQ");
        repository.save(person);

        // when
        final var result = repository.findBy(personId);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(person);
    }

    @Test
    void shouldSavePerson() {
        // given
        final var personId = new PersonId("PRN32532");
        final var person = new Person(personId, "Joe", "Doe", LocalDate.parse("2000-01-01"), "CDQ");

        // when
        repository.save(person);

        // then
        assertThat(repository.existsBy(personId)).isTrue();
    }

    @Test
    void shouldUpdatePerson() {
        // given
        final var personId = new PersonId("PRN32532");
        final var person = new Person(personId, "Joe", "Doe", LocalDate.parse("2000-01-01"), "CDQ");
        repository.save(person);
        final var updateCommand = new EditPersonCommand(
                personId,
                new Pair<>(true, "John"),
                new Pair<>(false, null),
                new Pair<>(false, null),
                new Pair<>(false, null)
        );
        person.edit(updateCommand);

        // when
        repository.update(person);

        // then
        assertThat(repository.findBy(personId)).usingRecursiveComparison().isEqualTo(person);
    }

    @Test
    void shouldRemovePerson() {
        // given
        final var personId = new PersonId("PRN32532");
        final var person = new Person(personId, "Joe", "Doe", LocalDate.parse("2000-01-01"), "CDQ");
        repository.save(person);

        // when
        repository.remove(personId);

        // then
        assertThat(repository.existsBy(personId)).isFalse();
    }

    @Test
    void shouldCheckIfPersonExists() {
        // given
        final var personId = new PersonId("PRN32532");
        final var person = new Person(personId, "Joe", "Doe", LocalDate.parse("2000-01-01"), "CDQ");
        repository.save(person);

        // when
        final var result = repository.existsBy(personId);

        // then
        assertThat(result).isTrue();
    }
}
