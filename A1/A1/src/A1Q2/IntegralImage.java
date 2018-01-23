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

    /**
     * Constructs an integral image from the given input image.  
     *
     * @author jameselder
     * @param image The image represented
     * @throws InvalidImageException Thrown if input array is not rectangular
     */
    public IntegralImage(int[][] image) throws InvalidImageException {
        this.imageHeight = image.length;					// set height
        this.imageWidth = image[0].length;				// set width
        for(int i = 0; i < this.imageHeight; i++){		// compare row lengths for equality
        		if(this.imageWidth != image[i].length){		// if uneven, throw exception
        			throw new InvalidImageException();
        		}
        }
        for(int i = 0; i < this.imageHeight; i++){
        		for(int j = 0; j < this.imageWidth; j++){
        		}
        }
        this.integralImage = image.clone();
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
    		if ((!(top <= bottom)) || !(left <= right)){		// exception check
    			throw new NullSubImageException();
    		}
    		if ((top < 0) || (bottom > this.imageHeight) || (left < 0) || (right > this.imageWidth)){
    			throw new BoundaryViolationException();		// exception check
    		}
    		return 0; //dummy value - remove once coded.
    }
}
