package pl.borowa5b.cdq_recruitment_task.application.response;

import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;

public record TaskStatusResponse(Stage stage, Integer progress) {

    public static TaskStatusResponse of(final Stage stage, final Integer progress) {
        return new TaskStatusResponse(stage, progress);
    }
}
