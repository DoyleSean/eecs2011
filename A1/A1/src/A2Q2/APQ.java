package A2Q2;

import java.util.*;

/**
 * Adaptible priority queue using location-aware entries in a min-heap, based on
 * an extendable array.  The order in which equal entries were added is preserved.
 *
 * @author jameselder
 * @param <E> The entry type.
 */
public class APQ<E> {

    private final ArrayList<E> apq; //will store the min heap
    private final Comparator<E> comparator; //to compare the entries
    private final Locator<E> locator;  //to locate the entries within the queue

    /**
     * Constructor
     * @param comparator used to compare the entries
     * @param locator used to locate the entries in the queue
     * @throws NullPointerException if comparator or locator parameters are null
     */
    public APQ(Comparator<E> comparator, Locator<E> locator) throws NullPointerException {
        if (comparator == null || locator == null) {
            throw new NullPointerException();
        }
        apq = new ArrayList<>();
        apq.add(null); //dummy value at index = 0
        this.comparator = comparator;
        this.locator = locator;
    }

    /**
     * Inserts the specified entry into this priority queue.
     *
     * @param e the entry to insert
     * @throws NullPointerException if parameter e is null
     */
    public void offer(E e) throws NullPointerException {
    	if(e == null) {
    		throw new NullPointerException();
    	}
    	apq.add(e);
    	upheap(this.size());
    }

   /**
     * Removes the entry at the specified location.
     *
     * @param pos the location of the entry to remove
     * @throws BoundaryViolationException if pos is out of range
     */
    public void remove(int pos) throws BoundaryViolationException {
    	if(pos > this.size() || pos < 1) {
    		throw new BoundaryViolationException();
    	}
    	apq.set(1, apq.get(this.size())); 
    	apq.set(this.size(), null);
    	downheap(1);
    }

   /**
     * Removes the first entry in the priority queue.
 * @throws BoundaryViolationException 
     */
    public E poll() throws BoundaryViolationException {
        E remove = apq.get(1);
        apq.remove(remove);
        upheap(1);
        return remove;
    }

  /**
     * Returns but does not remove the first entry in the priority queue.
     */
     public E peek() {
        if (isEmpty()) {
            return null;
        }
        return apq.get(1);
    }

   public boolean isEmpty() {
        return (size() == 0); 
    }

    public int size() {
        return apq.size() - 1; //dummy node at location 0
    }


    /**
     * Shift the entry at pos upward in the heap to restore the minheap property
     * @param pos the location of the entry to move
     */
    private void upheap(int pos) {
    	if (pos == 1) {
    		return;
    	} else {
    		int thisNodePos = pos;
    		int parentNodePos = Math.floorDiv(pos, 2);
    		System.out.println(apq);
    		E thisNode = apq.get(thisNodePos);
    		E parentNode = apq.get(parentNodePos);    		
    		if(comparator.compare(thisNode, parentNode) > 0) {
    			swap(thisNodePos, parentNodePos);
    			upheap(parentNodePos);
    		} else {
    			return;
    		}
    	}
    }

    /**
     * Shift the entry at pos downward in the heap to restore the minheap property
     * @param pos the location of the entry to move
     */
    private void downheap(int pos) {
    	// Base case: end of heap reached
        if(pos == apq.size()) {
        	return;
        } else {
        	int thisNodePos = pos;
        	int leftChildPos = pos+1;
        	int rightChildPos = pos+2;
        	E thisNode = apq.get(thisNodePos);
        	E leftChildNode = apq.get(leftChildPos);
        	E rightChildNode = apq.get(rightChildPos);
        	if(thisNode == null) {
        		return;
        	}
        	if(leftChildNode == null) {
        		return;
        	}
        	
        	// Check if heap order is not valid
        	if(comparator.compare(thisNode, leftChildNode) > 0) {
        		return;
        	}
        	
        	// Determine path of traversal
        	boolean goLeft;
        	if(rightChildNode == null) {
        		goLeft = true;
        	} else if(comparator.compare(leftChildNode, rightChildNode) > 0) {
        			goLeft = false;
        		} else {
        			goLeft = true;
        		}
        		if(goLeft) {
        			swap(thisNodePos, leftChildPos);
        			downheap(leftChildPos);
        		} else {
        			swap(thisNodePos, rightChildPos);
        			downheap(rightChildPos);
        		}
        	} 
        }
    

    /**
     * Swaps the entries at the specified locations.
     *
     * @param pos1 the location of the first entry 
     * @param pos2 the location of the second entry 
     */
    private void swap(int pos1, int pos2) {
        E obj1 = apq.get(pos1);
        E obj2 = apq.get(pos2);
        
        locator.set(obj1, pos2);
        locator.set(obj2, pos1);
        
        return;
    }
}