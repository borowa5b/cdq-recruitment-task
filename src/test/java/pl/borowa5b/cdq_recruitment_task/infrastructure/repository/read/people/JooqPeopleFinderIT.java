package pl.borowa5b.cdq_recruitment_task.infrastructure.repository.read.people;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Page;
import pl.borowa5b.cdq_recruitment_task.domain.repository.PersonRepository;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.people.PersonDetails;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.people.PersonQuery;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;
import pl.borowa5b.cdq_recruitment_task.helper.IntegrationTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class JooqPeopleFinderIT {

    @Autowired
    private JooqPeopleFinder jooqPeopleFinder;

    @Autowired
    private PersonRepository personRepository;

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
        personRepository.save(person);
        personRepository.save(person2);
        personRepository.save(person3);
        final var query = new PersonQuery(null, "John", null, null, null);
        final var page = new Page(1, 10, new Sort.Order(Sort.Direction.ASC, "creationDate"));

        // when
        final var result = jooqPeopleFinder.findBy(query, page);

        // then
        final var data = result.data();
        assertThat(data).hasSize(2);
        assertThat(data.stream().map(PersonDetails::id).toList()).contains(person.getId().value(), person2.getId().value());

        final var pagination = result.pagination();
        assertThat(pagination.size()).isEqualTo(page.size());
        assertThat(pagination.number()).isEqualTo(page.number());
        assertThat(pagination.hasPrevious()).isFalse();
        assertThat(pagination.hasNext()).isFalse();
    }
}