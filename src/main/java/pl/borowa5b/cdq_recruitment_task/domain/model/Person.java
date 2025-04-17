package pl.borowa5b.cdq_recruitment_task.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.borowa5b.cdq_recruitment_task.domain.command.EditPersonCommand;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class Person implements Cloneable {

    public static final String NAME_FIELD = "name";
    public static final String SURNAME_FIELD = "surname";
    public static final String BIRTH_DATE_FIELD = "birthDate";
    public static final String COMPANY_FIELD = "company";

    private PersonId id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String company;

    public boolean edit(final EditPersonCommand command) {
        final var edited = new ArrayList<Boolean>();

        final var nameUpdate = command.nameUpdate();
        if (nameUpdate.first()) {
            edited.add(update(name, nameUpdate.second(), () -> name = nameUpdate.second()));
        }

        final var surnameUpdate = command.surnameUpdate();
        if (surnameUpdate.first()) {
            edited.add(update(surname, surnameUpdate.second(), () -> surname = surnameUpdate.second()));
        }

        final var birthDateUpdate = command.birthDateUpdate();
        if (birthDateUpdate.first()) {
            edited.add(update(birthDate, birthDateUpdate.second(), () -> birthDate = birthDateUpdate.second()));
        }

        final var companyUpdate = command.companyUpdate();
        if (companyUpdate.first()) {
            edited.add(update(company, companyUpdate.second(), () -> company = companyUpdate.second()));
        }

        return edited.stream().anyMatch(booleanValue -> booleanValue);
    }

    private boolean update(final Object oldValue, final Object newValue, final Runnable updateAction) {
        if (oldValue == null || !oldValue.equals(newValue)) {
            updateAction.run();
            return true;
        }
        return false;
    }

    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
