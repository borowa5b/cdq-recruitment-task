package pl.borowa5b.cdq_recruitment_task.application.request.filter;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.AggregatingValidationExceptionHandler;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.OrderParser;

import static org.assertj.core.api.Assertions.assertThat;

class PageFilterTest {

    @Test
    void shouldValidateFilter() {
        // given
        final var filter = new PageFilter();
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        filter.validate(exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isFalse();
    }

    @Test
    void shouldNotValidateFilter() {
        // given
        final var filter = new PageFilter();
        filter.setPageSize(-1);
        filter.setPageNumber(-1);
        filter.setOrder("bla");
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        filter.validate(exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isTrue();
    }

    @Test
    void shouldConvertToPage() {
        // given
        final var filter = new PageFilter();
        filter.setPageSize(10);
        filter.setPageNumber(1);
        filter.setOrder("creationDate,ASC");

        // when
        final var result = filter.toPage();

        // then
        assertThat(result.number()).isEqualTo(1);
        assertThat(result.size()).isEqualTo(10);
        assertThat(result.order()).isEqualTo(OrderParser.parse("creationDate,ASC"));
    }
}