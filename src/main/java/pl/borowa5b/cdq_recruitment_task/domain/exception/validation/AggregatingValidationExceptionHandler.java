package pl.borowa5b.cdq_recruitment_task.domain.exception.validation;

import lombok.Getter;
import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationErrorException;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AggregatingValidationExceptionHandler implements ValidationExceptionHandler {

    private final List<ValidationError> errors = new ArrayList<>();

    @Override
    public void handle(final ValidationErrorException exception) {
        errors.add(exception.getError());
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
