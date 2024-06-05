import java.util.Map;

public class CompressionInfoHolder {
     private final Map<Short, String> codeToWord; 
     private final byte[] codedText;
    Map<Short, String> codeMap; 
    byte[] codeWords;
    public CompressionInfoHolder(Map<Short, String> codeToWord, byte[] codedText){
this.codeToWord  = codeToWord;
this.codedText = codedText;
    }
    public byte[] getCodedText(){
        return codedText;
    }
    public Map<Short, String> getCodeToWord(){
        return codeToWord;
        
    }
    
}
