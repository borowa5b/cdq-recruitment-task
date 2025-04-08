package pl.borowa5b.cdq_recruitment_task.domain.pagination;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class PageableTest {

    @Test
    void shouldCreateFromDataAndPage() {
        // given
        final var data = new ArrayList<String>();
        final var page = new Page(1, 10, new Sort.Order(Sort.Direction.ASC, "name"));

        // when
        final var result = Pageable.of(data, page);

        // then
        assertThat(result).isInstanceOf(Pageable.class);
    }
}