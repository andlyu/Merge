package parsingOutput;

import java.io.*;
import java.util.*;
public class FindConnections{
   public static void main(String [] args)throws IOException{
      Scanner input = null;
      BufferedWriter writer = null;
      try{
         input = new Scanner(new FileReader("New Hood.txt"));
         //writer = new BufferedWriter(new FileWriter("Noun.txt"));
      } catch(Exception e){
         e.printStackTrace();
      }
      String line = "";
      while(input.hasNext()){
         line = input.nextLine();
         Stack<Integer> open = new Stack(); 
         int countParen = 0; 
         String noun;
         String verb; 
         boolean nounPhrase; 
         boolean verbPhrase;
         int loc = line.indexOf("(S");
         String curLine = line;
         for(int i = loc; i<line.length(); i++){
            curLine = line.substring(i);
         
            if(countParen>0){
               if(curLine.charAt(0) == ')')
                  countParen--;
               else if(curLine.charAt(0) == '(')
                  countParen++;
              
            }
            else{
               if(nounPhrase = true && curLine.indexOf(")")< curLine.indexOf(")")){
                  noun = curLine.substring(curLine.indexOf(" "), curLine.indexOf(")"));
                  System.out.println(noun);
               }
               if(curLine.indexOf("(") >= 0)
                  if(curLine.indexOf("(S") == curLine.indexOf("(")){
                     i = i + curLine.indexOf("(");
                  }
                  else if(curLine.indexOf("(N") == curLine.indexOf("(")){
                     i += curLine.indexOf("(");
                     nounPhrase = true;
                  }
                  else if ( curLine.indexOf("(V") == curLine.indexOf("(")){
                     i += curLine.indexOf("(");
                     verbPhrase = true;
                  
                  } else{
                     i += curLine.indexOf("(");
                     countParen++;
                     System.out.print(countParen);
                     for(int j = 0; j<i; j++)
                        System.out.print(" ");
                     System.out.println( curLine);
                  }
            }
         
         
         }
      }
      System.out.println("check");
   }
}