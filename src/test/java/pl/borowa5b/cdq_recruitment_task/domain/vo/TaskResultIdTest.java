package pl.borowa5b.cdq_recruitment_task.domain.vo;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.AggregatingValidationExceptionHandler;

import static org.assertj.core.api.Assertions.assertThat;

class TaskResultIdTest {

    @Test
    void shouldValidateId() {
        // given
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        TaskResultId.validate("TKR123124", "taskResultId", exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isFalse();
    }

    @Test
    void shouldNotValidateId() {
        // given
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        TaskResultId.validate("PPPD242423", "taskResultId", exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isTrue();
    }
}