
import java.io.*;
import java.util.*;
public class FindConnections2{
   public static void main(String [] args)throws IOException{ 
      Scanner input = null;
      BufferedWriter writer = new BufferedWriter(new FileWriter("AllWords.txt"));
      Scanner nounInput = new Scanner(new FileReader("Big Nouns.txt"));
      HashMap<String, NounWithVerbs> nouns = new HashMap<>();
      HashSet<String> wholeNounCollecton = new HashSet<>();
      while( nounInput.hasNext()){
         wholeNounCollecton.add(nounInput.nextLine().split(" ")[5]);
      }
      Scanner verbInput = new Scanner(new FileReader("Big Verbs.txt"));
      HashSet<String> wholeVerbCollecton = new HashSet<>();
      while( verbInput.hasNext()){
         wholeVerbCollecton.add(verbInput.nextLine().split(" ")[5]);
      }
      try{
         input = new Scanner(new FileReader("World-Book-Encyclopedia.txt"));         
      } catch(Exception e){
         e.printStackTrace();
      }
      String line = "";
   
      while(input.hasNext()){
         line = input.nextLine();
      
         Queue<Phrase> queue = new LinkedList();
         Tree<Phrase> tree = new Tree();
         int iD = 0; 
         Phrase root = new Phrase(line, iD++);
         queue.add(root);
         tree.addPhrase(null, root);
         while(queue.size() != 0 )
         {
            {
               Phrase parent = queue.poll();
               line = parent.getPhrase();
               int stop = line.length();
               int start = 0;//start of a phrase (after the opening parenthesy)
               int index = start; // to travers through the characters
               int countParen = 0;
               while(line.substring(index).indexOf("(") >= 0)//while has another phrase
               {
                  
                  if(countParen == 0){
                     while(line.charAt(index) != '('){
                        index++;
                     }
                     start = index++;
                  }//finds the first "(" and sets start to it
                  countParen = 1;
                  while (countParen > 0 && index < stop){
                     if(line.charAt(index) == '(')
                        countParen++;
                     else if(line.charAt(index) == ')')
                        countParen--;
                     index++;
                  }// finds where the phrase inds (the ending ")")
                  String phraseString = "";
                  if(countParen == 0)
                     phraseString = line.substring(start, index);//next Line will crash if index>stop;
                  Phrase phrase = new Phrase(phraseString,iD++);// phrase to be added to the stack
                  tree.addPhrase(parent, phrase);
                  if(phrase.getPhrase().contains("("))
                     queue.add(phrase);
                  else
                     start = index;                     
                  
               }
            }
         }
         for(Node a: tree.getArrayList()){
            if("S".equals(((Phrase)a.getPhrase()).getTag()) && !containsChildS(a)){
               Node noun = getNounChild(a);
               Node verb = getVerbChild(a);
               while(noun!= null && getNounChild(noun)!= null){
                  noun = getNounChild(noun);}
               while(verb != null && getVerbChild(verb)!= null)
                  verb = getVerbChild(verb);
               if(noun != null && verb != null)
                  if(!noun.toString().contains("(") && !verb.toString().contains("(")){
                     String wordNoun  = noun.getPhrase().toString().toLowerCase();
                     String wordVerb = verb.getPhrase().toString().toLowerCase();
                     System.out.print("Noun: " + noun.getPhrase());
                     System.out.println("    Verb: " + verb.getPhrase());
                     if(wholeNounCollecton.contains(wordNoun) && wholeVerbCollecton.contains(wordVerb)){
                        if(nouns.get(wordNoun)!= null){
                           NounWithVerbs curNoun = nouns.get(wordNoun);
                           curNoun.addVerb(wordVerb);
                           nouns.put(wordNoun, curNoun);
                        }
                        else{
                           NounWithVerbs curNoun = new NounWithVerbs(wordNoun);
                           curNoun.addVerb(wordVerb);
                           nouns.put(wordNoun,curNoun);
                        }
                     }
                  }
            }
               
         }
      
      }
      for(String a:nouns.keySet()){
         System.out.println(nouns.get(a));
      }
      System.out.println(nouns.size());
   }
   
   public static HashMap infoFromParsed(){
      Scanner input = null;
      BufferedWriter writer = null; // new BufferedWriter(new FileWriter("AllWords.txt"));
      Scanner nounInput =  new Scanner(new FileReader("Big Nouns.txt"));
      HashMap<String, NounWithVerbs> nouns = new HashMap<>();
      HashSet<String> wholeNounCollecton = new HashSet<>();
      while( nounInput.hasNext()){
         wholeNounCollecton.add(nounInput.nextLine().split(" ")[5]);
      }
      Scanner verbInput = null; //  new Scanner(new FileReader("Big Verbs.txt"));
      HashSet<String> wholeVerbCollecton = new HashSet<>();
      while( verbInput.hasNext()){
         wholeVerbCollecton.add(verbInput.nextLine().split(" ")[5]);
      }
      try{
         verbInput =   new Scanner(new FileReader("Big Verbs.txt"));
         writer =  new BufferedWriter(new FileWriter("AllWords.txt"));
         nounInput = new Scanner(new FileReader("Big Nouns.txt"));
         input = new Scanner(new FileReader("World-Book-Encyclopedia.txt"));         
      } catch(Exception e){
         e.printStackTrace();
      }
      String line = "";
   
      while(input.hasNext()){
         line = input.nextLine();
      
         Queue<Phrase> queue = new LinkedList();
         Tree<Phrase> tree = new Tree();
         int iD = 0; 
         Phrase root = new Phrase(line, iD++);
         queue.add(root);
         tree.addPhrase(null, root);
         while(queue.size() != 0 )
         {
            {
               Phrase parent = queue.poll();
               line = parent.getPhrase();
               int stop = line.length();
               int start = 0;//start of a phrase (after the opening parenthesy)
               int index = start; // to travers through the characters
               int countParen = 0;
               while(line.substring(index).indexOf("(") >= 0)//while has another phrase
               {
                  
                  if(countParen == 0){
                     while(line.charAt(index) != '('){
                        index++;
                     }
                     start = index++;
                  }//finds the first "(" and sets start to it
                  countParen = 1;
                  while (countParen > 0 && index < stop){
                     if(line.charAt(index) == '(')
                        countParen++;
                     else if(line.charAt(index) == ')')
                        countParen--;
                     index++;
                  }// finds where the phrase inds (the ending ")")
                  String phraseString = "";
                  if(countParen == 0)
                     phraseString = line.substring(start, index);//next Line will crash if index>stop;
                  Phrase phrase = new Phrase(phraseString,iD++);// phrase to be added to the stack
                  tree.addPhrase(parent, phrase);
                  if(phrase.getPhrase().contains("("))
                     queue.add(phrase);
                  else
                     start = index;                     
                  
               }
            }
         }
         for(Node a: tree.getArrayList()){
            if("S".equals(((Phrase)a.getPhrase()).getTag()) && !containsChildS(a)){
               Node noun = getNounChild(a);
               Node verb = getVerbChild(a);
               while(noun!= null && getNounChild(noun)!= null){
                  noun = getNounChild(noun);}
               while(verb != null && getVerbChild(verb)!= null)
                  verb = getVerbChild(verb);
               if(noun != null && verb != null)
                  if(!noun.toString().contains("(") && !verb.toString().contains("(")){
                     String wordNoun  = noun.getPhrase().toString().toLowerCase();
                     String wordVerb = verb.getPhrase().toString().toLowerCase();
                     System.out.print("Noun: " + noun.getPhrase());
                     System.out.println("    Verb: " + verb.getPhrase());
                     if(wholeNounCollecton.contains(wordNoun) && wholeVerbCollecton.contains(wordVerb)){
                        if(nouns.get(wordNoun)!= null){
                           NounWithVerbs curNoun = nouns.get(wordNoun);
                           curNoun.addVerb(wordVerb);
                           nouns.put(wordNoun, curNoun);
                        }
                        else{
                           NounWithVerbs curNoun = new NounWithVerbs(wordNoun);
                           curNoun.addVerb(wordVerb);
                           nouns.put(wordNoun,curNoun);
                        }
                     }
                  }
            }
               
         }
      
      }
      for(String a:nouns.keySet()){
         System.out.println(nouns.get(a));
      }
      return nouns;
   }
   
   //Pre: phrase is a Node, that is a sentance and should contain a noun
   //Post: returns the noun if there is only one. Otherwise returns null;
   public static Node getNounChild(Node phrase){
      Node out = null;
      for(Node a: ((LinkedList<Node>)phrase.getChildren()))
         if(((Phrase)a.getPhrase()).getTag().charAt(0) == 'N')
            if(out == null)
               out = a;
            else out = null;
      return out;
   }
   
   //Pre: phrase is a Node, that is a sentance and should contain a verb
   //Post: returns the verb if there is only one. Otherwise returns null;
   public static Node getVerbChild(Node phrase){
      Node out = null;
      for(Node a: ((LinkedList<Node>)phrase.getChildren()))
         if(((Phrase)a.getPhrase()).getTag().charAt(0) == 'V')
            if(out == null)
               out = a;
            else out = null;
      return out;
   }
   
   public static boolean containsChildS(Node phrase){
      for(Node a: ((LinkedList<Node>)phrase.getChildren()))
         if(((Phrase)a.getPhrase()).getTag().charAt(0) == 'S')
            return true;
      return false;
   }
   
   public static void debug(Tree tree){
      for(int i = 0; i<tree.size(); i++){
         int countO = 0;
         int countC = 0;
         for(char a: tree.get(i).toString().toCharArray()){
            if(a == '(')
               countO++;
            if(a== ')')
               countC++;
         }
         System.out.println(countO + " "+countC);
         System.out.println(tree.get(i).toString());
      }
   }
}