package pl.borowa5b.cdq_recruitment_task.application.request.filter;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.AggregatingValidationExceptionHandler;

import static org.assertj.core.api.Assertions.assertThat;

class GetTasksFilterTest {

    @Test
    void shouldValidateFilter() {
        // given
        final var filter = new GetTasksFilter("TSK12321321321", "CREATED");
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        filter.validate(exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isFalse();
    }

    @Test
    void shouldNotValidateFilter() {
        // given
        final var filter = new GetTasksFilter("DSA12321321321", "BLABLA");
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        filter.validate(exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isTrue();
    }

    @Test
    void shouldConvertToQuery() {
        // given
        final var filter = new GetTasksFilter("TSK12321321321", "CREATED");

        // when
        final var result = filter.toQuery();

        // then
        assertThat(result.taskId().value()).isEqualTo("TSK12321321321");
        assertThat(result.stage().name()).isEqualTo("CREATED");
    }
}