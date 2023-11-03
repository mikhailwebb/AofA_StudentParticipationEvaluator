package model;

public class ChatMessage {
    private String studentName;
    private String instructorName;
    private String message;

    public ChatMessage(String studentName, String instructorName, String message) {
        this.studentName = studentName;
        this.instructorName = instructorName;
        this.message = message;
    }

    //Getters
    public String getStudentName() {
        return studentName;
    }
    
    public String getInstructorName() {
        return instructorName;
    }
    
    public String getMessage() {
        return message;
    }

    //Setters
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
