package pl.borowa5b.cdq_recruitment_task.domain.vo;

import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationErrorException;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ThrowingValidationExceptionHandler;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationError;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationExceptionHandler;

public record PersonId(String value) {

    public static final String PREFIX = "PRN";

    public PersonId {
        validate(value);
    }

    public static void validate(final String value) {
        validate(value, "id", new ThrowingValidationExceptionHandler());
    }

    public static void validate(final String value,
                                final String fieldName,
                                final ValidationExceptionHandler exceptionHandler) {
        if (!value.startsWith(PREFIX)) {
            exceptionHandler.handle(new ValidationErrorException(new ValidationError(
                    "Field has invalid value",
                    "Field `" + fieldName + "` must start with `" + PREFIX + "`",
                    fieldName
            )));
        }
    }
}