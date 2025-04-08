package pl.borowa5b.cdq_recruitment_task.application.request;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.borowa5b.cdq_recruitment_task.domain.command.AddPersonCommand;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationExceptionHandler;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.Validator;

import java.time.LocalDate;

public record AddPersonRequest(

        @Schema(type = "string", description = "Person name", example = "Joe")
        String name,

        @Schema(type = "string", description = "Person surname", example = "Doe")
        String surname,

        @Schema(type = "date", description = "Person birth date", example = "1996-04-30")
        String birthDate,

        @Schema(type = "string", description = "Person company", example = "CDQ")
        String company
) {

    public void validate(final ValidationExceptionHandler exceptionHandler) {
        Validator.isNotNullOrBlank(name, "name", exceptionHandler);
        Validator.isNotNullOrBlank(surname, "surname", exceptionHandler);
        Validator.isNotNullOrBlank(birthDate, "birthDate", exceptionHandler);
        Validator.isNotNullOrBlank(company, "company", exceptionHandler);

        Validator.isValidLocalDate(birthDate, "birthDate", exceptionHandler);
        Validator.isNoLongerThan(name, 255, "name", exceptionHandler);
        Validator.isNoLongerThan(surname, 255, "name", exceptionHandler);
        Validator.isNoLongerThan(company, 255, "name", exceptionHandler);
    }

    public AddPersonCommand toCommand() {
        return new AddPersonCommand(name, surname, LocalDate.parse(birthDate), company);
    }
}
