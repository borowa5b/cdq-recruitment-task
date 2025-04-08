package pl.borowa5b.cdq_recruitment_task.domain.exception;

import org.zalando.problem.Status;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

public class PersonNotFoundException extends DomainException {

    public PersonNotFoundException(final PersonId personId) {
        super(Status.NOT_FOUND, "Person with id " + personId.value() + " not found");
    }
}
