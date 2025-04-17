package pl.borowa5b.cdq_recruitment_task.infrastructure.repository.read;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import pl.borowa5b.cdq_recruitment_task.domain.exception.PersonNotFoundException;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Page;
import pl.borowa5b.cdq_recruitment_task.domain.repository.PersonRepository;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.people.PersonQuery;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;
import pl.borowa5b.cdq_recruitment_task.helper.IntegrationTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@IntegrationTest
class DefaultPeopleFinderIT {

    @Autowired
    private DefaultPeopleFinder finder;

    @Autowired
    private PersonRepository repository;

    @Test
    void shouldFindPeople() {
        // given
        final var person = new Person(
                new PersonId("PRN2325433534"),
                "John",
                "Goe",
                LocalDate.parse("1990-01-01"),
                "Company"
        );
        final var person2 = new Person(
                new PersonId("PRN23254332423"),
                "John",
                "Doe",
                LocalDate.parse("1990-01-01"),
                "Company"
        );
        final var person3 = new Person(
                new PersonId("PRN23254332723"),
                "Joe",
                "Doe",
                LocalDate.parse("1990-01-01"),
                "Company"
        );
        repository.save(person);
        repository.save(person2);
        repository.save(person3);
        final var query = new PersonQuery(null, "John", null, null, null);
        final var page = new Page(1, 10, new Sort.Order(Sort.Direction.ASC, "creationDate"));

        // when
        final var result = finder.findBy(query, page);

        // then
        final var data = result.data();
        assertThat(data).hasSize(2);
        assertThat(data.stream().map(Person::getId).toList()).contains(person.getId(), person2.getId());

        final var pagination = result.pagination();
        assertThat(pagination.size()).isEqualTo(page.size());
        assertThat(pagination.number()).isEqualTo(page.number());
        assertThat(pagination.hasPrevious()).isFalse();
        assertThat(pagination.hasNext()).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenPersonNotFound() {
        // given
        final var personId = new PersonId("PRN2325433534");

        // when
        final var result = catchThrowable(() -> finder.findBy(personId));

        // then
        assertThat(result).isExactlyInstanceOf(PersonNotFoundException.class);
    }

    @Test
    void shouldFindPersonById() {
        // given
        final var personId = new PersonId("PRN2325433534");
        final var person = new Person(
                personId,
                "John",
                "Goe",
                LocalDate.parse("1990-01-01"),
                "Company"
        );
        repository.save(person);

        // when
        final var result = finder.findBy(personId);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(person);
    }
}