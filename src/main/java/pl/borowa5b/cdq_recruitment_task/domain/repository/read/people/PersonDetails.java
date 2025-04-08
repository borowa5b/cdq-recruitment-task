package pl.borowa5b.cdq_recruitment_task.domain.repository.read.people;

import java.time.LocalDate;

public record PersonDetails(String id, String name, String surname, LocalDate birthDate, String company) {
}
