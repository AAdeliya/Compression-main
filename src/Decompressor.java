import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Decompressor {
    public static void main(String[] args) throws FileNotFoundException, IOException, CompressionException, ClassNotFoundException {
        String compressedFile = "src/files/input.txt.sc";
        String decompressedFile = compressedFile + ".txt";
    //the more you have in one class the more mistake you will have 
     ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(compressedFile)); //take a file
        Object inputObject = inputStream.readObject();  //read

        if(!(inputObject instanceof CompressionInfoHolder inputHolder)){
            throw new CompressionException("Unexpected type received. Program expects" + CompressionInfoHolder.class.getCanonicalName());
        }
           

            BufferedWriter decopressingWriter = new BufferedWriter(new FileWriter(decompressedFile)); 
            byte[] codedBytes = inputHolder.getCodedText();
            for (int i = 0; i < codedBytes.length; i++) {
                short high = codedBytes[i];
                short low = codedBytes[i+1];
                //every iteration we do 2 bytes
                short code = (short)((high<< 8) +low);

                String word = inputHolder.getCodeToWord().get(code);
                if(word !=null) decopressingWriter.write(word); //we write each word that we took
                
            }
            decopressingWriter.flush();
            decopressingWriter.close();
            System.out.println("Compressed file had: " + codedBytes.length/2 + " words.");
            System.out.println("Compressed file had: " + inputHolder.getCodeToWord().size() + " unique words");
    }
    
}
