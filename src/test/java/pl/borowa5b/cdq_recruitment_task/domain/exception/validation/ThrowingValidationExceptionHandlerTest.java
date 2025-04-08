package pl.borowa5b.cdq_recruitment_task.domain.exception.validation;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ThrowingValidationExceptionHandlerTest {

    @Test
    void shouldHandleException() {
        // given
        final var exceptionHandler = new ThrowingValidationExceptionHandler();
        final var exception = new ValidationErrorException(new ValidationError(
                "Field is null or blank",
                "Field name cannot be null or blank",
                "name"));

        // when
        final var result = catchThrowable(() -> exceptionHandler.handle(exception));

        // then
        assertThat(result).isExactlyInstanceOf(exception.getClass());
    }
}