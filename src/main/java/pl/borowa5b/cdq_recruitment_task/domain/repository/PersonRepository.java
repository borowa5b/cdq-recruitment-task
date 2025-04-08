package pl.borowa5b.cdq_recruitment_task.domain.repository;

import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

public interface PersonRepository {

    Person findBy(final PersonId personId);

    void save(final Person person);

    void update(final Person person);

    void remove(final PersonId personId);

    boolean existsBy(PersonId personId);
}
