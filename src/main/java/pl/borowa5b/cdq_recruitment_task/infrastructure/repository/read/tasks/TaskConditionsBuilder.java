package pl.borowa5b.cdq_recruitment_task.infrastructure.repository.read.tasks;

import org.jooq.Condition;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TaskQuery;

import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.noCondition;

public class TaskConditionsBuilder {

    public static Condition build(final TaskQuery query) {
        final var conditions = new ArrayList<Condition>();

        if (query.taskId() != null) {
            conditions.add(field(TaskTableDefinition.Column.ID).eq(query.taskId().value()));
        }

        if (query.stage() != null) {
            conditions.add(field(TaskTableDefinition.Column.STAGE).eq(query.stage().name()));
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
