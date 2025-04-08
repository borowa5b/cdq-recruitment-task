package pl.borowa5b.cdq_recruitment_task.domain.pagination;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.AggregatingValidationExceptionHandler;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class PaginationTest {

    @Test
    void shouldCreateFromDataAndPage() {
        // given
        final var data = new ArrayList<String>();
        final var page = new Page(1, 10, new Sort.Order(Sort.Direction.ASC, "name"));

        // when
        final var result = Pagination.of(data, page);

        // then
        assertThat(result).isInstanceOf(Pagination.class);
    }

    @Test
    void shouldValidatePagination() {
        // given
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        Pagination.validate(10, 10, exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isFalse();
    }

    @Test
    void shouldNotValidatePagination() {
        // given
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        Pagination.validate(-1, -1, exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isTrue();
    }
}