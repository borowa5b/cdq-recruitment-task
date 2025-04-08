package pl.borowa5b.cdq_recruitment_task.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;

@Getter
@AllArgsConstructor
public class TaskStatus {

    private Stage stage;
    private int progress;

    public void setProgress(final int progress) {
        this.progress = progress;
        if (progress == 0) {
            this.stage = Stage.CREATED;
        } else if (progress > 0 && progress < 100) {
            this.stage = Stage.IN_PROGRESS;
        } else if (progress == 100) {
            this.stage = Stage.COMPLETED;
        }
    }
}
