//import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
   private Node head, tail;
   private int queueSize;

   private class Node {
      private Item item;
      Node next = null;
      Node prev = null;
   }

   //constructs an empty deque
   public Deque() {
      head = null;
      tail = null;
      queueSize = 0;
   }

   //is the deque empty?
   public boolean isEmpty() {
      return head == null;
   }

   //return the number of items on the deque
   public int size() {
      return queueSize;
   }

   //add the item to the front
   public void addFirst(Item item) {
      if (item == null) throw new java.lang.NullPointerException();
      queueSize++;
      Node temp = head;
      Node newNode = new Node();
      newNode.item = item;
      newNode.prev = null;
      if (isEmpty()) {
         head = newNode;
         tail = head;
      }
      else {
         head = newNode;   
         head.next = temp;
         temp.prev = head;
      }
      /*Node head = new Node();
      head.item = item;
      head.prev = null;
      if (isEmpty()) tail = head;
      else {
         head.next = temp;
         temp.prev = head;
      }*/
   }

   //add the item to the end
   public void addLast(Item item) {
      if (item == null) throw new java.lang.NullPointerException(); 
      queueSize++;
      Node temp = tail;
      Node newNode = new Node();
      newNode.item = item;
      newNode.next = null;
      if (isEmpty()) {
         tail = newNode; 
         head = tail;
      }
      else {          
         tail = newNode;
         temp.next = tail; 
         tail.prev = temp;
      }
   }

   // remove and return the item from the front  
   public Item removeFirst() {
      if (head == null) throw new java.util.NoSuchElementException();
      queueSize--;
      Item item = head.item;
      if (head.next == null) {
         head = null;
         tail = null;
      }
      else {
         head = head.next;
         head.prev = null;
      }

      return item;
   }             

   // remove and return the item from the end
   public Item removeLast() {
      if (head == null) throw new java.util.NoSuchElementException();
      queueSize--;
      Item item = tail.item;

      if (head.next == null) {
         head = null;
         tail = null;
      }
      else {
         tail = tail.prev;
         tail.next = null;
      }
   
      return item;
   }            

   // return an iterator over items in order from front to end
   public Iterator<Item> iterator() { return new ListIterator(); }

   private class ListIterator implements Iterator<Item> {
      private Node curr = head;

      public boolean hasNext() { return curr != null; }

      public void remove() {
         throw new java.lang.UnsupportedOperationException();
      }

      public Item next() {
         if (!hasNext()) throw new java.util.NoSuchElementException();
         Item item = curr.item;
         curr = curr.next;
         return item;
      }
   }

/*
   public void printQueue() {
      Iterator itr = iterator();

      while (itr.hasNext()) {
         Object element = itr.next();
         System.out.print(element + " ");
      }
      
      System.out.println("");
   }
*/

   //unit testing        
   public static void main(String[] args) {
      Deque<Integer> deque = new Deque<Integer>();
      
/*      System.out.println(deque.isEmpty());
      for (int i = 0; i < 10; i++){
         deque.addFirst(i);
         deque.printQueue();
      }*/
      //deque.addFirst(1);
      //deque.removeLast();
      //deque.printQueue();
/*      deque.addFirst(1);
      deque.printQueue();
      StdOut.println("Size: " + deque.size());
      deque.addFirst(2);
      deque.printQueue();
      System.out.println("Size: " + deque.size());
      deque.addFirst(3);
      deque.printQueue();
      StdOut.println("Size: " + deque.size());
      deque.removeFirst();
      deque.printQueue();
      deque.removeLast();
      StdOut.println("Size: " + deque.size());
      deque.printQueue();
*/
   }
}
