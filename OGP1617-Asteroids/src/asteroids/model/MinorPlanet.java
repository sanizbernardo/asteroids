package asteroids.model;

/**
 * A class of minor planets which bounce elastically off of each other. A subclass of Entity
 * 
 * @version 1.0
 * @author Bernardo Saniz, Elien Vlaeyen
 */
public abstract class MinorPlanet extends Entity {

	/**
	 * Resolves a collision between this minor planet and another.
	 * 
	 * @param other
	 * 	The minor planet that this minor planet will collide with.
	 * @post Momentum is conserved.
	 *  | new.getTotalspeed()*this.getTotalMass + (new other).getTotalspeed()*other.getTotalMass() ==
	 *  | 	this.getTotalspeed()*this.getTotalMass + other.getTotalspeed()*other.getTotalMass()
	 */
	public void minorCollide(MinorPlanet other) {
		double sigma = this.getRadius() + other.getRadius();
		double dx = other.getXDifference(this);
		double dy = other.getYDifference(this);
		double dvx = other.getVel().getX() - this.getVel().getX();
		double dvy = other.getVel().getY() - this.getVel().getY();
		double dvr = dvx*dx + dvy*dy;
	
		double j = (2*this.getMass()*((MinorPlanet)other).getMass()*dvr)/(sigma*(this.getMass() + ((MinorPlanet)other).getMass()));
		double jx = j*dx/sigma;
		double jy = j*dy/sigma;
	
		this.setSpeed(this.getVel().getX() + jx/this.getMass(), this.getVel().getY() + jy/this.getMass());
		other.setSpeed(other.getVel().getX() - jx/((MinorPlanet)other).getMass(), other.getVel().getY() - jy/((MinorPlanet)other).getMass());
	}
	
}
