package pl.borowa5b.cdq_recruitment_task.domain.vo;

import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationErrorException;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ThrowingValidationExceptionHandler;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationError;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationExceptionHandler;

public record TaskId(String value) {

    public static final String PREFIX = "TSK";

    public TaskId {
        validate(value);
    }

    public static void validate(final String value) {
        validate(value, "taskId", new ThrowingValidationExceptionHandler());
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