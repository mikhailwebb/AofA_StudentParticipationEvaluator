package model;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String answersFilePath = "resources/Answers.txt";
        String chatLogPath = "resources/ChatTranscript.txt";
        String outputFilePath = "resources/StudentAnswers.txt";

        CorrectAnswers correctAnswers = new CorrectAnswers(answersFilePath);
        try {
            String[] correctAnswerList = correctAnswers.loadAnswers();
            Set<String> correctAnswerTokens = new HashSet<>(Arrays.asList(correctAnswerList));

            String[] participantAnswers = readChatLog(chatLogPath);
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
                for (String answer : participantAnswers) {
                    Set<String> participantAnswerTokens = new HashSet<>(Arrays.asList(answer.toUpperCase().split("\\s+")));
                    double similarity = JaccardSimilarity.calculateJaccardSimilarity(correctAnswerTokens, participantAnswerTokens);
                    writer.printf("Participant's answer: %s | Jaccard similarity score: %.2f%n", answer, similarity);
                    System.out.println("Participant's answer: " + answer + " | Jaccard similarity score: " + similarity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] readChatLog(String chatLogPath) throws IOException {
        // This assumes the chat log is formatted with one answer per line
        // Additional parsing logic would be needed for a different format
        return new CorrectAnswers(chatLogPath).loadAnswers(); // Reusing the loadAnswers method for simplicity
    }
}
