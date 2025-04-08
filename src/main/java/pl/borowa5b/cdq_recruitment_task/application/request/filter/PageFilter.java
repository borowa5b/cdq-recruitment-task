package pl.borowa5b.cdq_recruitment_task.application.request.filter;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;
import pl.borowa5b.cdq_recruitment_task.domain.exception.validation.ValidationExceptionHandler;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.OrderParser;
import pl.borowa5b.cdq_recruitment_task.domain.pagination.Page;

@Data
@ParameterObject
public class PageFilter {

    @Parameter(description = "Page number (default: 1)", example = "1")
    private Integer pageNumber = 1;

    @Parameter(description = "Page size (default: 100)", example = "10")
    private Integer pageSize = 100;

    @Parameter(description = "Page order (default: creationDate,DESC)", example = "creationDate,DESC")
    private String order = "creationDate,DESC";

    public void validate(final ValidationExceptionHandler exceptionHandler) {
        Page.validate(pageNumber, pageSize, exceptionHandler);
        if (order != null) {
            OrderParser.validate(order, "order", exceptionHandler);
        }
    }

    public Page toPage() {
        return new Page(pageNumber, pageSize, OrderParser.parse(order));
    }
}
