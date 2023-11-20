package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructorGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField instructorNameField;
    public static String instructorName;
    
    public InstructorGUI() {
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Student Partipation Evaluator");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        ImageIcon image = new ImageIcon("src/resources/validator-logo.png");
        setIconImage(image.getImage());
        getContentPane().setBackground(new Color(0xAED6F1));

        // Components
        //String defaultText = "Dr Arnett Campbell"; // Default text
        JLabel instructionsLabel = new JLabel("<html><div style='text-align: center;'><b><u>NB:</u></b> Write the "
        		+ "instructor's name exactly as Dr William White, Ms Sandra Ronald, Prof Liz Taylor-Bland, or Mr "
        		+ "James Baldwin. <font color='red'> DO NOT USE ANY</font> full stops, special characters, commas, "
        		+ "numbers, all common letters, or all capital letters. Only hyphens are allowed inside the name "
        		+ "as a special character.</html>");
        JLabel label = new JLabel("Instructor Name:");
        //instructorNameField = new JTextField(defaultText, 20);
        instructorNameField = new JTextField(25);
        JButton enterButton = new JButton("Enter");
        
        // Inner Layout (FlowLayout for the other components)
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        // Outer Layout (BorderLayout)
        setLayout(new BorderLayout(15, 15));

        // Add components to frame
        add(instructionsLabel, BorderLayout.NORTH);
        
        // Add components to panel
        panel.add(label);
        panel.add(instructorNameField);
        panel.add(enterButton);

        // Add panel to frame
        add(panel, BorderLayout.CENTER);

        // Button action listener
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instructorName = instructorNameField.getText();
                if (validateInstructorName(instructorName)) {
                    new ParticipationGUI().setVisible(true);
                    dispose(); // Close the frame
                    // Perform any action with the entered instructor name
                    String message = "<html>Instructor Name Entered: <font color='blue'><b>" + instructorName + "</b></font></html>";
                    JOptionPane.showMessageDialog(null, message);
                } else {
                    // Display error message for incorrect name format
                	String invalidMessage = "<html><font color = 'red'>Incorrect Instructor Name Format.</font> Please follow the instructions.</html>";
                    JOptionPane.showMessageDialog(null, invalidMessage);
                }
            }
        });

        setVisible(true);
    }

    public static boolean validateInstructorName(String name) {
        // Check if the string starts with one of the specified prefixes
        if (!name.matches("^(Mr|Ms|Dr|Sir|Prof)\\s[A-Za-z-]+(\\s[A-Za-z-]+)*[^-\\s]$")) {
            return false;
        }

        // Check for double spaces
        if (name.contains("  ")) {
            return false;
        }

        // Check for full stops or commas inside the string
        if (name.matches(".*[.,].*")) {
            return false;
        }

        // Check for numbers
        if (name.matches(".*\\d.*")) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InstructorGUI instructorGUI = new InstructorGUI();
            
            instructorGUI.setVisible(true);
        });
    }
}
