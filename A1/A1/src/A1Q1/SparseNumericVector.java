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
      while(exceptionIterator.hasNext()){										// traverse the elements
    	  	if(exceptionIterator.next().getIndex() == e.getIndex()){			// check for existing indices
    	  		doesExist = true;
    	  	}
      }	
	  if(e.getValue() == 0 || doesExist){										// exception throwing
		  throw new UnsupportedOperationException();
	  }
	  
	  // functionality
	  if(this.head == null){													// add to empty list
		  SparseNumericNode n = new SparseNumericNode(e, null);
		  this.head = n;
		  this.tail = n;
	  } else if (e.getIndex() < this.getFirst().getElement().getIndex()){		// add to start of list
		  SparseNumericNode newHead = new SparseNumericNode(e, this.head);
		  this.head = newHead;
	  } else if (e.getIndex() > this.tail.getElement().getIndex()){				// add to end of list
		  SparseNumericNode newTail = new SparseNumericNode(e, null);
		  this.tail.setNext(newTail);
		  this.tail = newTail;
	  } else {																	// add to middle of list
		  long insertIndex = e.getIndex();										// index of element to be inserted
		  long lastNodeIndex, workingNodeIndex;									// indices of current and prev nodes
		  SparseNumericNode workingNode = this.head;							// current node
		  SparseNumericNode lastNode = null;									// previous node
		  SparseNumericNode insertNode = new SparseNumericNode(e, null);		// node to be inserted (temporarily null)
		  Iterator<SparseNumericElement> addIterator = this.iterator();
		  while(addIterator.hasNext()){											// traverse the vector searching for insert point
			  workingNodeIndex = workingNode.getElement().getIndex();			// index of current node's element
			  if(workingNode.equals(this.head)){								// for when the insert is next to the head
				  lastNodeIndex = -1;
			  } else {															// for when the insert is anywhere else
				  lastNodeIndex = lastNode.getElement().getIndex();
			  }
			  
			  // check to see that the index to insert is less than the next and greater than the index before it
			  if ((insertIndex < workingNodeIndex) && (insertIndex > lastNodeIndex)) {
				  insertNode.setNext(workingNode);								// chain the inserted node to the next node
				  lastNode.setNext(insertNode);									// chain the previous node to the inserted node
				  break; 														// element added, finished
			  }
			  lastNode = workingNode;											// the last node becomes the old working node
			  workingNode = workingNode.getNext();								// the new working node is the next node
			  addIterator.next();												// iterate to the next element (i.e. node)
		  }
	  }
	  size++;																	// update size to reflect the addition
    }

    /**
     * If an element with the specified index exists, it is removed and the
     * method returns true.  If not, it returns false.
     *
     * @param index of element to remove
     * @return true if removed, false if does not exist
     */
    public boolean remove(Long index) {
    	
    		long remIndex = index;												// the index to be removed 
    		long workingNodeIndex;												// the index we're currently examining
    		SparseNumericNode lastNode = null;									// the node before the index we're examining
    		SparseNumericNode workingNode = this.head;							// the node of the index we're currently examining
    		SparseNumericNode nextNode = null;									// the node after the index we're examining
    		Iterator<SparseNumericElement> removeIterator = this.iterator();
    		while(removeIterator.hasNext()){									// iterate over each element since the # elements = # nodes
    			nextNode = workingNode.getNext();								// compute the next node
    			workingNodeIndex = workingNode.getElement().getIndex();			// compute index of working node
    			if(workingNodeIndex == remIndex){								// check if current node is the one to remove
    				if(workingNode.equals(this.head)){							// special case where the head is removed
    					this.head = this.head.getNext();						// update the head to its next node
    				} else if (workingNode.equals(this.tail)) {					// special case where tail is to be removed
    					lastNode.setNext(null);									// update the last node to chop off the tail
    				} else {													// general case for all other removals
    					lastNode.setNext(nextNode);								// chain around the working node
    				}
    				this.size--;												// reduce the size by one to account for removal
    				return true;												// returns true since there was a removal
    			} else {														// update nodes for next iteration
    				lastNode = workingNode;												
    				workingNode = nextNode;
    				if((lastNode == this.tail)) {								// if the last node is the tail, we're done
    					break;
    				}
    			}
    			removeIterator.next();											// get next element (effectively, the next node)
    		}
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
    	
    	// Maps to store index, element pairs from each SparseNumericVector - allows for O(m+n) projection using keySet()
    	Map<Long, SparseNumericElement> yMap = new LinkedHashMap<Long, SparseNumericElement>();
    	Map<Long, SparseNumericElement> thisMap = new LinkedHashMap<Long, SparseNumericElement>();
    	
    	// Working nodes to convert into each SNV's respective Map
    	SparseNumericNode yNode = Y.head;
    	SparseNumericNode thisNode = this.head; 
    	
    	for(int i = 0; i < this.getSize(); i++) {								// Load Element Index as key and Element Value as value
    		thisMap.put(thisNode.getElement().getIndex(), thisNode.getElement());
    		thisNode = thisNode.getNext();										// Load the next node's values in
    	}
    	for(int y = 0; y < Y.getSize(); y++) {									// Load Element Index as key and Element Value as value
    		yMap.put(yNode.getElement().getIndex(), yNode.getElement());
    		yNode = yNode.getNext();											// Load the next node's values in
    	}
    	
    	double dotSum = 0;														// Initialize the dot product 
    	if(thisMap.size() > yMap.size()) {										// Reduce runtime by traversing the smaller set
    		for(long yIndex : yMap.keySet()) {									// Traverse the yMap keySet() since its smaller
    			if(thisMap.containsKey(yIndex)) {								// Only consider values with matching keys for computation
    				dotSum += yMap.get(yIndex).getValue() * thisMap.get(yIndex).getValue();
    			}
    		}
    	} else {
    		for(long thisIndex : thisMap.keySet()) {							// Traverse the yMap keySet() since its smaller
    			if(yMap.containsKey(thisIndex)) {								// Only consider values with matching keys for computation
    				dotSum += yMap.get(thisIndex).getValue() * thisMap.get(thisIndex).getValue();
    			}
    		}
    	}
        return dotSum; 															// Return the dot product
        
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
