package pl.borowa5b.cdq_recruitment_task.domain.repository;

import org.springframework.data.domain.Pageable;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

import java.util.List;

public interface PersonRepository {

    Person findBy(final PersonId personId);

    List<Person> findAll(final Person probe, final Pageable pageable);

    void save(final Person person);

    void update(final Person person);

    void remove(final PersonId personId);

    boolean existsBy(PersonId personId);
}
