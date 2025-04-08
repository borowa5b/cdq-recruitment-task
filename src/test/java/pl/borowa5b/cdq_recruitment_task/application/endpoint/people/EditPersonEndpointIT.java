package pl.borowa5b.cdq_recruitment_task.application.endpoint.people;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import pl.borowa5b.cdq_recruitment_task.application.request.EditPersonRequest;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.repository.PersonRepository;
import pl.borowa5b.cdq_recruitment_task.domain.repository.TaskRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.helper.IntegrationTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class EditPersonEndpointIT {


    @Autowired
    private EditPersonEndpoint endpoint;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldNotEditPerson() {
        // given
        final var name = "Krzysztof";
        final var surname = "Sokołowski";
        final var birthDate = LocalDate.parse("1987-05-16");
        final var company = "Nocny Kochanek";
        final var personId = "PRN12321321321";
        personRepository.save(new Person(new PersonId(personId), name, surname, birthDate, company));
        final var request = new EditPersonRequest(name, surname, birthDate.toString(), company, false, false, false, false);

        // when
        final var result = endpoint.editPerson(personId, request);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        final var body = result.getBody();
        final var updated = body.updated();
        final var taskId = body.taskId();
        assertThat(updated).isFalse();
        assertThat(taskId).isNull();
    }

    @Test
    void shouldEditPerson() {
        // given
        final var name = "Krzysztof";
        final var newName = "Andrzej";
        final var surname = "Sokołowski";
        final var birthDate = LocalDate.parse("1987-05-16");
        final var company = "Nocny Kochanek";
        final var personId = new PersonId("PRN12321321321");
        personRepository.save(new Person(personId, name, surname, birthDate, company));
        final var request = new EditPersonRequest(newName, surname, birthDate.toString(), company, true, false, false, false);

        // when
        final var result = endpoint.editPerson(personId.value(), request);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        final var body = result.getBody();
        final var updated = body.updated();
        final var taskId = body.taskId();
        assertThat(updated).isTrue();
        assertThat(taskId).isNotNull();

        final var editedPerson = personRepository.findBy(personId);
        assertThat(editedPerson.getName()).isEqualTo(newName);
        assertThat(editedPerson.getSurname()).isEqualTo(surname);
        assertThat(editedPerson.getBirthDate()).isEqualTo(birthDate);
        assertThat(editedPerson.getCompany()).isEqualTo(company);

        final var createdTask = taskRepository.findBy(new TaskId(taskId));
        assertThat(createdTask).isNotNull();
    }
}