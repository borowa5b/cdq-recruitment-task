package pl.borowa5b.cdq_recruitment_task.application.response;

import pl.borowa5b.cdq_recruitment_task.domain.model.Person;

import java.time.LocalDate;

public record PersonResponse(String id, String name, String surname, LocalDate birthDate, String company) {

    public static PersonResponse fromDomain(final Person person) {
        return new PersonResponse(
                person.getId().value(),
                person.getName(),
                person.getSurname(),
                person.getBirthDate(),
                person.getCompany()
        );
    }
}
