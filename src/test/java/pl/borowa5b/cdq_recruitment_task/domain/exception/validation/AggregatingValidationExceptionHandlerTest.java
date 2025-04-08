package pl.borowa5b.cdq_recruitment_task.domain.exception.validation;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationErrorException;

import static org.assertj.core.api.Assertions.assertThat;

class AggregatingValidationExceptionHandlerTest {

    @Test
    void shouldHandleException() {
        // given
        final var exceptionHandler = new AggregatingValidationExceptionHandler();
        final var exception = new ValidationErrorException(new ValidationError(
                "Field is null or blank",
                "Field name cannot be null or blank",
                "name"));


        // when
        exceptionHandler.handle(exception);

        // then
        assertThat(exceptionHandler.hasErrors()).isTrue();
        assertThat(exceptionHandler.getErrors()).contains(exception.getError());
    }
}