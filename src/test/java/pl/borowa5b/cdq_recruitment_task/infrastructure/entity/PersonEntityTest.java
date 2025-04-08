package pl.borowa5b.cdq_recruitment_task.infrastructure.entity;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PersonEntityTest {

    @Test
    void shouldConvertFromDomain() {
        // given
        final var person = new Person(
                new PersonId("PRN242423324432"),
                "John",
                "Doe",
                LocalDate.parse("1990-01-01"),
                "Company"
        );

        // when
        final var result = PersonEntity.fromDomain(person);

        // then
        assertThat(result).isExactlyInstanceOf(PersonEntity.class);
        assertThat(result.getId()).isEqualTo(person.getId().value());
        assertThat(result.getName()).isEqualTo(person.getName());
        assertThat(result.getSurname()).isEqualTo(person.getSurname());
        assertThat(result.getBirthDate()).isEqualTo(person.getBirthDate());
        assertThat(result.getCompany()).isEqualTo(person.getCompany());
    }

    @Test
    void shouldConvertToDomain() {
        // given
        final var entity = PersonEntity.fromDomain(new Person(
                new PersonId("PRN242423324432"),
                "John",
                "Doe",
                LocalDate.parse("1990-01-01"),
                "Company"
        ));

        // when
        final var result = entity.toDomain();

        // then
        assertThat(result).isExactlyInstanceOf(Person.class);
        assertThat(result.getId().value()).isEqualTo(entity.getId());
        assertThat(result.getName()).isEqualTo(entity.getName());
        assertThat(result.getSurname()).isEqualTo(entity.getSurname());
        assertThat(result.getBirthDate()).isEqualTo(entity.getBirthDate());
        assertThat(result.getCompany()).isEqualTo(entity.getCompany());
    }
}