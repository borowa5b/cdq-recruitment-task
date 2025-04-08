package pl.borowa5b.cdq_recruitment_task.domain.exception.validation;

import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationErrorException;

public class ThrowingValidationExceptionHandler implements ValidationExceptionHandler {

    @Override
    public void handle(final ValidationErrorException exception) {
        throw exception;
    }
}
