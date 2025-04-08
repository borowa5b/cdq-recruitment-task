package pl.borowa5b.cdq_recruitment_task.application.response;

import pl.borowa5b.cdq_recruitment_task.domain.pagination.Pagination;

import java.util.List;

public record PageResponse<T>(List<T> data, Pagination pagination) {
}
