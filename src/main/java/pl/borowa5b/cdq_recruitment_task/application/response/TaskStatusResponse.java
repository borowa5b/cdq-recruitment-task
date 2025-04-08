package pl.borowa5b.cdq_recruitment_task.application.response;

import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;

public record TaskStatusResponse(Stage stage, int progress) {

    public static TaskStatusResponse of(final Stage stage, final int progress) {
        return new TaskStatusResponse(stage, progress);
    }
}
