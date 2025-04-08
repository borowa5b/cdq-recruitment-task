package pl.borowa5b.cdq_recruitment_task.domain.command;

import pl.borowa5b.cdq_recruitment_task.domain.vo.Pair;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

import java.time.LocalDate;

public record EditPersonCommand(
        PersonId personId,
        Pair<Boolean, String> nameUpdate,
        Pair<Boolean, String> surnameUpdate,
        Pair<Boolean, LocalDate> birthDateUpdate,
        Pair<Boolean, String> companyUpdate
) {
}
