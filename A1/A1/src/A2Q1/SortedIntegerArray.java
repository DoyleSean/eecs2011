package A2Q1;
import java.util.*;
/**
 * Represents a sorted integer array.  Provides a method, kpairSum, that
 * determines whether the array contains two elements that sum to a given
 * integer k.  Runs in O(n) time, where n is the length of the array.
 * @author jameselder
 */
public class SortedIntegerArray {

    protected int[] sortedIntegerArray;

    public SortedIntegerArray(int[] integerArray) {
        sortedIntegerArray = integerArray.clone();
        Arrays.sort(sortedIntegerArray);
    }

/**
 * Determines whether the array contains two elements that sum to a given
 * integer k. Runs in O(n) time, where n is the length of the array.
 * @author jameselder
 */
    public boolean kPairSum(Integer k) {
    	int size = sortedIntegerArray.length;
    	if(size < 1) {
    		return false;
    	}
    	return kPairSumInterval(k, 0, size-1);
    }
    
    private boolean kPairSumInterval(Integer k, int i, int j) {
    	long tempSum = sortedIntegerArray[i] + sortedIntegerArray[j];
    	int newI = i+1;
    	int newJ = j-1;
    	if (tempSum == k) {
    		return true;
    	} else if (tempSum > k) {
    		if(newI >= newJ) {
    			return false;
    		}
    		return kPairSumInterval(k, newI, newJ);
    	} else if (tempSum > k) {
    		if(newI >= newJ) {
    			return false;
    		}
    		return kPairSumInterval(k, newI, newJ);
    	} else {
    		return false;
    	}
    }
}