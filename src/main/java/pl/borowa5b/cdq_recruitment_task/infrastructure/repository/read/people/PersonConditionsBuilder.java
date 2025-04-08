package pl.borowa5b.cdq_recruitment_task.infrastructure.repository.read.people;

import org.jooq.Condition;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.people.PersonQuery;

import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.noCondition;

public class PersonConditionsBuilder {

    public static Condition build(final PersonQuery query) {
        final var conditions = new ArrayList<Condition>();

        if (query.personId() != null) {
            conditions.add(field(PersonTableDefinition.Column.ID).eq(query.personId().value()));
        }

        if (query.name() != null) {
            conditions.add(field(PersonTableDefinition.Column.NAME).eq(query.name()));
        }

        if (query.surname() != null) {
            conditions.add(field(PersonTableDefinition.Column.SURNAME).eq(query.surname()));
        }

        if (query.birthDate() != null) {
            conditions.add(field(PersonTableDefinition.Column.BIRTH_DATE).eq(query.birthDate()));
        }

        if (query.company() != null) {
            conditions.add(field(PersonTableDefinition.Column.COMPANY).eq(query.company()));
        }

        return foldConditions(conditions);
    }

    private static Condition foldConditions(List<Condition> conditions) {
        var result = noCondition();
        for (final var condition : conditions) {
            result = condition.and(result);
        }
        return result;
    }
}
