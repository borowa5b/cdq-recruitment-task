package pl.borowa5b.cdq_recruitment_task.application.request;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.AggregatingValidationExceptionHandler;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class AddPersonRequestTest {

    @Test
    void shouldValidateRequest() {
        // given
        final var request = new AddPersonRequest("Krzysztof", "Sokołowski", "1987-05-16", "Nocny Kochanek");
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        request.validate(exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isFalse();
    }

    @Test
    void shouldNotValidateRequest() {
        // given
        final var request = new AddPersonRequest("", "", "", "");
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        // when
        request.validate(exceptionHandler);

        // then
        assertThat(exceptionHandler.hasErrors()).isTrue();
    }

    @Test
    void shouldConvertToCommand() {
        // given
        final var name = "Krzysztof";
        final var surname = "Sokołowski";
        final var birthDate = "1987-05-16";
        final var company = "Nocny Kochanek";
        final var request = new AddPersonRequest(name, surname, birthDate, company);

        // when
        final var command = request.toCommand();

        // then
        assertThat(command.name()).isEqualTo(name);
        assertThat(command.surname()).isEqualTo(surname);
        assertThat(command.birthDate()).isEqualTo(LocalDate.parse(birthDate));
        assertThat(command.company()).isEqualTo(company);
    }
}