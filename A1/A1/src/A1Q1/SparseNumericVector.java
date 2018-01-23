package A1Q1;
import java.util.*;

/**
 * Represents a sparse numeric vector. Elements are comprised of a (long)
 * location index an a (double) value.  The vector is maintained in increasing
 * order of location index, which facilitates numeric operations like
 * inner products (projections).  Note that location indices can be any integer
 * from 1 to Long.MAX_VALUE.  The representation is based upon a
 * singly-linked list.
 * The following methods are supported:  iterator, getSize, getFirst,
 * add, remove, and dot, which takes the dot product of the with a second vector
 * passed as a parameter.
 * @author jameselder
 */
public class SparseNumericVector implements Iterable {

    protected SparseNumericNode head = null;
    protected SparseNumericNode tail = null;
    protected long size;

  /**
     * Iterator
     */
    @Override
    public Iterator<SparseNumericElement> iterator() { //iterator
        return new SparseNumericIterator(this);
    }

    /**
     * @return number of non-zero elements in vector
     */
   public long getSize() {
        return size;
    }

     /**
     * @return the first node in the list.
     */
    public SparseNumericNode getFirst() {
        return head;
    }
    
    /**
     * Add the element to the vector.  It is inserted to maintain the
     * vector in increasing order of index.  If the element has zero value, or if 
     * an element with the same index already exists, an UnsupportedOperationException is thrown. 
     * @param e element to add
     */
  public void add(SparseNumericElement e) throws UnsupportedOperationException {
      boolean doesExist = false;
      while(this.iterator().hasNext()){
    	  	if(this.iterator().next().getIndex() == e.getIndex()){
    	  		doesExist = true;
    	  	}
      }
	  if(e.getValue() == 0 || doesExist){
		  throw new UnsupportedOperationException();
       }
	  
	  if(this.head == null){
		  SparseNumericNode n = new SparseNumericNode(e, null);
		  this.head = n;
		  this.tail = n;
	  } else if (e.getIndex() < this.getFirst().getElement().getIndex()){
		  SparseNumericNode newHead = new SparseNumericNode(e, this.head);
		  this.head = newHead;
	  } else if (e.getIndex() > this.tail.getElement().getIndex()){
		  SparseNumericNode newTail = new SparseNumericNode(e, null);
		  SparseNumericNode oldTail = new SparseNumericNode(this.tail.getElement(), newTail);
		  this.tail = newTail;
	  } else {
		  long index = e.getIndex();
		  SparseNumericElement lastElement;
		  int workingIndex = 1;
		  for(int i = 1; i < this.getSize(); i++){
			  
		  }
	  }
    }

    /**
     * If an element with the specified index exists, it is removed and the
     * method returns true.  If not, it returns false.
     *
     * @param index of element to remove
     * @return true if removed, false if does not exist
     */
    public boolean remove(Long index) {
        //implement this method
        //this return statement is here to satisfy the compiler - replace it with your code.
        return false; 
    }

    /**
     * Returns the inner product of the vector with a second vector passed as a
     * parameter.  The vectors are assumed to reside in the same space.
     * Runs in O(m+n) time, where m and n are the number of non-zero elements in
     * each vector.
     * @param Y Second vector with which to take inner product
     * @return result of inner product
     */

    public double dot (SparseNumericVector Y) {
        //implement this method
        //this return statement is here to satisfy the compiler - replace it with your code.
        return 0;
   }

       /**
     * returns string representation of sparse vector
     */

    @Override
    public String toString() {
        String sparseVectorString = "";
        Iterator<SparseNumericElement> it = iterator();
        SparseNumericElement x;
        while (it.hasNext()) {
            x = it.next();
            sparseVectorString += "(index " + x.getIndex() + ", value " + x.getValue() + ")\n";
        }
        return sparseVectorString;
    }
}
