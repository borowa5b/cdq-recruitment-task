package pl.borowa5b.cdq_recruitment_task.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskResult;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class Task {

    private final TaskId id;
    private final TaskStatus status;
    private final List<TaskResult> results;
    private final OffsetDateTime creationDate;

    public Task(final TaskId id) {
        this.id = id;
        this.status = new TaskStatus(Stage.CREATED, 0);
        this.results = new ArrayList<>();
        this.creationDate = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public void setProgress(final int progress) {
        this.status.setProgress(progress);
    }

    public Stage getStage() {
        return this.status.getStage();
    }

    public Integer getProgress() {
        return this.status.getProgress();
    }

    public void addResult(final TaskResult taskResult) {
        this.results.add(taskResult);
    }
}
