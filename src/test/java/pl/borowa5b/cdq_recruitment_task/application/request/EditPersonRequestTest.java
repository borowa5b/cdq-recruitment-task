package pl.borowa5b.cdq_recruitment_task.application.request;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class EditPersonRequestTest {

    @Test
    void shouldConvertToCommand() {
        // given
        final var name = "Krzysztof";
        final var surname = "Sokołowski";
        final var birthDate = "1987-05-16";
        final var company = "Nocny Kochanek";
        final var request = new EditPersonRequest(name, surname, birthDate, company, true, true, true, true);

        // when
        final var command = request.toCommand(new PersonId("PRN12321321321"));

        // then
        final var nameUpdate = command.nameUpdate();
        assertThat(nameUpdate.first()).isTrue();
        assertThat(nameUpdate.second()).isEqualTo(name);
        final var surnameUpdate = command.surnameUpdate();
        assertThat(surnameUpdate.first()).isTrue();
        assertThat(surnameUpdate.second()).isEqualTo(surname);
        final var birthDateUpdate = command.birthDateUpdate();
        assertThat(birthDateUpdate.first()).isTrue();
        assertThat(birthDateUpdate.second()).isEqualTo(LocalDate.parse(birthDate));
        final var companyUpdate = command.companyUpdate();
        assertThat(companyUpdate.first()).isTrue();
        assertThat(companyUpdate.second()).isEqualTo(company);
    }
}