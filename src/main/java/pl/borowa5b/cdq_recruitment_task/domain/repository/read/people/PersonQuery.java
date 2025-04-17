package pl.borowa5b.cdq_recruitment_task.domain.repository.read.people;

import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

import java.time.LocalDate;

public record PersonQuery(PersonId personId, String name, String surname, LocalDate birthDate, String company) {

    public Person toPerson() {
        return new Person(personId, name, surname, birthDate, company);
    }
}
