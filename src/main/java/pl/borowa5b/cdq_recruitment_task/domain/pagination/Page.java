package pl.borowa5b.cdq_recruitment_task.domain.pagination;

import org.springframework.data.domain.Sort;
import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationErrorException;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ThrowingValidationExceptionHandler;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationError;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationExceptionHandler;

public record Page(Integer number, Integer size, Sort.Order order) {

    public Page {
        validate(number, size, new ThrowingValidationExceptionHandler());
    }

    public String getOrderAsString() {
        return toSnakeCase(order.getProperty()) + " " + order.getDirection().name();
    }

    public static void validate(final Integer number,
                                final Integer size,
                                final ValidationExceptionHandler exceptionHandler) {
        if (number != null) {
            if (number < 1) {
                exceptionHandler.handle(new ValidationErrorException(new ValidationError(
                        "Invalid page number",
                        "Page number must be greater than 0",
                        "number"
                )));
            }
        }

        if (size != null) {
            if (size < 1) {
                exceptionHandler.handle(new ValidationErrorException(new ValidationError(
                        "Invalid page size",
                        "Page size must be greater than 0",
                        "size"
                )));
            }
        }
    }

    private String toSnakeCase(final String input) {
        return input.replaceAll("(?<=.)(?=\\p{Upper})", "_").toUpperCase();
    }
}
