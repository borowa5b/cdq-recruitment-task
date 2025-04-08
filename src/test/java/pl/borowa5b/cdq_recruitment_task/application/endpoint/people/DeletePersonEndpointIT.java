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
class DeletePersonEndpointIT {

    @Autowired
    private DeletePersonEndpoint endpoint;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void shouldDeletePerson() {
        // given
        final var name = "Krzysztof";
        final var surname = "Sokołowski";
        final var birthDate = LocalDate.parse("1987-05-16");
        final var company = "Nocny Kochanek";
        final var personId = "PRN12321321321";
        personRepository.save(new Person(new PersonId(personId), name, surname, birthDate, company));

        // when
        endpoint.deletePerson(personId);

        // then
        final var deletedPerson = personRepository.findBy(new PersonId(personId));
        assertThat(deletedPerson).isNull();
    }
}