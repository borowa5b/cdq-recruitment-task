package pl.borowa5b.cdq_recruitment_task.application.endpoint.people;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.borowa5b.cdq_recruitment_task.domain.PersonRemover;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

@PeopleEndpoint
@AllArgsConstructor
class DeletePersonEndpoint {

    private final PersonRemover personRemover;

    @Operation(summary = "Deletes existing person")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Person deleted", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content())
    })
    @DeleteMapping(value = "/{personId}")
    ResponseEntity<?> deletePerson(@Parameter(
            description = "Person identifier",
            example = "PRN23423432232"
    ) @PathVariable final String personId) {
        personRemover.remove(new PersonId(personId));
        return ResponseEntity.noContent().build();
    }
}
