package pl.borowa5b.cdq_recruitment_task.application.endpoint.people;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import pl.borowa5b.cdq_recruitment_task.application.request.filter.GetPeopleFilter;
import pl.borowa5b.cdq_recruitment_task.application.request.filter.PageFilter;
import pl.borowa5b.cdq_recruitment_task.application.response.PageResponse;
import pl.borowa5b.cdq_recruitment_task.application.response.PersonResponse;
import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationException;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.AggregatingValidationExceptionHandler;
import pl.borowa5b.cdq_recruitment_task.domain.repository.read.people.PeopleFinder;

@PeopleEndpoint
@AllArgsConstructor
public class GetPeopleEndpoint {

    private final PeopleFinder finder;

    @Operation(summary = "Get all people")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "People fetched", content = @Content(schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Request validation failed", content = @Content()),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    PageResponse<PersonResponse> getPeople(final GetPeopleFilter filter, final PageFilter pageFilter) {
        validate(filter, pageFilter);
        final var peopleDetails = finder.findBy(filter.toQuery(), pageFilter.toPage());
        final var data = peopleDetails.data().stream().map(PersonResponse::fromDomain).toList();
        return new PageResponse<>(data, peopleDetails.pagination());
    }

    private void validate(final GetPeopleFilter filter, final PageFilter pageFilter) {
        final var validationExceptionHandler = new AggregatingValidationExceptionHandler();

        filter.validate(validationExceptionHandler);
        pageFilter.validate(validationExceptionHandler);

        if (validationExceptionHandler.hasErrors()) {
            throw new ValidationException(validationExceptionHandler.getErrors());
        }
    }
}
