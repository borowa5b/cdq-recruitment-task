package pl.borowa5b.cdq_recruitment_task.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.borowa5b.cdq_recruitment_task.domain.command.EditPersonCommand;
import pl.borowa5b.cdq_recruitment_task.domain.exception.PersonNotFoundException;
import pl.borowa5b.cdq_recruitment_task.domain.repository.PersonRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

@Component
@AllArgsConstructor
public class PersonEditor {

    private final PersonRepository repository;
    private final TaskCreator taskCreator;
    private final TaskProcessor taskProcessor;

    @Transactional
    public TaskId edit(final EditPersonCommand command) {
        final var personId = command.personId();
        if (!repository.existsBy(personId)) {
            throw new PersonNotFoundException(personId);
        }

        final var currentPerson = repository.findBy(personId);
        final var personBefore = currentPerson.clone();
        if (!currentPerson.edit(command)) {
            return null;
        }

        repository.update(currentPerson);
        final var taskId = taskCreator.create();
        taskProcessor.process(taskId, personBefore, currentPerson);
        return taskId;
    }
}
