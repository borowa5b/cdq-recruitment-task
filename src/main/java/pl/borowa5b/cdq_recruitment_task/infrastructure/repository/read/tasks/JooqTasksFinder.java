package pl.borowa5b.cdq_recruitment_task.infrastructure.repository.read.tasks;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.SQLDataType;
import org.springframework.stereotype.Repository;
import pl.borowa5b.cdq_recruitment_task.domain.exception.TaskNotFoundException;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Page;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Pageable;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TaskDetails;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TaskQuery;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TaskResultDetails;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TasksFinder;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Classification;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.field;

@Repository
@AllArgsConstructor
public class JooqTasksFinder implements TasksFinder {

    private final DSLContext dslContext;

    @Override
    public Pageable<TaskDetails> findBy(final TaskQuery query, final Page page) {
        final var conditions = TaskConditionsBuilder.build(query);
        final var data = dslContext
                .select(fields())
                .from(TaskTableDefinition.TABLE_NAME)
                .leftJoin(TaskResultTableDefinition.TABLE_NAME)
                .on(field(TaskResultTableDefinition.Column.TASK_ID).eq(field(TaskTableDefinition.Column.ID)))
                .where(conditions)
                .orderBy(field(TaskTableDefinition.TABLE_NAME + "." + page.getOrderAsString()))
                .limit((page.number() - 1) * page.size(), page.size() + 1)
                .fetch()
                .stream()
                .collect(resolveJoinedTaskWithResultsCollector())
                .entrySet()
                .stream()
                .map(entry -> {
                    final var task = entry.getKey();
                    final var results = entry.getValue();
                    return new TaskDetails(task.id(), task.stage(), task.progress(), task.creationDate(), results);
                })
                .toList();
        return Pageable.of(data, page);
    }

    @Override
    public TaskDetails findBy(final TaskId taskId) {
        final var fetchedRecords = dslContext
                .select(fields())
                .from(TaskTableDefinition.TABLE_NAME)
                .leftJoin(TaskResultTableDefinition.TABLE_NAME)
                .on(field(TaskResultTableDefinition.Column.TASK_ID).eq(field(TaskTableDefinition.Column.ID)))
                .where(field(TaskTableDefinition.Column.ID).eq(taskId.value()))
                .fetch();
        if (fetchedRecords.isEmpty()) {
            throw new TaskNotFoundException(taskId);
        }
        final var taskRecord = fetchedRecords.getFirst();
        return new TaskDetails(
                taskRecord.get(TaskTableDefinition.Column.ID, String.class),
                Stage.valueOf(taskRecord.get(TaskTableDefinition.Column.STAGE, String.class)),
                taskRecord.get(TaskTableDefinition.Column.PROGRESS, Integer.class),
                taskRecord.get(TaskTableDefinition.Column.CREATION_DATE, OffsetDateTime.class),
                fetchedRecords.stream()
                        .filter(taskResultRecord -> taskResultRecord.get(TaskResultTableDefinition.Column.ID, String.class) != null)
                        .map(taskResultRecord -> new TaskResultDetails(
                                taskResultRecord.get(TaskResultTableDefinition.Column.ID, String.class),
                                taskResultRecord.get(TaskResultTableDefinition.Column.VALUE_BEFORE, String.class),
                                taskResultRecord.get(TaskResultTableDefinition.Column.CURRENT_VALUE, String.class),
                                taskResultRecord.get(TaskResultTableDefinition.Column.DISSIMILARITY, Double.class),
                                Classification.valueOf(taskResultRecord.get(TaskResultTableDefinition.Column.CLASSIFICATION, String.class))
                        )).toList()
        );
    }

    private List<Field<?>> fields() {
        return List.of(
                field(TaskTableDefinition.Column.ID, SQLDataType.VARCHAR),
                field(TaskTableDefinition.Column.STAGE, SQLDataType.VARCHAR),
                field(TaskTableDefinition.Column.PROGRESS, SQLDataType.INTEGER),
                field(TaskTableDefinition.Column.CREATION_DATE, SQLDataType.OFFSETDATETIME),
                field(TaskResultTableDefinition.Column.ID, SQLDataType.VARCHAR),
                field(TaskResultTableDefinition.Column.VALUE_BEFORE, SQLDataType.VARCHAR),
                field(TaskResultTableDefinition.Column.CURRENT_VALUE, SQLDataType.VARCHAR),
                field(TaskResultTableDefinition.Column.DISSIMILARITY, SQLDataType.DOUBLE),
                field(TaskResultTableDefinition.Column.CLASSIFICATION, SQLDataType.VARCHAR)
        );
    }

    private static Collector<Record, ?, LinkedHashMap<TaskDetails, List<TaskResultDetails>>> resolveJoinedTaskWithResultsCollector() {
        return Collectors.groupingBy(
                record -> new TaskDetails(
                        record.get(TaskTableDefinition.Column.ID, String.class),
                        Stage.valueOf(record.get(TaskTableDefinition.Column.STAGE, String.class)),
                        record.get(TaskTableDefinition.Column.PROGRESS, Integer.class),
                        record.get(TaskTableDefinition.Column.CREATION_DATE, OffsetDateTime.class),
                        new ArrayList<>()
                ),
                LinkedHashMap::new,
                Collectors.mapping(record -> {
                    if (record.get(TaskResultTableDefinition.Column.ID) != null) {
                        return new TaskResultDetails(
                                record.get(TaskResultTableDefinition.Column.ID, String.class),
                                record.get(TaskResultTableDefinition.Column.VALUE_BEFORE, String.class),
                                record.get(TaskResultTableDefinition.Column.CURRENT_VALUE, String.class),
                                record.get(TaskResultTableDefinition.Column.DISSIMILARITY, Double.class),
                                Classification.valueOf(record.get(TaskResultTableDefinition.Column.CLASSIFICATION, String.class))
                        );
                    }
                    return null;
                }, Collectors.toList())
        );
    }
}
