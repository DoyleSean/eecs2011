package A1Q1;
import java.util.*;
/**
 *
 * @author jameselder
 */
public class testSparseNumericVector {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SparseNumericVector X = new SparseNumericVector();
        SparseNumericVector Y = new SparseNumericVector();
        double projection;
        print(X);
        X.add(new SparseNumericElement(150000, 3.1415));
        print(X);
        X.add(new SparseNumericElement(15, 3));
        print(X);
        X.add(new SparseNumericElement(1500, 3.14));
        print(X);
        X.add(new SparseNumericElement(150, 3.1));
        print(X);
        X.add(new SparseNumericElement(15000, 3.141));
        print(X);
        Y.add(new SparseNumericElement(150000, 1));
        print(Y);
        Y.add(new SparseNumericElement(15, 1));
        print(Y);
        System.out.println("TESTER: PRINT OF X:" + X);
        System.out.println("TESTER: PRINT OF Y:" + Y);
        X.remove((long) 150);
        System.out.println("TESTER: PRINT OF X rem:" + X);
        projection = X.dot(Y);

        System.out.println("The inner product of");
        System.out.print(X);
        System.out.println("and");
        System.out.print(Y);
        System.out.println("is ");
        System.out.printf("%.5f\n\n",projection); //answer should be 3*1 + 3.1415*1 = 6.1415
     }
    
    public static void print(SparseNumericVector p) {
    	Iterator<SparseNumericElement> priterator = p.iterator();
    	while(priterator.hasNext()) {
    		SparseNumericElement e = priterator.next();
    		System.out.println("IDX: " + e.getIndex() + " VAL: " + e.getValue());
    	}
    }
}