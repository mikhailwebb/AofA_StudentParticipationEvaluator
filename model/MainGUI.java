package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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
        setTitle("STUDENT PARTICIPATION EVALUATOR");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        // Text area for displaying results
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Panel for buttons
       JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Load Chat Log Button
        addButton("Load Chat Log", buttonPanel, e -> loadChatLog(), "Load the chat log file");
        // Load Questions Button
        addButton("Load Answers", buttonPanel, e -> loadQuestions(), "Load the questions file");
        // Display Grades Button
        addButton("Display Grades", buttonPanel, e -> displayGrades(),"Calculate and display grades"); 

        // Display the Clear button
        addButton("Clear", buttonPanel, e -> textArea.setText(""), "Clear the text area");
       

        add(buttonPanel, BorderLayout.SOUTH);
        // Display the frame
        setVisible(true);
    }
    
    private void addButton(String text, JPanel panel, ActionListener listener, String toolTip) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setToolTipText(toolTip);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBackground(new Color(230, 230, 250));
        button.setForeground(Color.DARK_GRAY);
        panel.add(button);
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

    try {
        
        PrintWriter writer = new PrintWriter(new FileWriter(gradesFilePath, true));
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
            String gradeOutput = String.format("%s | Jaccard similarity score: %.2f | Grade: %d%n", name, similarity, grade);
            writer.print(gradeOutput);
            textArea.append(String.format("%s | Grade: %d%n", name, grade));
        }
        JOptionPane.showMessageDialog(this, "Grades calculated and written to file.", "Success", JOptionPane.INFORMATION_MESSAGE);
        writer.close();
    }} catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error processing files: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
