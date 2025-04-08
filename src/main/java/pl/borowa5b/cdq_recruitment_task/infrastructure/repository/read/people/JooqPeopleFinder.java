package pl.borowa5b.cdq_recruitment_task.infrastructure.repository.read.people;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.SQLDataType;
import org.springframework.stereotype.Repository;
import pl.borowa5b.cdq_recruitment_task.domain.exception.PersonNotFoundException;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Page;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Pageable;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.people.PeopleFinder;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.people.PersonDetails;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.people.PersonQuery;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

import java.util.List;

import static org.jooq.impl.DSL.field;

@Repository
@AllArgsConstructor
public class JooqPeopleFinder implements PeopleFinder {

    private final DSLContext dslContext;

    @Override
    public Pageable<PersonDetails> findBy(final PersonQuery query, final Page page) {
        final var conditions = PersonConditionsBuilder.build(query);
        final var data = dslContext
                .select(fields())
                .from(PersonTableDefinition.TABLE_NAME)
                .where(conditions)
                .orderBy(field(page.getOrderAsString()))
                .limit((page.number() - 1) * page.size(), page.size() + 1)
                .fetch()
                .into(PersonDetails.class);
        return Pageable.of(data, page);
    }

    @Override
    public PersonDetails findBy(final PersonId personId) {
        final var record = dslContext
                .select(fields())
                .from(PersonTableDefinition.TABLE_NAME)
                .where(field(PersonTableDefinition.Column.ID).eq(personId.value()))
                .fetchOne();
        if (record == null) {
            throw new PersonNotFoundException(personId);
        }
        return record.into(PersonDetails.class);
    }

    private List<Field<?>> fields() {
        return List.of(
                field(PersonTableDefinition.Column.ID, SQLDataType.VARCHAR),
                field(PersonTableDefinition.Column.NAME, SQLDataType.VARCHAR),
                field(PersonTableDefinition.Column.SURNAME, SQLDataType.VARCHAR),
                field(PersonTableDefinition.Column.BIRTH_DATE, SQLDataType.LOCALDATE),
                field(PersonTableDefinition.Column.COMPANY, SQLDataType.VARCHAR)
        );
    }
}
