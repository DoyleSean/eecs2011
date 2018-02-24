package A2Q2;

import java.util.*;

/**
 * Triages patients in Emergency Ward according to medical priority and wait time.
 * Priorities are positive integers;  the highest priority is 1. 
 * Normally patients are seen in priority order, however, if there are patients 
 * who have waited longer than a specified time (maxWait), they are seen first, 
 * in order of their arrival.  
 * @author elder
 */
public class PatientTriage {

    private APQ<Patient> priorityHeap; //maintain patients in priority order
    private APQ<Patient> timeHeap;  //maintain patients in order of arrival
    private Time maxWait; //maximum waiting time
    
    /**
     * Constructor
     *
     * @param time Maximum wait time.  Patients waiting longer than this are seen first.
     */
    public PatientTriage(Time time) {
        Comparator<Patient> priorityComparator = new PatientPriorityComparator();
        Comparator<Patient> timeComparator = new PatientTimeComparator();
        Locator<Patient> priorityLocator = new PatientPriorityLocator();
        Locator<Patient> timeLocator = new PatientTimeLocator();
        priorityHeap = new APQ<>(priorityComparator, priorityLocator);
        timeHeap = new APQ<>(timeComparator, timeLocator);
        setMaxWait(time);
    }

   /**
     * Adds patient to queues.  
     * @param patient to add.
     * @throws NullPointerException if given null patient
     */
    public void add(Patient patient) throws NullPointerException {
        if (patient == null) {
            throw new NullPointerException();
        }
        priorityHeap.offer(patient); //add to priority queue
        timeHeap.offer(patient); //add to arrival time queue
    }

  /**
     * Removes next patient in queue.  
     * @param currentTime used to determine whether to use priority or arrival time
     * @return Next patient to attend to
     * @throws NullPointerException if given null time
     * @throws EmptyQueueException if queue is empty
     * @throws BoundaryViolationException under some internal error conditions
     */
    public Patient remove(Time currentTime) throws NullPointerException, EmptyQueueException, BoundaryViolationException {
    	if(priorityHeap.isEmpty() || timeHeap.isEmpty()) {
    		throw new EmptyQueueException();
    	}
    	
    	Patient timeRemove = timeHeap.peek();
    	Patient priorityRemove = priorityHeap.peek();
    	
    	TimeComparator t = new TimeComparator();
    	
		PatientPriorityLocator ppl = new PatientPriorityLocator();
		PatientTimeLocator ptl = new PatientTimeLocator();
		if(currentTime == null) {
			System.out.println("NULL");
    		throw new NullPointerException();
    	}
		// If theres a patient passed the max wait time, remove them from both queues
		Time arrival = timeRemove.getArrivalTime();
		Time elapsed = arrival.elapsed(currentTime);
		
    	if(t.compare(elapsed, this.getMaxWait()) > 0) {
    		int remPos = ppl.get(timeRemove);
    		timeHeap.poll();
    		priorityHeap.remove(remPos);
    		return timeRemove;
    	} else { // Remove based on priority
    		int remPos = ptl.get(priorityRemove);
    		priorityHeap.poll();
    		timeHeap.remove(remPos);
    		return priorityRemove;
    	}
    }

   /**
     * @return maximum wait time
     */
    public Time getMaxWait() {
        return maxWait;
    }

    /**
     * Set the maximum wait time
     *
     * @param time - the maximum wait time
     * @throws NullPointerException if given null time
     */
    public void setMaxWait(Time time) throws NullPointerException {
        if (time == null) {
            throw new NullPointerException();
        }
        maxWait = time;
    }

}
