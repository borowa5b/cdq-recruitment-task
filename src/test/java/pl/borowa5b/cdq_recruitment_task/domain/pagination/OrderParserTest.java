package pl.borowa5b.cdq_recruitment_task.domain.pagination;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.AggregatingValidationExceptionHandler;

import static org.assertj.core.api.Assertions.assertThat;

class OrderParserTest {

    @Test
    void shouldParseOrder() {
        // given
        final var orderParser = new OrderParser();
        final var order = "creationDate,DESC";

        // when
        final var result = OrderParser.parse(order);

        // then
        assertThat(result).isExactlyInstanceOf(Sort.Order.class);
        assertThat(result.getDirection()).isEqualTo(Sort.Direction.DESC);
        assertThat(result.getProperty()).isEqualTo("creationDate");
    }

    @Test
    void shouldValidateOrder() {
        // given
        final var orderParser = new OrderParser();
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        OrderParser.validate("wrongOrder", "order", exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isTrue();
    }
}