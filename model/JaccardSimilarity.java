package model;

import java.util.*;

public class JaccardSimilarity {

    public static double calculateJaccardSimilarity(Set<String> correctAnswerTokens, Set<String> participantAnswerTokens) {
        Set<String> intersection = new HashSet<>(correctAnswerTokens);
        intersection.retainAll(participantAnswerTokens);

        Set<String> union = new HashSet<>(correctAnswerTokens);
        union.addAll(participantAnswerTokens);

        return intersection.size() / (double) union.size();
    }
}
