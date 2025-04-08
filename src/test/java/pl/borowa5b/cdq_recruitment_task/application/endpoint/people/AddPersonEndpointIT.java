package pl.borowa5b.cdq_recruitment_task.application.endpoint.people;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import pl.borowa5b.cdq_recruitment_task.application.request.AddPersonRequest;
import pl.borowa5b.cdq_recruitment_task.domain.repository.PersonRepository;
import pl.borowa5b.cdq_recruitment_task.domain.repository.TaskRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.helper.IntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class AddPersonEndpointIT {

    @Autowired
    private AddPersonEndpoint endpoint;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldAddPerson() {
        // given
        final var name = "Krzysztof";
        final var surname = "Sokołowski";
        final var birthDate = "1987-05-16";
        final var company = "Nocny Kochanek";
        final var request = new AddPersonRequest(name, surname, birthDate, company);

        // when
        final var result = endpoint.addPerson(request);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        final var body = result.getBody();
        final var personId = body.personId();
        final var taskId = body.taskId();
        assertThat(personId).isNotNull();
        assertThat(taskId).isNotNull();

        final var createdPerson = personRepository.findBy(new PersonId(personId));
        assertThat(createdPerson.getName()).isEqualTo(name);
        assertThat(createdPerson.getSurname()).isEqualTo(surname);
        assertThat(createdPerson.getBirthDate().toString()).isEqualTo(birthDate);
        assertThat(createdPerson.getCompany()).isEqualTo(company);

        final var createdTask = taskRepository.findBy(new TaskId(taskId));
        assertThat(createdTask).isNotNull();
    }
}