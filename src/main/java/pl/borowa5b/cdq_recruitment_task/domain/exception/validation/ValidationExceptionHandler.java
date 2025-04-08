package pl.borowa5b.cdq_recruitment_task.domain.exception.validation;

import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationErrorException;

public interface ValidationExceptionHandler {

    void handle(final ValidationErrorException exception);
}
