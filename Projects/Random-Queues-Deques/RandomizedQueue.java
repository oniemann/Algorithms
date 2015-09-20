//Okeefe Niemann
//9/20/2015
//Creates a Queue whose indices are randomized by the corresponding iterator.
//Everything retrieved from the Queue is randomized.

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
   private Item[] queue;
   private int size = 0;
   private int head = 0;
   private int tail = 0;

   //construct an empty randomized queue
   public RandomizedQueue() {
      queue = (Item[]) new Object[2];
   }

   //is the queue empty?
   public boolean isEmpty() {
      return size == 0;
   }

   //return the number of items on the queue
   public int size() {
      return size;
   }

   //add the item
   public void enqueue(Item item) {
      if (item == null) throw new java.lang.NullPointerException(); 
      if (size == queue.length) resize(2 * queue.length);
      queue[tail++] = item;
      size++;   
   }

   //removes and returns a random item
   public Item dequeue() {
      if (isEmpty()) throw new java.util.NoSuchElementException();

      int rand =  StdRandom.uniform(size);
      Item item = queue[rand];
      queue[rand] = queue[tail-1];
      queue[tail-1] = null;
      size--;
      tail--;
      if (size > 0 && size == queue.length/4) resize(queue.length/2); 
      return item;      
   }

   //return (but does not remove) a random item
   public Item sample() {
      if (isEmpty()) throw new java.util.NoSuchElementException();
      int rand = StdRandom.uniform(size);
      return queue[rand]; 
   }

   private void resize(int max) {
      assert max >= size;
      Item[] temp = (Item[]) new Object[max];
      
      for (int i = 0; i < size; i++)
         if (queue[(head+i) % queue.length] != null)
            temp[i] = queue[(head+i) % queue.length];
      
      queue = temp;
      head = 0;
      tail = size;
   }

   //return an independent iterator
   public Iterator<Item> iterator() {
      return new ArrayIterator();
   }
   
   private class ArrayIterator implements Iterator<Item> {
      private int N = 0;
      private int[] qSeq;

      public ArrayIterator() {
         N = size;
         qSeq = new int[size];
         
         for (int i = 0; i < size; i++)
            qSeq[i] = i;
         
         StdRandom.shuffle(qSeq);
      }

      public boolean hasNext() { return N > 0; }
      public void remove() { throw new java.lang.UnsupportedOperationException(); }
      public Item next() {
         if (!hasNext()) throw new java.util.NoSuchElementException();
         N--;
         return queue[qSeq[N]];
      }
   }

   //Prints the queue
   public void printQueue() {
      Iterator itr = iterator();

      while (itr.hasNext()) {
         Object element = itr.next();
         System.out.print(element + " ");
      }
   
      System.out.println("");
   }


   //unit testing
   public static void main(String[] args) {
      RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

     // queue.enqueue(452);
     // queue.printQueue();
     // queue.enqueue(124);
     // queue.printQueue();
     // queue.dequeue();
     // queue.printQueue();
      //for (int i = 0; i < 10; i++)
      //   queue.enqueue(i);

      //queue.printQueue();

      //for (int i = 0; i < 10; i++) {
      //   queue.dequeue();
         //queue.printQueue();
      //   StdOut.println(queue.sample());
      //}

      //queue.dequeue();
      //queue.printQueue();
   }
}
