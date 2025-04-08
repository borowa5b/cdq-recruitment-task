package pl.borowa5b.cdq_recruitment_task.infrastructure.generator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.borowa5b.cdq_recruitment_task.domain.generator.IdGenerator;
import pl.borowa5b.cdq_recruitment_task.domain.generator.PersonIdGenerator;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

@Component
@AllArgsConstructor
public class DefaultPersonIdGenerator implements PersonIdGenerator {

    private final IdGenerator idGenerator;

    @Override
    public PersonId generate() {
        return new PersonId(idGenerator.generate(PersonId.PREFIX));
    }
}
