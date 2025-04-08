package pl.borowa5b.cdq_recruitment_task.domain.pagination;

import java.util.List;

public record Pageable<T>(List<T> data, Pagination pagination) {

    public static <T> Pageable<T> of(final List<T> data, final Page page) {
        return new Pageable<>(data.stream().limit(page.size()).toList(), Pagination.of(data, page));
    }
}
