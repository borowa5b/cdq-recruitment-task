package pl.borowa5b.cdq_recruitment_task.application.endpoint.people;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.borowa5b.cdq_recruitment_task.application.request.EditPersonRequest;
import pl.borowa5b.cdq_recruitment_task.application.response.EditPersonResponse;
import pl.borowa5b.cdq_recruitment_task.domain.PersonEditor;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

@PeopleEndpoint
@AllArgsConstructor
class EditPersonEndpoint {

    private final PersonEditor editor;

    @Operation(summary = "Edits existing person")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Person edited", content = @Content(schema = @Schema(implementation = EditPersonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content())
    })
    @PostMapping(value = "/{personId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EditPersonResponse> editPerson(@Parameter(
            description = "Person identifier",
            example = "PRN23423432232"
    ) @PathVariable final String personId, @RequestBody final EditPersonRequest request) {
        final var taskId = editor.edit(request.toCommand(new PersonId(personId)));
        final var valuesUpdated = taskId != null;
        if (valuesUpdated) {
            return ResponseEntity.ok(new EditPersonResponse(true, taskId.value()));
        }
        return ResponseEntity.ok(new EditPersonResponse(false, null));
    }
}
