package pl.borowa5b.cdq_recruitment_task.infrastructure;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import pl.borowa5b.cdq_recruitment_task.domain.exception.DomainException;
import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationErrorException;
import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationException;

import java.net.URI;
import java.util.logging.Logger;

@ControllerAdvice
public class RestErrorHandler {

    private static final Logger LOGGER = Logger.getLogger(RestErrorHandler.class.getSimpleName());


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Problem> handle(final Exception exception) {
        final var status = resolveStatus(exception);
        final var message = resolveMessage(exception);
        final var problem = Problem.builder()
                .withType(URI.create("cdq-recruitment-task/unexpected-error"))
                .withTitle("Unexpected error")
                .withDetail(message)
                .withStatus(status)
                .build();
        LOGGER.warning("Unexpected error occurred: " + exception);
        return ResponseEntity.status(problem.getStatus().getStatusCode()).body(problem);
    }

    @ExceptionHandler(ValidationErrorException.class)
    public ResponseEntity<Problem> handle(final ValidationErrorException exception) {
        final var error = exception.getError();
        final var problem = Problem.builder()
                .withType(URI.create("cdq-recruitment-task/validation-error"))
                .withTitle(error.title())
                .withDetail(error.message())
                .withStatus(Status.BAD_REQUEST)
                .with("fieldName", error.fieldName())
                .build();
        return ResponseEntity.status(problem.getStatus().getStatusCode()).body(problem);
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Problem> handle(final ValidationException exception) {
        final var problem = Problem.builder()
                .withType(URI.create("cdq-recruitment-task/validation-errors"))
                .withTitle("Validation errors")
                .withDetail("Validation errors occurred")
                .withStatus(Status.BAD_REQUEST)
                .with("errors", exception.getErrors())
                .build();
        return ResponseEntity.status(problem.getStatus().getStatusCode()).body(problem);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Problem> handle(final DomainException exception) {
        final var problem = Problem.builder()
                .withType(URI.create("cdq-recruitment-task/domain-error"))
                .withTitle("Domain error")
                .withDetail(exception.getMessage())
                .withStatus(exception.getStatus())
                .build();
        LOGGER.warning("Domain error occurred: " + exception);
        return ResponseEntity.status(problem.getStatus().getStatusCode()).body(problem);
    }

    private Status resolveStatus(final Exception exception) {
        if (exception instanceof HttpMediaTypeException) {
            return Status.valueOf(((HttpMediaTypeException) exception).getBody().getStatus());
        }
        return Status.INTERNAL_SERVER_ERROR;
    }

    private String resolveMessage(final Exception exception) {
        if (exception instanceof HttpMediaTypeException) {
            return ((HttpMediaTypeException) exception).getBody().getDetail();
        }
        return exception.getMessage();
    }
}
