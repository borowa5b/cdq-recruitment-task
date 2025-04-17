package pl.borowa5b.cdq_recruitment_task.application.response;

import pl.borowa5b.cdq_recruitment_task.domain.vo.Classification;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskResult;

public record TaskResultResponse(String id,
                                 String fieldName,
                                 String valueBefore,
                                 String currentValue,
                                 Double dissimilarity,
                                 Classification classification) {

    public static TaskResultResponse fromDomain(final TaskResult taskResult) {
        if (taskResult == null) {
            return null;
        }

        return new TaskResultResponse(taskResult.id().value(),
                taskResult.fieldName(),
                taskResult.valueBefore(),
                taskResult.currentValue(),
                taskResult.dissimilarity(),
                taskResult.classification());
    }
}
