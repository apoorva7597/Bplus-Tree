public class Node {
  
LeafNode data;
   
Node nextNode;
    
Node prevNode;

    
// Constructor 
public Node(LeafNode data, Node nextNode, Node prevNode) {
        
this.data = data;
       
this.nextNode = nextNode;
       
this.prevNode = prevNode;
   
 }

}