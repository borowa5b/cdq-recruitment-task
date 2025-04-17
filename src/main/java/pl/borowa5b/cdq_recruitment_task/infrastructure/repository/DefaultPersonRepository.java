package pl.borowa5b.cdq_recruitment_task.infrastructure.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pl.borowa5b.cdq_recruitment_task.domain.exception.PersonNotFoundException;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.repository.PersonRepository;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;
import pl.borowa5b.cdq_recruitment_task.infrastructure.entity.PersonEntity;

import java.util.List;

@Component
@AllArgsConstructor
public class DefaultPersonRepository implements PersonRepository {

    private final SpringMongoPersonRepository repository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Person findBy(final PersonId personId) {
        return repository.findById(personId.value()).map(PersonEntity::toDomain).orElse(null);
    }

    @Override
    public List<Person> findAll(final Person probe, final Pageable pageable) {
        final var matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase();
        final var example = Example.of(PersonEntity.fromDomain(probe), matcher);
        final var query = new Query()
                .addCriteria(new Criteria().alike(example))
                .with(pageable)
                .limit(pageable.getPageSize() + 1);
        return mongoTemplate.find(query, PersonEntity.class).stream().map(PersonEntity::toDomain).toList();
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
interface SpringMongoPersonRepository extends MongoRepository<PersonEntity, String> {

}