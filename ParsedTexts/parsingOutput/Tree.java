package parsingOutput;
//package ParsingParsed;

import java.util.*;

public class Tree<T> {
   private ArrayList<Node> tree;

   public Tree() {
      tree = new ArrayList<>();
   }
   
   public Node remove(Node toRemove){
      for(Node a: ((Node[]) toRemove.getChildren().toArray())){
         this.remove(a);
      }
      List brothers = tree.get(tree.indexOf(toRemove.getParent())).getChildren();
      brothers.remove(brothers.indexOf(toRemove));
      return tree.remove(tree.indexOf(toRemove));
   }
   
   public boolean add(Node parent, Node child){
      if(parent == null)
         tree.add(child);
      else{
         tree.add(child);
         child.setParent(parent);
         parent.addChild(child);
      }
      return true;
   }
   
   public boolean addPhrase(Phrase parentPhrase, Phrase phrase){
      if(parentPhrase == null)
         return this.add(null,new Node(phrase, null));
      Node parent = nodeOf(parentPhrase);
      return this.add(parent,new Node(phrase,parent));  
   
   }
   
   public String toString(){
      return tree.toString();
   }
   
   public int size(){
      return tree.size();
   }
   
   public Node nodeOf(Phrase phrase){
      for(Node a: tree)
         if(a.getPhrase() == phrase){
            return a;
         }
      return null;
   }
   
   public Node get(int i){
      return tree.get(i);
   }
   
   public ArrayList<Node> getArrayList(){
      return tree;
   } 
}