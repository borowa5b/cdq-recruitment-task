package pl.borowa5b.cdq_recruitment_task.infrastructure.repository.read;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pl.borowa5b.cdq_recruitment_task.domain.exception.PersonNotFoundException;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Page;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Pageable;
import pl.borowa5b.cdq_recruitment_task.domain.repository.PersonRepository;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.people.PeopleFinder;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.people.PersonQuery;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

@Component
@AllArgsConstructor
public class DefaultPeopleFinder implements PeopleFinder {

    private final PersonRepository personRepository;

    @Override
    public Pageable<Person> findBy(final PersonQuery query, final Page page) {
        final var pageable = PageRequest.of(
                page.number() - 1,
                page.size(),
                page.order().getDirection(),
                page.order().getProperty()
        );
        final var data = personRepository.findAll(query.toPerson(), pageable);
        return Pageable.of(data, page);
    }

    @Override
    public Person findBy(final PersonId personId) {
        final var person = personRepository.findBy(personId);
        if (person == null) {
            throw new PersonNotFoundException(personId);
        }
        return person;
    }
}
