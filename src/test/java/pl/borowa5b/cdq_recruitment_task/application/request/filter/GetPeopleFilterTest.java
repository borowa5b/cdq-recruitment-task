package pl.borowa5b.cdq_recruitment_task.application.request.filter;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.AggregatingValidationExceptionHandler;

import static org.assertj.core.api.Assertions.assertThat;

class GetPeopleFilterTest {

    @Test
    void shouldValidateFilter() {
        // given
        final var filter = new GetPeopleFilter("PRN12321321321", "Krzysztof", "Sokołowski", "1987-05-16", "Nocny Kochanek");
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        filter.validate(exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isFalse();
    }

    @Test
    void shouldNotValidateFilter() {
        // given
        final var filter = new GetPeopleFilter("PPP234324", "Krzysztof", "Sokołowski", "1987-05-d16", "Nocny Kochanek");
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        filter.validate(exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isTrue();
    }

    @Test
    void shouldConvertToQuery() {
        // given
        final var filter = new GetPeopleFilter("PRN12321321321", "Krzysztof", "Sokołowski", "1987-05-16", "Nocny Kochanek");

        // when
        final var result = filter.toQuery();

        // then
        assertThat(result.personId().value()).isEqualTo("PRN12321321321");
        assertThat(result.name()).isEqualTo("Krzysztof");
        assertThat(result.surname()).isEqualTo("Sokołowski");
        assertThat(result.birthDate()).isEqualTo("1987-05-16");
        assertThat(result.company()).isEqualTo("Nocny Kochanek");
    }
}