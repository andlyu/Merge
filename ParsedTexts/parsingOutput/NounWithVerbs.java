package parsingOutput;

import java.io.*;
import java.util.*;

public class NounWithVerbs{
   private String noun;
   private HashSet<String> verbs;
   public NounWithVerbs(String noun, HashSet verbs){
      this.noun = noun;
      this.verbs = verbs;
   }
   public NounWithVerbs(String noun){
      this(noun,new HashSet<>());
   }
   
   public void addVerb(String verb){
      verbs.add(verb.toLowerCase());
   }
   
   public String getNoun(){
      return noun;
   }
   
   public String toString(){
      Iterator<String> iterator = verbs.iterator();
      String out =  noun + ":";
      while(iterator.hasNext()){
         out += " "+iterator.next();
      }
      return out;
      
   }
}