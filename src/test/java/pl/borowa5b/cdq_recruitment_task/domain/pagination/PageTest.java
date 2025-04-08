package pl.borowa5b.cdq_recruitment_task.domain.pagination;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.AggregatingValidationExceptionHandler;

import static org.assertj.core.api.Assertions.assertThat;

class PageTest {

    @Test
    void shouldGetOrderAsString() {
        // given
        final var page = new Page(1, 10, new Sort.Order(Sort.Direction.ASC, "name"));
        final var expectedStringOrder = "NAME ASC";

        // when
        final var result = page.getOrderAsString();

        // then
        assertThat(result).isEqualTo(expectedStringOrder);
    }

    @Test
    void shouldValidatePage() {
        // given
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        Page.validate(10, 10, exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isFalse();
    }

    @Test
    void shouldNotValidatePage() {
        // given
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        Page.validate(-1, -1, exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isTrue();
    }
}