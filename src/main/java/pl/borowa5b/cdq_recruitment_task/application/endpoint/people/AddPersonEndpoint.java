package pl.borowa5b.cdq_recruitment_task.application.endpoint.people;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.borowa5b.cdq_recruitment_task.application.request.AddPersonRequest;
import pl.borowa5b.cdq_recruitment_task.application.response.AddPersonResponse;
import pl.borowa5b.cdq_recruitment_task.domain.PersonAdder;
import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationException;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.AggregatingValidationExceptionHandler;

@PeopleEndpoint
@AllArgsConstructor
class AddPersonEndpoint {

    private final PersonAdder personAdder;

    @Operation(summary = "Adds new person and returns task id processing this person.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Person added", content = @Content(schema = @Schema(implementation = AddPersonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Request validation failed", content = @Content())
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AddPersonResponse> addPerson(@RequestBody final AddPersonRequest request) {
        validate(request);
        final var ids = personAdder.add(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AddPersonResponse(ids.first().value(), ids.second().value()));
    }

    private void validate(final AddPersonRequest request) {
        final var exceptionHandler = new AggregatingValidationExceptionHandler();
        request.validate(exceptionHandler);

        if (exceptionHandler.hasErrors()) {
            throw new ValidationException(exceptionHandler.getErrors());
        }
    }
}
