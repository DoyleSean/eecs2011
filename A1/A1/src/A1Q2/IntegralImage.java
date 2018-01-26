package A1Q2;

/**
 * Represents an integer integral image, which allows the user to query the mean
 * value of an arbitrary rectangular subimage in O(1) time.  Uses O(n) memory,
 * where n is the number of pixels in the image.
 *
 * @author jameselder
 */
public class IntegralImage {

	
    private final int[][] integralImage;
    private final int imageHeight; // height of image (first index)
    private final int imageWidth; // width of image (second index)
    private int topSum, leftSum, diagSum, directSum;

    /**
     * Constructs an integral image from the given input image.  
     *
     * @author jameselder
     * @param image The image represented
     * @throws InvalidImageException Thrown if input array is not rectangular
     */
    public IntegralImage(int[][] image) throws InvalidImageException {
    	// exception handling
        this.imageHeight = image.length;							// set height
        this.imageWidth = image[0].length;							// set width
        for(int i = 0; i < this.imageHeight; i++){					// compare row lengths for equality
        		if(this.imageWidth != image[i].length){				// if uneven, throw exception
        			throw new InvalidImageException();
        		}
        }
        
        // summed area table functionality 
        this.integralImage = new int[this.imageHeight][this.imageWidth];
        for(int i = 0; i < this.imageHeight; i++){
        		for(int j = 0; j < this.imageWidth; j++){
        			boolean diagTop= false;							// track if theres room above 
        			boolean diagLeft = false;						// track if theres room to the left
        			this.directSum = image[i][j];					// direct value to add to the average from the image
        			if(i==0) {										// check if we're on an edge
        				this.topSum = 0;							// top edges have no top space to sum in
        				this.diagSum = 0;							// edges dont have diagonal spaces
        			} else {
        				diagTop = true;								// theres room in the top dimension for a diagonal to exist
        				this.topSum = this.integralImage[i-1][j];	// top sum is placed into the integral image
        			}
        			if(j==0) {										// check if we're on an edge
        				this.leftSum = 0;
        				this.diagSum = 0;
        			} else {
        				diagLeft = true;
        				this.leftSum = this.integralImage[i][j-1];
        			}
        			if(diagLeft && diagTop) {							// if theres room in the vertical and horiz dimension
        				this.diagSum = this.integralImage[i-1][j-1];	// factor in the diagonal into the sum
        			} else {
        				this.diagSum = 0;
        			}
        			
        			this.integralImage[i][j] = leftSum + topSum - diagSum + image[i][j]; // tabulation into the bottom right
        		}
        }
       
    }

    /**
     * Returns the mean value of the rectangular sub-image specified by the
     * top, bottom, left and right parameters. The sub-image should include
     * pixels in rows top and bottom and columns left and right.  For example,
     * top = 1, bottom = 2, left = 1, right = 2 specifies a 2 x 2 sub-image starting
     * at (top, left) coordinate (1, 1).  
     *
     * @author jameselder
     * @param top top row of sub-image
     * @param bottom bottom row of sub-image
     * @param left left column of sub-image
     * @param right right column of sub-image
     * @return 
     * @throws BoundaryViolationException if image indices are out of range
     * @throws NullSubImageException if top > bottom or left > right
     */
    public double meanSubImage(int top, int bottom, int left, int right) throws BoundaryViolationException, NullSubImageException {
    		// exception handling
    		if ((!(top <= bottom)) || !(left <= right)){		// exception check
    			throw new NullSubImageException();
    		}
    		if ((top < 0) || (bottom > this.imageHeight) || (left < 0) || (right > this.imageWidth)){
    			throw new BoundaryViolationException();		// exception check
    		}
    		
    		// compute sum and samples (i.e. pixels), return average
    		int samples = (bottom - top + 1) * (right - left + 1);
    		
            double sum = this.integralImage[top-1][left-1] - this.integralImage[top-1][right] - this.integralImage[bottom][left-1] + this.integralImage[bottom][right];
    		double average = sum / samples;
    		return average; 
    }
}
