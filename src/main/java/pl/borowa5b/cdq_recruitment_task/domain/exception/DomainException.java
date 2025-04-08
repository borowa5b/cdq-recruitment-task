package pl.borowa5b.cdq_recruitment_task.domain.exception;

import lombok.Getter;
import org.zalando.problem.StatusType;

@Getter
public class DomainException extends RuntimeException {

    private final StatusType status;
    private final String message;

    public DomainException(final StatusType status, final String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
