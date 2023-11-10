package TextFiles;

import java.util.HashSet;
import java.util.Set;

public class JaccardSimilarity {
    public static double calculate(String phrase1, String phrase2) {
        // Tokenize the phrases into words
        Set<String> words1 = tokenize(phrase1);
        Set<String> words2 = tokenize(phrase2);

        // Calculate the intersection of words
        Set<String> intersection = new HashSet<>(words1);
        intersection.retainAll(words2);

        // Calculate the union of words
        Set<String> union = new HashSet<>(words1);
        union.addAll(words2);

        // Calculate Jaccard similarity
        if (union.isEmpty()) {
            return 0.0; // Avoid division by zero
        } else {
            return (double) intersection.size() / union.size();
        }
    }

    private static Set<String> tokenize(String phrase) {
        String[] tokens = phrase.split("\\s+"); // Split by whitespace
        Set<String> words = new HashSet<>();
        for (String token : tokens) {
            words.add(token.toLowerCase()); // Convert to lowercase for case-insensitive comparison
        }
        return words;
    }

    public static void main(String[] args) {
        //String phrase1 = "It means that for large input sizes, algorithm X has a better time or space complexity compared to algorithm Y";
        //String phrase2 = "X outperforms algorithm Y when the input size becomes very large";
    	
    	String phrase1 = "X outperforms algorithm Y when the input size becomes very slarge";
        String phrase2 = "X outperforms algorithm Y when the input size becomes very large";

        //String phrase1 = "YES"; //It means that for large input sizes, algorithm X has a better time or space complexity compared to algorithm Y";
        //String phrase2 = "no";//"X outperforms algorithm Y when the input size becomes very large";

        
        double similarity = calculate(phrase1, phrase2);
        System.out.println("Jaccard similarity: " + similarity);
    }
}
