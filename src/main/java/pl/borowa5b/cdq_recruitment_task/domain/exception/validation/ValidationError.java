package pl.borowa5b.cdq_recruitment_task.domain.exception.validation;

public record ValidationError(String title, String message, String fieldName) {

}
