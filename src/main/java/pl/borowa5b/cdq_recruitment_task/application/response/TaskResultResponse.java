package pl.borowa5b.cdq_recruitment_task.application.response;

import pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks.TaskResultDetails;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Classification;

public record TaskResultResponse(String id,
                                 String valueBefore,
                                 String currentValue,
                                 Double dissimilarity,
                                 Classification classification) {

    public static TaskResultResponse fromDetails(final TaskResultDetails taskResult) {
        if (taskResult == null) {
            return null;
        }

        return new TaskResultResponse(taskResult.id(),
                taskResult.valueBefore(),
                taskResult.currentValue(),
                taskResult.dissimilarity(),
                taskResult.classification());
    }
}
