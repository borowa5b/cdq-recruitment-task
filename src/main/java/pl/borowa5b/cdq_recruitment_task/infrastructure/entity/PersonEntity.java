package pl.borowa5b.cdq_recruitment_task.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Getter
@Entity(name = "person")
@NoArgsConstructor(force = true)
public class PersonEntity {

    @Id
    private final String id;
    @Setter
    private String name;
    @Setter
    private String surname;
    @Setter
    private LocalDate birthDate;
    @Setter
    private String company;
    private OffsetDateTime creationDate;
    private OffsetDateTime modificationDate;
    @Version
    private Long entityVersion;

    @PrePersist
    public void prePersist() {
        creationDate = OffsetDateTime.now(ZoneOffset.UTC);
        modificationDate = OffsetDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    public void preUpdate() {
        modificationDate = OffsetDateTime.now(ZoneOffset.UTC);
    }

    private PersonEntity(final String id,
                         final String name,
                         final String surname,
                         final LocalDate birthDate,
                         final String company) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.company = company;
    }

    public Person toDomain() {
        return new Person(
                new PersonId(id),
                name,
                surname,
                birthDate,
                company
        );
    }

    public static PersonEntity fromDomain(final Person person) {
        if (person == null) {
            return null;
        }

        return new PersonEntity(
                person.getId().value(),
                person.getName(),
                person.getSurname(),
                person.getBirthDate(),
                person.getCompany()
        );
    }
}
