package pl.borowa5b.cdq_recruitment_task.domain.vo;

public record TaskResult(TaskResultId id,
                         TaskId taskId,
                         String fieldName,
                         String valueBefore,
                         String currentValue,
                         Double dissimilarity,
                         Classification classification) {
}
