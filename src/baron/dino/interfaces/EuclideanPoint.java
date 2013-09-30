package baron.dino.interfaces;

/**
 * This package represents a point in R^n space
 * @author maplebaconburgr
 *
 */
public interface EuclideanPoint {

	/**
	 * Returns a vector of n elements indicating where in space this element is
	 * @return
	 */
	public double[] getCoordinates();
	
	/**
	 * Returns the distance between this point and another point
	 * @param point
	 * @return
	 */
	public double getDistance(EuclideanPoint point);
	
}
