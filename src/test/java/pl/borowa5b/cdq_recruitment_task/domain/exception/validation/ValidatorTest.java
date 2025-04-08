package pl.borowa5b.cdq_recruitment_task.domain.exception.validation;


import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Classification;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorTest {

    @Test
    void shouldCheckIfFieldIsNullOrBlank() {
        // given
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        Validator.isNotNullOrBlank(null, "field", exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isTrue();
    }

    @Test
    void shouldCheckIfFieldIsNoLongerThan() {
        // given
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        Validator.isNoLongerThan("field", 1, "field", exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isTrue();
    }

    @Test
    void shouldCheckIfFieldIsValidLocalDate() {
        // given
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        Validator.isValidLocalDate("wrongDate", "field", exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isTrue();
    }

    @Test
    void shouldCheckIfFieldIsValidEnumValue() {
        // given
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        Validator.isValidEnumValue(Classification.class, "wrongValue", "field", exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isTrue();
    }
}