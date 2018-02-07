
public class Phrase{
   private String phrase;
   private String tag;
   private int iD;
   
   public Phrase(){
   }
   
   public Phrase(String phrase, String tag, int iD){
      this.phrase = phrase;
      this.tag = tag;
      this.iD = iD;
   }
   
   public Phrase(String phraseString, int iD){
      this(phraseString.substring(phraseString.indexOf(" ")+1, phraseString.length()-1),phraseString.split(" ")[0].substring(1),iD);
   }
   
   public void setPhrase(String phrase){
      this.phrase = phrase;
   }
   
   public String toString(){
      return phrase;
   }
   
   public String getPhrase(){
      return phrase;
   }
   
   public String getTag(){
      return tag;
   }
   
   
   
   
}
