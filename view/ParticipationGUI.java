package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ParticipationGUI extends JFrame {

    private static final long serialVersionUID = 1L;
	private JTextArea textArea;
    private JTextField searchField;
    //public BufferReader reader;

    public ParticipationGUI() {
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

        // Buttons
        JButton loadChatButton = new JButton("Load Chat");
        JButton clearButton = new JButton("Clear");
        JButton returnToMainButton = new JButton("Return to Main");
        JButton exitButton = new JButton("Exit");

        // Text area with scroll pane
        textArea = new JTextArea(31, 130);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Search field and Enter button
        String defaultText = "Jeneal Dawkins";
        
        searchField = new JTextField(defaultText, 20);
        //searchField.setForeground(Color.GRAY);
        JButton enterSearchButton = new JButton("Enter");

        // View All Participation Grade button
        JButton viewAllButton = new JButton("View All Participation Grade");

        // Layout
        setLayout(new BorderLayout());

        // Top panel for buttons
        JPanel topPanel = new JPanel();
        topPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add spacing
        topPanel.add(loadChatButton);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add spacing
        topPanel.add(clearButton);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add spacing
        topPanel.add(returnToMainButton);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add spacing
        topPanel.add(exitButton);

        // Middle panel for text area
        JPanel middlePanel = new JPanel();
        middlePanel.add(scrollPane);

        // Bottom panel for search field, Enter button, and View All button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        // Center the label and text field
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel searchLabel = new JLabel("Search for a specific student participation grade");
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(enterSearchButton);

        // Center the View All button
        JPanel viewAllPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        viewAllPanel.add(viewAllButton);

        // Add panels to the frame
        bottomPanel.add(searchPanel);
        bottomPanel.add(viewAllPanel);

        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	loadFile();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(""); // Clear the text area
            }
        });

        returnToMainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new InstructorGUI().setVisible(true); 
                dispose();       
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int choice = exitChecker();
            	if(choice == JOptionPane.YES_OPTION) {
            		System.exit(0);
            	}
            }
        });

        enterSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement your search functionality
            	//textArea.setText(InstructorGUI.instructorName);
            }
        });

        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement your view all participation grade functionality
            }
        });

        // Center the frame
        setLocationRelativeTo(null);

        setVisible(true);
    }
    
    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
            	            	
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                textArea.setText(content.toString());

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }//end try-catch
        }//end if
    }
    
    private int exitChecker() {
    	return JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ParticipationGUI();
        });
    }
}
