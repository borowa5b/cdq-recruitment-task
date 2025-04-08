package pl.borowa5b.cdq_recruitment_task.domain.command;

import java.time.LocalDate;

public record AddPersonCommand(String name, String surname, LocalDate birthDate, String company) {
}
