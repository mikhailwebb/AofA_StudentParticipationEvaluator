package model;
import java.io.*;


public class CorrectAnswers {
 private String answersFilePath; //this will be a string of the correct answers
  
 public CorrectAnswers( String answersFilePath){//this will load the correct answers

        this.answersFilePath=answersFilePath;
   
 }


 private int countFileLines(String fileName) throws IOException {
     int lines=0;
     try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
         while (reader.readLine() != null) {
             lines++;
         }
     }

     return lines;
 }

public String[] loadAnswers() throws IOException {
    int numLines = countFileLines(answersFilePath);
    String[] correctAnswers = new String[numLines];
    try(BufferedReader reader = new BufferedReader(new FileReader(answersFilePath))) {
       String line;
       int i=0;
       while ((line = reader.readLine()) != null) {
           correctAnswers[i++] = line.trim().toUpperCase();
          
       }
        }
        return correctAnswers;
    }



public String getAnswersFilePath() {
    return answersFilePath;
}




    
}

 

