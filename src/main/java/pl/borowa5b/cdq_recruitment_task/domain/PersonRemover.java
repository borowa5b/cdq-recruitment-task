package pl.borowa5b.cdq_recruitment_task.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.borowa5b.cdq_recruitment_task.domain.exception.PersonNotFoundException;
import pl.borowa5b.cdq_recruitment_task.domain.repository.PersonRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

@Component
@AllArgsConstructor
public class PersonRemover {

    private PersonRepository repository;

    public void remove(final PersonId personId) {
        if (!repository.existsBy(personId)) {
            throw new PersonNotFoundException(personId);
        }
        repository.remove(personId);
    }
}
