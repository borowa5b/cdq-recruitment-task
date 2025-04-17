package pl.borowa5b.cdq_recruitment_task.infrastructure.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.borowa5b.cdq_recruitment_task.domain.model.Person;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Setter
@Getter
@Document(collection = "person")
@NoArgsConstructor(force = true)
public class PersonEntity {

    @Id
    private String id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String company;
    @CreatedDate
    private OffsetDateTime creationDate;
    @LastModifiedDate
    private OffsetDateTime modificationDate;
    @Version
    private Long entityVersion;

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
                person.getId() != null ? person.getId().value() : null,
                person.getName(),
                person.getSurname(),
                person.getBirthDate(),
                person.getCompany()
        );
    }
}
