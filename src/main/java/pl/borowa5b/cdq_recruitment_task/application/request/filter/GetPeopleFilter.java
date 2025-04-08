package pl.borowa5b.cdq_recruitment_task.application.request.filter;

import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationExceptionHandler;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.Validator;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.people.PersonQuery;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

import java.time.LocalDate;

@ParameterObject
public record GetPeopleFilter(

        @Parameter(description = "Person identifier", example = "PRN1214242443242")
        String personId,

        @Parameter(description = "Person name", example = "John")
        String name,

        @Parameter(description = "Person surname", example = "Doe")
        String surname,

        @Parameter(description = "Person birth date", example = "2022-01-01")
        String birthDate,

        @Parameter(description = "Person company", example = "CDQ")
        String company
) {
    public void validate(final ValidationExceptionHandler exceptionHandler) {
        if (personId != null) {
            PersonId.validate(personId, "personId", exceptionHandler);
        }

        if (birthDate != null) {
            Validator.isValidLocalDate(birthDate, "birthDate", exceptionHandler);
        }
    }

    public PersonQuery toQuery() {
        return new PersonQuery(
                personId != null
                        ? new PersonId(personId)
                        : null,
                name,
                surname,
                birthDate != null
                        ? LocalDate.parse(birthDate)
                        : null,
                company);
    }
}
