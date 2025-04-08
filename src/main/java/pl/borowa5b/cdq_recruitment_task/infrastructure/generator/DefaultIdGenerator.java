package pl.borowa5b.cdq_recruitment_task.infrastructure.generator;

import io.viascom.nanoid.NanoId;
import org.springframework.stereotype.Component;
import pl.borowa5b.cdq_recruitment_task.domain.generator.IdGenerator;

@Component
public class DefaultIdGenerator implements IdGenerator {

    private static final int ID_LENGTH = 21;
    private static final String ID_ALPHABET = "0123456789";

    public String generate(final String prefix) {
        return prefix + NanoId.generate(ID_LENGTH, ID_ALPHABET);
    }
}
