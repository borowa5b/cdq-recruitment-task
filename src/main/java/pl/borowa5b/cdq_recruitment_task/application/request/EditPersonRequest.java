package pl.borowa5b.cdq_recruitment_task.application.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.borowa5b.cdq_recruitment_task.domain.command.EditPersonCommand;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Pair;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditPersonRequest {

    @Schema(type = "string", description = "Person name", example = "Joe")
    private String name;

    @Schema(type = "string", description = "Person surname", example = "Doe")
    private String surname;

    @Schema(type = "date", description = "Person birth date", example = "1996-04-30")
    private String birthDate;

    @Schema(type = "string", description = "Person company", example = "CDQ")
    private String company;

    @JsonIgnore
    private boolean shouldEditName = false;

    @JsonIgnore
    private boolean shouldEditSurname = false;

    @JsonIgnore
    private boolean shouldEditBirthDate = false;

    @JsonIgnore
    private boolean shouldEditCompany = false;

    @JsonSetter(nulls = Nulls.SET)
    public void setName(final String name) {
        this.name = name;
        this.shouldEditName = true;
    }

    @JsonSetter(nulls = Nulls.SET)
    public void setSurname(final String surname) {
        this.surname = surname;
        this.shouldEditSurname = true;
    }

    @JsonSetter(nulls = Nulls.SET)
    public void setBirthDate(final String birthDate) {
        this.birthDate = birthDate;
        this.shouldEditBirthDate = true;
    }

    @JsonSetter(nulls = Nulls.SET)
    public void setCompany(final String company) {
        this.company = company;
        this.shouldEditCompany = true;
    }

    public EditPersonCommand toCommand(final PersonId personId) {
        return new EditPersonCommand(personId,
                new Pair<>(shouldEditName, name),
                new Pair<>(shouldEditSurname, surname),
                new Pair<>(shouldEditBirthDate, birthDate != null ? LocalDate.parse(birthDate) : null),
                new Pair<>(shouldEditCompany, company));
    }
}
