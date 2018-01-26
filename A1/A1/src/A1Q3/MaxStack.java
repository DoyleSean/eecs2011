package A1Q3;
import java.util.*;

/**
 * Specializes the stack data structure for comparable elements, and provides
 * a method for determining the maximum element on the stack in O(1) time.
 * @author jameselder
 */
public class MaxStack<E extends Comparable<E>> extends Stack<E> {

    private Stack<E> maxStack, unsortedStack;
    
    public MaxStack() {
        maxStack = new Stack<>();
        unsortedStack = new Stack<>();
    }
    
    /* must run in O(1) time */
    public E push(E element) {
    		if(maxStack.isEmpty()){
    			maxStack.push(element);								// simple push into empty stack			
    			unsortedStack.push(element);
    		} else if (element.compareTo(maxStack.peek()) < 0){		// element on top is more than whats being pushed
    			maxStack.push(maxStack.peek());						// repush the max   			
    			unsortedStack.push(element);						// always push for the unsorted stack
    		} else if (element.compareTo(maxStack.peek()) >= 0){	// element on top is less than whats being pushed 
    			maxStack.push(element);
    			unsortedStack.push(element);
    		}
        return element;
    }

    /* @exception  EmptyStackException  if this stack is empty. */
    /* must run in O(1) time */
   public synchronized E pop() {
	   //exception handling
	   if(unsortedStack.isEmpty()) {
		   throw new EmptyStackException();
	   } else { //functionality - pop from both stacks, return the max pop
		   unsortedStack.pop();
		   E obj = maxStack.peek();
		   maxStack.pop();
		   return obj;
	   }
    }

    /* Returns the maximum value currenctly on the stack. */
    /* @exception  EmptyStackException  if this stack is empty. */
    /* must run in O(1) time */
    public synchronized E max() {
    	// exception handling
    	if(unsortedStack.isEmpty()) {
    		throw new EmptyStackException();
    	}
    	// functionality - top of maxstack is the highest element
    	E max = maxStack.peek();
        return max; //Dummy return to satisfy compiler.  Remove once coded.
    }
}