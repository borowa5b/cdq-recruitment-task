package pl.borowa5b.cdq_recruitment_task.domain.repository.read.tasks;

import pl.borowa5b.cdq_recruitment_task.domain.vo.Classification;

public record TaskResultDetails(String id,
                                String valueBefore,
                                String currentValue,
                                Double dissimilarity,
                                Classification classification) {
}
