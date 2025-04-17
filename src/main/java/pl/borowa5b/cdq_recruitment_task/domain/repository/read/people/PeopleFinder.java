package pl.borowa5b.cdq_recruitment_task.domain.repository.read.people;

import org.springframework.cache.annotation.Cacheable;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Page;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Pageable;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

public interface PeopleFinder {

    @Cacheable(cacheNames = "people")
    Pageable<Person> findBy(final PersonQuery query, final Page page);


    @Cacheable(cacheNames = "people")
    Person findBy(final PersonId personId);
}
