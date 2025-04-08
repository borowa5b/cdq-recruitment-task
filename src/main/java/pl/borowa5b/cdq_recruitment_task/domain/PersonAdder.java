package pl.borowa5b.cdq_recruitment_task.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.borowa5b.cdq_recruitment_task.domain.command.AddPersonCommand;
import pl.borowa5b.cdq_recruitment_task.domain.generator.PersonIdGenerator;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.repository.PersonRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Pair;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;
import pl.borowa5b.cdq_recruitment_task.domain.vo.TaskId;

@Component
@AllArgsConstructor
public class PersonAdder {

    private final PersonIdGenerator personIdGenerator;
    private final PersonRepository repository;
    private final TaskCreator taskCreator;
    private final TaskProcessor taskProcessor;

    @Transactional
    public Pair<PersonId, TaskId> add(final AddPersonCommand command) {
        final var personId = personIdGenerator.generate();
        final var person = new Person(
                personId,
                command.name(),
                command.surname(),
                command.birthDate(),
                command.company()
        );
        repository.save(person);
        final var taskId = taskCreator.create();
        taskProcessor.process(taskId, null, person);
        return new Pair<>(person.getId(), taskId);
    }
}
