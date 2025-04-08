package pl.borowa5b.cdq_recruitment_task.domain.model;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.command.EditPersonCommand;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Pair;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PersonTest {

    @Test
    void shouldEditPerson() {
        // given
        final var newName = "Andrew";
        final var personId = new PersonId("PRN242423324432");
        final var person = new Person(
                personId,
                "John",
                "Doe",
                LocalDate.parse("1990-01-01"),
                "Company"
        );
        final var editCommand = new EditPersonCommand(
                personId,
                new Pair<>(true, newName),
                new Pair<>(false, null),
                new Pair<>(false, null),
                new Pair<>(false, null)
        );

        // when
        final var result = person.edit(editCommand);

        // then
        assertThat(result).isTrue();
        assertThat(person.getName()).isEqualTo(newName);
    }

    @Test
    void shouldClone() {
        // given
        final var person = new Person(
                new PersonId("PRN242423324432"),
                "John",
                "Doe",
                LocalDate.parse("1990-01-01"),
                "Company"
        );

        // when
        final var result = person.clone();

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(person);
    }
}