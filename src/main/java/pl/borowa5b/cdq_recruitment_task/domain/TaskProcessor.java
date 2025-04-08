package pl.borowa5b.cdq_recruitment_task.domain;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

@Component
@AllArgsConstructor
public class TaskProcessor {

    private final TaskClassificationManager classificationManager;

    @Async
    @Transactional
    public void process(final TaskId taskId, final Person personBefore, final Person currentPerson) {
        classificationManager.beginClassification(taskId, resolveName(personBefore), resolveName(currentPerson));
        classificationManager.beginClassification(taskId, resolveSurname(personBefore), resolveSurname(currentPerson));
        classificationManager.beginClassification(taskId, resolveBirthDate(personBefore), resolveBirthDate(currentPerson));
        classificationManager.beginClassification(taskId, resolveCompany(personBefore), resolveCompany(currentPerson));
    }

    private String resolveName(final Person person) {
        return person == null ? null : person.getName();
    }

    private String resolveSurname(final Person person) {
        return person == null ? null : person.getSurname();
    }

    private String resolveBirthDate(final Person person) {
        return person == null ? null : person.getBirthDate().toString();
    }

    private String resolveCompany(final Person person) {
        return person == null ? null : person.getCompany();
    }
}
