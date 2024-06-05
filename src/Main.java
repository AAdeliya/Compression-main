import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Main {

    public static void main(String[] args) throws CompressionException {
        // Compressor compressor = new Compressor();
        // List<String> words = compressor.getAllWords();
        // Map<String, Integer> map = compressor.createMap(words);


        // for (String word : words) {
        //     System.out.println("Word: " + word);
        // }

        // for (String key : map.keySet()) {
        //     Integer value = map.get(key);
        //     System.out.println("Key: " + key + ", Value: " + value);
        // }

        // compressor.compress(map, true);
        // compressor.compress(map, false);
        // //compressor.decompress(map);
        // try {
        //     compressor.compareFiles();
        // } catch (IOException ex) {
        //     Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        
    String inputFile = "src/files/input.txt";
    String outputFile = inputFile + ".sc";

    try{
    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(outputFile));




    ByteArrayOutputStream codedText= new ByteArrayOutputStream();
    Map<String,Short> wordToCode = new HashMap<>();
    Map<Short, String> codeToWord = new HashMap<>();
    short codeCounter = 0;


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

            codeCounter++;
            if(codeCounter == Short.MAX_VALUE){
                throw new CompressionException("Tooo many words in the file");
            }
        }

        CompressionInfoHolder holder = new CompressionInfoHolder(codeToWord, codedText.toByteArray());



        System.out.println(w);
       }
             System.out.println();
             line=reader.readLine();
    }
    System.out.println("Number of words " + codeCounter);

       
     }catch(FileNotFoundException e){
        System.err.println("File not found" + inputFile);
    } catch(IOException e){
        System.err.println("Error occured during reading" + inputFile + "error : " + e.getMessage());
      }
    
    

    
    }
    }
