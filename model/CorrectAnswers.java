package model;
import java.io.*;


public class CorrectAnswers {
 private String Answers="Answers.txt"; //this will be a string of the correct answers
 private String [] correctAnswers; //this will be an array of the correct answers

 public CorrectAnswers(){//this will load the correct answers
      try {
        this.correctAnswers=loadAnswers();
    } catch (IOException e) {
        e.printStackTrace();
    }
 }

 private int countFileLines(String fileName) throws IOException {
     int lines=0;
     try (BufferedReader reader = new BufferedReader(new FileReader(Answers))){
         while (reader.readLine() != null) {
             lines++;
         }
     }

     return lines;
 }

private String[] loadAnswers() throws IOException {
    int numLines = countFileLines(Answers);
    String[] correctAnswers = new String[numLines];
    try(BufferedReader reader = new BufferedReader(new FileReader(Answers))) {
       String line;
       int i=0;
       while ((line = reader.readLine()) != null) {
           correctAnswers[i++] = line.trim().toUpperCase();
          
       }
        }
        return correctAnswers;
    }

    
}

 

