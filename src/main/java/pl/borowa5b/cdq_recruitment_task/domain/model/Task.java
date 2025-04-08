package pl.borowa5b.cdq_recruitment_task.domain.model;

import lombok.Getter;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Stage;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskResult;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Task {

    private final TaskId id;
    private final TaskStatus status;
    private final List<TaskResult> results;

    public Task(final TaskId id) {
        this.id = id;
        this.status = new TaskStatus(Stage.CREATED, 0);
        this.results = new ArrayList<>();
    }

    public Task(final TaskId id, final TaskStatus status, final List<TaskResult> results) {
        this.id = id;
        this.status = status;
        this.results = results;
    }

    public void setProgress(final int progress) {
        this.status.setProgress(progress);
    }

    public Stage getStage() {
        return this.status.getStage();
    }

    public int getProgress() {
        return this.status.getProgress();
    }

    public void addResult(final TaskResult taskResult) {
        this.results.add(taskResult);
    }
}
