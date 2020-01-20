package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Value;

/**
 * A class of vectors containing an x coordinate and a y coordinate
 * 
 * @invar The x and y coordinates must be real numbers.
 *  | isValidValue(getX()) && isValidValue(getY())
 * 
 * @version 2.0
 * @author Bernardo Saniz, Elien Vlaeyen
 *
 */
@Value
public class Vector {
	
	/**
	 * Initializes this vector with a given x and y coordinate.
	 * 
	 * @param x
	 *  The given x coordinate
	 * @param y
	 *  The given y coordinate 
	 */
	@Raw
	public Vector(double x, double y) {
		if (! (isValidValue(getX()) && isValidValue(getY())))
			throw new IllegalArgumentException("Non-valid coordiante");
		this.x = x;
		this.y = y;
	}
	
	
	
	
	/**
	 * The x value of this vector
	 */
	private final double x;
	
	/**
	 * Returns the x value of this vector.
	 */
	@Basic @Raw @Immutable
	public double getX() {
		return this.x;
	}

	
	
	
	/**
	 * The y value of this vector
	 */
	private final double y;
	
	/**
	 * Returns the y position of this entity.
	 */
	@Basic @Raw @Immutable
	public double getY() {
		return this.y;
	}
	
	/**
	 * Checks whether the given value is a real number.
	 * 
	 * @param value
	 *  The value to be checked
	 * @return True if the given value is finite.
	 *  | result == Double.isFinite(value)
	 */
	public boolean isValidValue(double value) {
		return Double.isFinite(value);
	}
	
	
	
	/**
	 * Calculates the vector sum of this vector with a given vector.
	 * 
	 * @param other
	 *  The vector to be summed with this vector
	 * @return The vector sum of the two vectors.
	 *  | newx = this.getX() + other.getX()
	 *  | newy = this.getY() + other.getY()
	 *  | result == new Vector(newx , newy)
	 */
	public Vector add(Vector other) {
		return new Vector(this.getX() + other.getX(), this.getY() + other.getY());
	}
	
	/**
	 * Returns the magnitude of this vector.
	 * 
	 * @return The magnitude of this vector.
	 * 	| result == sqrt(this.getX()^2 + this.getY()^2)
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2));
	}
	
	
	/**
	 * Checks whether this vector is equal to a given object.
	 * 
	 * @param other
	 *  The object to be checked
	 * @return True if and only if the given object is a vector with the same coordinates as this vector.
	 *  result == other instanceof Vector &&
	 *  		  other.getX() == this.getX() &&
	 *  		  other.getY() == this.getY()
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		else if (! (other instanceof Vector))
			return false;
		else
			return (this.getX() == ((Vector)other).getX() && this.getY() == ((Vector)other).getY());
	}
	
	/**
	 * Returns the hash code for this vector. 
	 */
	@Override
	public int hashCode() {
		return Double.hashCode(getX()) + Double.hashCode(getY());
	}
	
	/**
	 * Returns a string representing this vector.
	 * 
	 * @return A string consisting of this veccor's x and y coordinates separated by a comma and enclosed in parentheses.
	 *  | result == "(" + Double.toString(getX()) + "," + Double.toString(getY()) + ")"
	 */
	@Override
	public String toString() {
		return "(" + Double.toString(getX()) + "," + Double.toString(getY()) + ")";
	}
	
}
