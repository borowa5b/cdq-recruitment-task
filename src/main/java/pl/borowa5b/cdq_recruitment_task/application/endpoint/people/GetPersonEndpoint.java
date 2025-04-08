package pl.borowa5b.cdq_recruitment_task.application.endpoint.people;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.borowa5b.cdq_recruitment_task.application.response.PersonResponse;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.people.PeopleFinder;
import pl.borowa5b.cdq_recruitment_task.domain.vo.PersonId;

@PeopleEndpoint
@AllArgsConstructor
public class GetPersonEndpoint {

    private final PeopleFinder finder;

    @Operation(summary = "Get person")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Person fetched", content = @Content(schema = @Schema(implementation = PersonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Request validation failed", content = @Content()),
    })
    @GetMapping(value = "/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    PersonResponse getPerson(
            @Parameter(description = "Person identifier", example = "PRN12432532")
            @PathVariable final String personId
    ) {
        final var personDetails = finder.findBy(new PersonId(personId));
        return PersonResponse.fromDetails(personDetails);
    }
}
