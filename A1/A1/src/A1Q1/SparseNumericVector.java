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
    protected long size = 0;

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
	  // exception handling
      boolean doesExist = false;
      Iterator<SparseNumericElement> exceptionIterator = this.iterator();
      while(exceptionIterator.hasNext()){											// traverse the elements
    	  System.out.println("ADD 1ST WHILE");
    	  	if(exceptionIterator.next().getIndex() == e.getIndex()){				// check for existing indices
    	  		doesExist = true;
    	  	}
      }	
	  if(e.getValue() == 0 || doesExist){										// exception throwing
		  throw new UnsupportedOperationException();
	  }
	  System.out.println("EXCEPTIONS CLEARED");
	  // functionality
	  if(this.head == null){													// add to empty list
		  System.out.println("ADDING TO EMPTY LIST CASE");
		  SparseNumericNode n = new SparseNumericNode(e, null);
		  this.head = n;
		  this.tail = n;
		  System.out.println("EMPTY LIST CASE CLEARED");
	  } else if (e.getIndex() < this.getFirst().getElement().getIndex()){		// add to start of list
		  System.out.println("ADDING TO HEAD OF LIST CASE");
		  SparseNumericNode newHead = new SparseNumericNode(e, this.head);
		  this.head = newHead;
		  System.out.println("HEAD OF LIST CASE CLEARED");
	  } else if (e.getIndex() > this.tail.getElement().getIndex()){				// add to end of list
		  System.out.println("ADDING TO TAIL OF LIST CASE");
		  SparseNumericNode newTail = new SparseNumericNode(e, null);
		  this.tail.setNext(newTail);
		  this.tail = newTail;
		  System.out.println("TAIL OF LIST CASE CLEARED");
	  } else {																	// add to middle of list
		  long insertIndex = e.getIndex();										// index of element to be inserted
		  long lastNodeIndex, workingNodeIndex;									// indices of current and prev nodes
		  SparseNumericNode workingNode = this.head;							// current node
		  SparseNumericNode lastNode = null;									// previous node
		  SparseNumericNode insertNode = new SparseNumericNode(e, null);		// node to be inserted (temporarily null)
		  Iterator<SparseNumericElement> addIterator = this.iterator();
		  while(addIterator.hasNext()){											// traverse the vector searching for insert point
			  System.out.println("ADD general case WHILE");
			  workingNodeIndex = workingNode.getElement().getIndex();			// index of current node's element
			  if(workingNode.equals(this.head)){								// for when the insert is next to the head
				  System.out.println("SET LASTNODE FOR HEADCHECK");
				  lastNodeIndex = -1;
			  } else {															// for when the insert is anywhere else
				  System.out.println("SET LASTNODE FOR GENERAL CHECK");
				  lastNodeIndex = lastNode.getElement().getIndex();
			  }
			  // check to see that the index to insert is less than the next and greater than the index before it
			  System.out.println("CHECKING FOR INSERTION POINT!");
			  if ((insertIndex < workingNodeIndex) && (insertIndex > lastNodeIndex)) {
				  System.out.println("CHAINING!");
				  insertNode.setNext(workingNode);								// chain the inserted node to the next node
				  lastNode.setNext(insertNode);									// chain the previous node to the inserted node
				 
				  break; //done!
			  }
			  System.out.println("NO POINT FOUND, MOVING ON...");
			  lastNode = workingNode;											// the last node becomes the old working node
			  workingNode = workingNode.getNext();
			  addIterator.next();
		  }
	  }
	  size++;
    }

    /**
     * If an element with the specified index exists, it is removed and the
     * method returns true.  If not, it returns false.
     *
     * @param index of element to remove
     * @return true if removed, false if does not exist
     */
    public boolean remove(Long index) {
    	
    		long remIndex = index;
    		long workingNodeIndex;
    		SparseNumericNode lastNode = null;
    		SparseNumericNode workingNode = this.head;
    		SparseNumericNode nextNode = null;
    		Iterator<SparseNumericElement> removeIterator = this.iterator();
    		while(removeIterator.hasNext()){
    			System.out.println("REM WHILE");
    			nextNode = workingNode.getNext();
    			workingNodeIndex = workingNode.getElement().getIndex();			// track index of working node
    			if(workingNodeIndex == remIndex){								// check if indices match
    				if(workingNode.equals(this.head)){							// special case where head is removed
    					System.out.println("REM HEAD CASE");
    					this.head = this.head.getNext();						// update the head for this case
    				} else if (workingNode.equals(this.tail)) {					// special case where 
    					System.out.println("REM TAIL CASE");
    					lastNode.setNext(null);
    				} else {													// general case for all other removals
    					System.out.println("REM GEN CASE");
    					lastNode.setNext(nextNode);								// chain around the working node
    				}
    				System.out.println("REM SUCCESS REMOVED");
    				this.size--;
    				return true;												// returns true since there was a removal
    			} else {														// update nodes for next iteration
    				System.out.println("REM NONMATCH, NEXT ITERATION!");
    				lastNode = workingNode;				
    				workingNode = nextNode;
    				System.out.println("REM WORK ASSIGNMENT TO NEXT");
    				if((lastNode == this.tail)) {
    					System.out.println("TAIL HIT - BREAKING TO NONMATCH");
    					break;
    				}
    			}
    			removeIterator.next();
    		}
    		System.out.println("REM NONMATCH");
    		return false;														// returns false since it ran through the loop w/o a removal
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
    	System.out.println("DOT CALLED");
    	Map<Long, SparseNumericElement> yMap = new LinkedHashMap<Long, SparseNumericElement>();
    	Map<Long, SparseNumericElement> thisMap = new LinkedHashMap<Long, SparseNumericElement>();
    	SparseNumericNode yNode = Y.head;
    	SparseNumericNode thisNode = this.head; 
    	System.out.println("DOT INSTANTIATION");
    	System.out.println("THIS SIZE: " + this.getSize());
    	for(int i = 0; i < this.getSize(); i++) {
    		thisMap.put(thisNode.getElement().getIndex(), thisNode.getElement());
    		thisNode = thisNode.getNext();
    		System.out.println("DOT THIS MAPPING");
    	}
    	System.out.println("Y SIZE: " + Y.getSize());
    	for(int y = 0; y < Y.getSize(); y++) {
    		yMap.put(yNode.getElement().getIndex(), yNode.getElement());
    		yNode = yNode.getNext();
    		System.out.println("DOT Y MAPPED");
    	}
    	
    	double dotSum = 0;
    	if(thisMap.size() > yMap.size()) {
    		System.out.println("DOT THIS LARGER THAN Y");
    		for(long yIndex : yMap.keySet()) {
    			System.out.println("DOT TRAVERSAL ON" + yIndex);
    			if(thisMap.containsKey(yIndex)) {
    				System.out.println("EQUALITY FOUND");
    				dotSum += yMap.get(yIndex).getValue() * thisMap.get(yIndex).getValue();
    				System.out.print(yMap.get(yIndex).getValue() + " x " + thisMap.get(yIndex).getValue());
    			}
    		}
    	} else {
    		System.out.println("DOT Y LARGER/EQUAL THAN THIS");
    		System.out.println(thisMap.keySet());
    		for(long thisIndex : thisMap.keySet()) {
    			System.out.println("DOT TRAVERSAL ON" + thisIndex);
    			if(yMap.containsKey(thisIndex)) {
    				System.out.println("EQUALITY FOUND");
    				dotSum += yMap.get(thisIndex).getValue() * thisMap.get(thisIndex).getValue();
    				System.out.print(yMap.get(thisIndex).getValue() + " x " + thisMap.get(thisIndex).getValue());
    			}
    		}
    	}
        return dotSum;
        
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
