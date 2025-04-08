package pl.borowa5b.cdq_recruitment_task.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationError;

import java.util.List;

@Getter
@AllArgsConstructor
public class ValidationException extends RuntimeException {

    private final List<ValidationError> errors;
}
