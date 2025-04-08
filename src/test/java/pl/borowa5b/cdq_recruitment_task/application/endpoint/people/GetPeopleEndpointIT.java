package pl.borowa5b.cdq_recruitment_task.application.endpoint.people;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.borowa5b.cdq_recruitment_task.application.request.filter.GetPeopleFilter;
import pl.borowa5b.cdq_recruitment_task.application.request.filter.PageFilter;
import pl.borowa5b.cdq_recruitment_task.application.response.PersonResponse;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.repository.PersonRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;
import pl.borowa5b.cdq_recruitment_task.helper.IntegrationTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class GetPeopleEndpointIT {

    @Autowired
    private GetPeopleEndpoint endpoint;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void shouldGetPeople() {
        // given
        final var name = "Krzysztof";
        final var surname = "Sokołowski";
        final var birthDate = LocalDate.parse("1987-05-16");
        final var company = "Nocny Kochanek";
        final var personId = "PRN12321321321";
        final var personId2 = "PRN12621321321";
        personRepository.save(new Person(new PersonId(personId), name, surname, birthDate, company));
        personRepository.save(new Person(new PersonId(personId2), name, surname, birthDate, company));
        final var filter = new GetPeopleFilter(null, null, null, null, null);
        final var pageFilter = new PageFilter();

        // when
        final var result = endpoint.getPeople(filter, pageFilter);

        // then
        final var data = result.data();
        assertThat(data).hasSize(2);
        assertThat(data.stream().map(PersonResponse::id).toList()).contains(personId, personId2);

        final var pagination = result.pagination();
        assertThat(pagination.hasNext()).isFalse();
        assertThat(pagination.hasPrevious()).isFalse();
        assertThat(pagination.number()).isEqualTo(1);
        assertThat(pagination.size()).isEqualTo(100);
    }
}
