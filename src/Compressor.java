import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.RuntimeErrorException;
public class Compressor {

    public static void main(String[] args) throws CompressionException {

    
    String inputFile = "src/files/input.txt";
    String compressedFile = inputFile + ".sc";
    String decompressedFile = compressedFile + ".txt";
   
    try{
    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    




    ByteArrayOutputStream codedText= new ByteArrayOutputStream();
    Map<String,Short> wordToCode = new HashMap<>();
    Map<Short, String> codeToWord = new HashMap<>();
    


    short newLineCode = 0;
    wordToCode.put(System.lineSeparator(), newLineCode);
    codeToWord.put(newLineCode, System.lineSeparator());
    
    short codeCounter = (short) (newLineCode+1); //because 1 by default int
    int totalNumberOfWords= 0;


    String line= reader.readLine();
    while(line!= null){
        
        String[] words=line.split("(?<=\\s)|(?=\\s)");   
         //Make sure there's a space before this point, but don't count the space itself.
       for(String w:words){
        Short existingCode = wordToCode.get(w);
        if(existingCode == null){
            wordToCode.put(w, codeCounter);
            codeToWord.put(codeCounter, w);


            byte high= (byte) (codeCounter >>> 8);
            byte low =(byte) codeCounter;
            codedText.write(high);
            codedText.write(low);
            totalNumberOfWords++;

            codeCounter++;
            if(codeCounter == Short.MAX_VALUE){
                throw new CompressionException("Tooo many words in the file");
            }
        }
    }
    codedText.write(0);
    codedText.write(0);
    totalNumberOfWords++;
    line= reader.readLine();
    }
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(compressedFile));

        CompressionInfoHolder holder = new CompressionInfoHolder(codeToWord, codedText.toByteArray());
        writer.writeObject(holder);
        writer.flush();//forcing 
        writer.close();
        
        
        System.out.println("Number of unique words " + codeCounter);
        System.out.println("Number of total words " + totalNumberOfWords);

        




       
     }catch(FileNotFoundException e){
        System.err.println("File not found" + inputFile);
    } catch(IOException e){
        System.err.println("Error occured during reading" + inputFile + "error : " + e.getMessage());
        e.printStackTrace();
    }
    
    

    
    }
    }
