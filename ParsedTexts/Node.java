import java.util.*;



public class Node<P> {
   private P phrase;
   private Node<P> parent;
   private List<Node> children;
      
   public Node(P phrase, Node parent){
      this.phrase = phrase;
      this.parent = parent;
      this.children = new LinkedList<Node>(); 
   }
   
   public void addChildren(Node<P> child){
      children.add(child);
   }
   
   public List<Node> getChildren(){
      return children;
   }
   
   public P getPhrase(){
      return phrase;
   }
   
   public String toString(){
      return phrase.toString();
   }
   
   public Node<P> getParent(){
      return parent;
   }
   
   public void setParent(Node<P> parent){
      this.parent = parent;
   }
   
   public void addChild(Node<P> child){
      if(children == null)
      {
         children = new LinkedList<>();
         children.add(child);
      }
      else
         children.add(child);
   }
   
}//might have made a mistake with List<Node<P>>