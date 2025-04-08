package pl.borowa5b.cdq_recruitment_task.domain.exception.validation;

import pl.borowa5b.cdq_recruitment_task.domain.exception.ValidationErrorException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class Validator {

    public static void isNotNullOrBlank(final String value,
                                        final String fieldName,
                                        final ValidationExceptionHandler exceptionHandler) {
        if (value == null || value.isBlank()) {
            exceptionHandler.handle(new ValidationErrorException(new ValidationError(
                    "Field is null or blank",
                    "Field " + fieldName + " cannot be null or blank",
                    fieldName
            )));
        }
    }

    public static void isNoLongerThan(final String value,
                                      final int noLongerThan,
                                      final String fieldName,
                                      final ValidationExceptionHandler exceptionHandler) {
        if (value.length() > noLongerThan) {
            exceptionHandler.handle(new ValidationErrorException(new ValidationError(
                    "Field is longer than " + noLongerThan,
                    "Field " + fieldName + " cannot be longer than " + noLongerThan + " characters",
                    fieldName
            )));
        }
    }

    public static void isValidLocalDate(final String value,
                                        final String fieldName,
                                        final ValidationExceptionHandler exceptionHandler) {
        try {
            LocalDate.parse(value);
        } catch (final DateTimeParseException exception) {
            exceptionHandler.handle(new ValidationErrorException(new ValidationError(
                    "Field has invalid value",
                    "Field " + fieldName + " is not correctly formatted local date",
                    fieldName
            )));
        }
    }

    public static <E extends Enum<E>> void isValidEnumValue(final Class<E> enumClass,
                                                            final String value,
                                                            final String fieldName,
                                                            final ValidationExceptionHandler validationExceptionHandler) {

        final var enumEntries = enumClass.getEnumConstants();
        final var enumValue = Arrays.stream(enumEntries)
                .filter(e -> e.name().equals(value.toUpperCase()))
                .findFirst()
                .orElse(null);

        if (enumValue == null) {
            final var allowedValues = Arrays.stream(enumEntries)
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));

            validationExceptionHandler.handle(
                    new ValidationErrorException(
                            new ValidationError(
                                    "Field has invalid value",
                                    "Field " + fieldName + " has invalid value. Allowed value " + allowedValues,
                                    fieldName
                            )
                    )
            );
        }
    }
}
