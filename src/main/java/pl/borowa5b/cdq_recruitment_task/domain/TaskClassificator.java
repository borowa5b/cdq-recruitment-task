package pl.borowa5b.cdq_recruitment_task.domain;

import org.springframework.stereotype.Component;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Classification;
import pl.borowa5b.cdq_recruitment_task.domain.vo.Pair;

@Component
public class TaskClassificator {

    public Pair<Double, Classification> classificate(final String valueBefore, final String currentValue) {
        if (valueBefore == null) {
            return new Pair<>(null, Classification.ADDED);
        }
        if (currentValue == null) {
            return new Pair<>(null, Classification.DELETED);
        }

        final var dissimilarity = calculateDissimilarity(valueBefore, currentValue);
        final var similarity = 1 - dissimilarity;
        return new Pair<>(dissimilarity, resolveClassification(similarity));
    }

    private double calculateDissimilarity(final String valueBefore, final String currentValue) {
        final var longerSequenceString = Math.max(valueBefore.length(), currentValue.length());
        var differences = calculateDifferencesWithLevenshteinDistance(valueBefore, currentValue);
        return differences / longerSequenceString;
    }


    private double calculateDifferencesWithLevenshteinDistance(final String valueBefore, final String currentValue) {
        final var dp = new int[valueBefore.length() + 1][currentValue.length() + 1];

        for (var i = 0; i <= valueBefore.length(); i++) {
            dp[i][0] = i;
        }
        for (var j = 0; j <= currentValue.length(); j++) {
            dp[0][j] = j;
        }

        for (var i = 1; i <= valueBefore.length(); i++) {
            for (var j = 1; j <= currentValue.length(); j++) {
                if (valueBefore.charAt(i - 1) == currentValue.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j],
                                    dp[i][j - 1]));
                }
            }
        }

        return dp[valueBefore.length()][currentValue.length()];
    }

    private Classification resolveClassification(final double similarity) {
        if (similarity < 0.4) {
            return Classification.LOW;
        } else if (similarity >= 0.4 && similarity <= 0.9) {
            return Classification.MEDIUM;
        } else {
            return Classification.HIGH;
        }
    }
}
