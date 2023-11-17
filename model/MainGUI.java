package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainGUI extends JFrame {

    private String chatLogPath = "";
    private String questionsPath = "";
    private String gradesFilePath = "resources/StudentParticipationGrade.txt";
    private JTextArea textArea;

    public MainGUI() {
        // Frame settings
        setTitle("Grade Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Text area for displaying results
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();

        // Load Chat Log Button
        JButton loadChatLogButton = new JButton("Load Chat Log");
        loadChatLogButton.addActionListener(e -> loadChatLog());
        buttonPanel.add(loadChatLogButton);

        // Load Questions Button
        JButton loadQuestionsButton = new JButton("Load Questions");
        loadQuestionsButton.addActionListener(e -> loadQuestions());
        buttonPanel.add(loadQuestionsButton);

        // Display Grades Button
        JButton displayGradesButton = new JButton("Display Grades");
        displayGradesButton.addActionListener(e -> displayGrades());
        buttonPanel.add(displayGradesButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Display the frame
        setVisible(true);
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> textArea.setText(""));
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    
    

    private void loadChatLog() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            chatLogPath = selectedFile.getAbsolutePath();
            textArea.append("Chat log loaded: " + chatLogPath + "\n");
        }
    }

    private void loadQuestions() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            questionsPath = selectedFile.getAbsolutePath();
            textArea.append("Questions loaded: " + questionsPath + "\n");
        }
    }

   

private void displayGrades() {
    if (chatLogPath.isEmpty() || questionsPath.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please load both chat log and questions files.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {PrintWriter writer = new PrintWriter(new FileWriter(gradesFilePath, true));
        {
        CorrectAnswers correctAnswers = new CorrectAnswers(questionsPath);
        String[] correctAnswerList = correctAnswers.loadAnswers();
        Set<String> correctAnswerTokens = new HashSet<>(Arrays.asList(correctAnswerList));

        String[] chatLogEntries = new CorrectAnswers(chatLogPath).loadAnswers(); // Reusing loadAnswers method
        for (String entry : chatLogEntries) {
            String[] parts = entry.split(":", 2); // Assuming format: Name: Answer
            if (parts.length < 2) {
                continue; // Skip if format is incorrect
            }
            String name = parts[0].trim();
            String answer = parts[1].trim();
            Set<String> participantAnswerTokens = new HashSet<>(Arrays.asList(answer.toUpperCase().split("\\s+")));
            double similarity = JaccardSimilarity.calculateJaccardSimilarity(correctAnswerTokens, participantAnswerTokens);
            int grade = (int) Math.round(similarity * 15); // Round grade to nearest whole number
            String gradeOutput = String.format("%s | Jaccard similarity score: %.2f | Numerical grade: %d%n", name, similarity, grade);
            writer.print(gradeOutput);
            textArea.append(String.format("%s | Numerical grade: %d%n", name, grade));
        }
        JOptionPane.showMessageDialog(this, "Grades calculated and written to file.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }} catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error processing files: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
