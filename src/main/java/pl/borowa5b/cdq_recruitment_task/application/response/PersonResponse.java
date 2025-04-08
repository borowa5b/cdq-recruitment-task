package pl.borowa5b.cdq_recruitment_task.application.response;

import pl.borowa5b.cdq_recruitment_task.domain.repository.read.people.PersonDetails;

import java.time.LocalDate;

public record PersonResponse(String id, String name, String surname, LocalDate birthDate, String company) {

    public static PersonResponse fromDetails(final PersonDetails person) {
        return new PersonResponse(
                person.id(),
                person.name(),
                person.surname(),
                person.birthDate(),
                person.company()
        );
    }
}
