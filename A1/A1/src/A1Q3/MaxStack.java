package A1Q3;
import java.util.*;

/**
 * Specializes the stack data structure for comparable elements, and provides
 * a method for determining the maximum element on the stack in O(1) time.
 * @author jameselder
 */
public class MaxStack<E extends Comparable> extends Stack<E> {

    private Stack<E> maxStack;
    private Stack<E> otherStack = new Stack<>();
    
    public MaxStack() {
        maxStack = new Stack<>();
    }
    
    /* must run in O(1) time */
    public E push(E element) {
    		if(maxStack.isEmpty()){
    			maxStack.push(element);
    			otherStack.push(element);
    		} else {
    			if(maxStack.peek().compareTo(element) == )
    		}

        return element; //Dummy return to satisfy compiler.  Remove once coded.
    }

    /* @exception  EmptyStackException  if this stack is empty. */
    /* must run in O(1) time */
   public synchronized E pop() {
	   E obj = maxStack.peek();
	   maxStack.pop();
        return obj; //Dummy return to satisfy compiler.  Remove once coded.
    }

    /* Returns the maximum value currenctly on the stack. */
    /* @exception  EmptyStackException  if this stack is empty. */
    /* must run in O(1) time */
    public synchronized E max() {
    	
        return null; //Dummy return to satisfy compiler.  Remove once coded.
    }
}