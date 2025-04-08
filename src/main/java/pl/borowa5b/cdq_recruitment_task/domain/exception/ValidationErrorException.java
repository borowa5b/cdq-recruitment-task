package pl.borowa5b.cdq_recruitment_task.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationError;

@Getter
@AllArgsConstructor
public class ValidationErrorException extends RuntimeException {

    private final ValidationError error;
}
