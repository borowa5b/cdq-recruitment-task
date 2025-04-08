package pl.borowa5b.cdq_recruitment_task.infrastructure.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pl.borowa5b.cdq_recruitment_task.domain.exception.PersonNotFoundException;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.repository.PersonRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;
import pl.borowa5b.cdq_recruitment_task.infrastructure.entity.PersonEntity;

@Component
@AllArgsConstructor
public class DefaultPersonRepository implements PersonRepository {

    private final SpringJpaPersonRepository repository;

    @Override
    public Person findBy(final PersonId personId) {
        return repository.findById(personId.value()).map(PersonEntity::toDomain).orElse(null);
    }

    @Override
    public void save(final Person person) {
        repository.save(PersonEntity.fromDomain(person));
    }

    @Override
    public void update(final Person person) {
        final var entity = repository.findById(person.getId().value()).orElseThrow(() -> new PersonNotFoundException(person.getId()));
        entity.setName(person.getName());
        entity.setSurname(person.getSurname());
        entity.setBirthDate(person.getBirthDate());
        entity.setCompany(person.getCompany());
        repository.save(entity);
    }

    @Override
    public void remove(final PersonId personId) {
        repository.deleteById(personId.value());
    }

    @Override
    public boolean existsBy(final PersonId personId) {
        return repository.existsById(personId.value());
    }
}

@Repository
interface SpringJpaPersonRepository extends JpaRepository<PersonEntity, String> {
}