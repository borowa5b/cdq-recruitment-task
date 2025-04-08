package pl.borowa5b.cdq_recruitment_task.infrastructure.repository.read.people;

import org.junit.jupiter.api.Test;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.people.PersonQuery;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jooq.impl.DSL.field;

class PersonConditionsBuilderTest {

    @Test
    void shouldBuildPersonIdCondition() {
        // given
        final var query = new PersonQuery(new PersonId("PRN2325433534"), null, null, null, null);
        final var expectedCondition = field(PersonTableDefinition.Column.ID).eq(query.personId().value());

        // when
        final var result = PersonConditionsBuilder.build(query);

        // then
        assertThat(result).isEqualTo(expectedCondition);
    }

    @Test
    void shouldBuildNameCondition() {
        // given
        final var query = new PersonQuery(null, "John", null, null, null);
        final var expectedCondition = field(PersonTableDefinition.Column.NAME).eq(query.name());

        // when
        final var result = PersonConditionsBuilder.build(query);

        // then
        assertThat(result).isEqualTo(expectedCondition);
    }

    @Test
    void shouldBuildSurnameCondition() {
        // given
        final var query = new PersonQuery(null, null, "Goe", null, null);
        final var expectedCondition = field(PersonTableDefinition.Column.SURNAME).eq(query.surname());

        // when
        final var result = PersonConditionsBuilder.build(query);

        // then
        assertThat(result).isEqualTo(expectedCondition);
    }

    @Test
    void shouldBuildBirthDateCondition() {
        // given
        final var query = new PersonQuery(null, null, null, LocalDate.parse("1990-01-01"), null);
        final var expectedCondition = field(PersonTableDefinition.Column.BIRTH_DATE).eq(query.birthDate());

        // when
        final var result = PersonConditionsBuilder.build(query);

        // then
        assertThat(result).isEqualTo(expectedCondition);
    }

    @Test
    void shouldBuildCompanyCondition() {
        // given
        final var query = new PersonQuery(null, null, null, null, "Company");
        final var expectedCondition = field(PersonTableDefinition.Column.COMPANY).eq(query.company());

        // when
        final var result = PersonConditionsBuilder.build(query);

        // then
        assertThat(result).isEqualTo(expectedCondition);
    }
}