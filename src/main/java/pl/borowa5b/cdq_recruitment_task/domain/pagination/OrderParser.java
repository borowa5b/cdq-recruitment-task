package pl.borowa5b.cdq_recruitment_task.domain.pagination;

import org.springframework.data.domain.Sort;
import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationErrorException;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ThrowingValidationExceptionHandler;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationError;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationExceptionHandler;

import java.util.regex.Pattern;

public final class OrderParser {

    private static final Pattern ORDER_REGEX_PATTERN = Pattern.compile(".*,(ASC|DESC)");

    public static Sort.Order parse(final String order) {
        if (order == null) {
            return null;
        }

        validate(order, "order", new ThrowingValidationExceptionHandler());
        final var splittedOrder = order.split(",");
        return new Sort.Order(Sort.Direction.fromString(splittedOrder[1]), splittedOrder[0]);
    }

    public static void validate(final String order,
                                final String fieldName,
                                final ValidationExceptionHandler exceptionHandler) {
        if (order != null) {
            if (!ORDER_REGEX_PATTERN.matcher(order).matches()) {
                exceptionHandler.handle(new ValidationErrorException(new ValidationError(
                        "Field has invalid value",
                        "Field `" + fieldName + "` is not correctly formatted order",
                        fieldName
                )));
            }
        }
    }
}
