package pl.borowa5b.cdq_recruitment_task.domain.pagination;

import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationErrorException;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ThrowingValidationExceptionHandler;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationError;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationExceptionHandler;

import java.util.List;

public record Pagination(Integer number, Integer size, Boolean hasNext, Boolean hasPrevious) {

    public Pagination {
        validate(number, size, new ThrowingValidationExceptionHandler());
    }

    public static Pagination of(final List<?> data, final Page page) {
        return new Pagination(page.number(), page.size(), data.size() > page.size(), page.number() > 1);
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
}
