package pl.borowa5b.cdq_recruitment_task.domain;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Classification;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TaskClassificatorTest {

    @ParameterizedTest
    @CsvSource(value = {
            "ABCD, BCD, 0.25, MEDIUM",
            "ABCD, BWD, 0.5, MEDIUM",
            "ABCDEFG, CFG, 0.5714285714285714, MEDIUM",
            "ABCABC, ABC, 0.5, MEDIUM",
            "ABCDEFGH, TDD, 0.875, LOW",
            "ABCDEFGHIJKLMN, ABCDDFGHIJKLMN, 0.07142857142857142, HIGH",
            "ABCD, null, null, DELETED",
            "null, ABCD, null, ADDED"
    }, nullValues = "null")
    void shouldClassificateValues(final String valueBefore,
                                  final String currentValue,
                                  final Double expectedDissimilarity,
                                  final Classification expectedClassification) {
        // given
        final var classificator = new TaskClassificator();

        // when
        final var result = classificator.classificate(valueBefore, currentValue);

        // then
        final var dissimilarity = result.first();
        final var classification = result.second();
        assertThat(dissimilarity).isEqualTo(expectedDissimilarity);
        assertThat(classification).isEqualTo(expectedClassification);
    }
}