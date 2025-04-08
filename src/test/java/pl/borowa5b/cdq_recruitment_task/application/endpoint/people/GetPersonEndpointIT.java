package pl.borowa5b.cdq_recruitment_task.application.endpoint.people;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.repository.PersonRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;
import pl.borowa5b.cdq_recruitment_task.helper.IntegrationTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class GetPersonEndpointIT {

    @Autowired
    private GetPersonEndpoint endpoint;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void shouldGetPerson() {
        // given
        final var name = "Krzysztof";
        final var surname = "Sokołowski";
        final var birthDate = LocalDate.parse("1987-05-16");
        final var company = "Nocny Kochanek";
        final var personId = "PRN12321321321";
        personRepository.save(new Person(new PersonId(personId), name, surname, birthDate, company));

        // when
        final var result = endpoint.getPerson(personId);

        // then
        assertThat(result.id()).isEqualTo(personId);
        assertThat(result.name()).isEqualTo(name);
        assertThat(result.surname()).isEqualTo(surname);
        assertThat(result.birthDate()).isEqualTo(birthDate);
        assertThat(result.company()).isEqualTo(company);
    }
}