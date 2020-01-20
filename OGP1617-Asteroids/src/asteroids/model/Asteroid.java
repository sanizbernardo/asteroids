package asteroids.model;

import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of asteroids that destroy ships when they touch them.
 * A subclass of MinoPlanet.
 * 
 * @version 1.0
 * @author Bernardo Saniz, Elien Vlaeyen
 */
public class Asteroid extends MinorPlanet {

	
	/**
	 * Creates a asteroid with the given position, velocity, and radius.
	 * 
	 * @param xpos
	 *  The new position in the x direction
	 * @param ypos
	 *  The new position in the y direction
	 * @param xvel
	 *  The new velocity in the x direction
	 * @param yvel
	 *  The new velocity in the y direction
	 * @param radius
	 *  The new radius
	 *  
	 * @post This asteroid's x position is equal to the given coordinates if they are valid.
	 * 	| if isValidXPosition(xpos)
	 *  |	then new.getXPosition() == xpos
	 * @post This asteroid's y position is equal to the given coordinates if they are valid.
	 * 	| if isValidYPosition(ypos)
	 *  |	then new.getYPosition() == ypos
	 * @post This asteroid's x and y velocities are equal to the given values if the total speed is valid.
	 * 		 Otherwise they are rescaled so that the total speed is equal to this asteroid's maximum speed.
	 *  | if isValidSpeed(Math.sqrt(xvel^2 + yvel^2))
	 *  |	then new.getXVelocity() == xvel && new.getYVelocity() == yvel
	 *  | else new.getTotalSpeed() == this.getMaxSpeed()
	 * @post The radius of this asteroid is equal to the given value.
	 * 	| if isValidRadius(radius)
	 *  | 	then new.getRadius() == radius
	 * @post The mass of this asteroid is equal to the minimum mass of an asteroid.
	 *  | new.getMass() == this.getMinMass
	 * @throws IllegalArgumentException if the given x position or y position is invalid.
	 *  | if ! isValidXPosition(xpos) || ! isValidYPosition(ypos)
	 *  |	then throw new IllegalArgumentException()
	 * @throws IllegalArgumentException if the given radius is invalid.
	 *  | if ! isValidRadius(radius)
	 *  |	then throw new IllegalArgumentException()
	 */
	@Raw
	public Asteroid(double xpos,double ypos, double xvel, double yvel, double radius) {
		
		try{
			this.setXPosition(xpos);
		} catch (IllegalArgumentException e) {
			throw e;
		}
		
		try{
			this.setYPosition(ypos);
		} catch (IllegalArgumentException e) {
			throw e;
		}
		
		this.setSpeed(xvel, yvel);
		
		try {
			this.setRadius(radius);
		} catch (IllegalArgumentException e) {
			throw e;
		}		
		this.setMass(this.getMinMass());
	}
	
	/**
	 * Resolves a collision between this asteroid and another entity.
	 * 
	 * @param other
	 *  The entity that this asteroid will collide with.
	 * @post If the other entity is a ship, the ship will be terminated.
	 *  | if other instanceof Ship
	 *  |	then (new other).isTerminated() == true
	 * @post If the other entity is a bullet, both this asteroid and the bullet are terminated.
	 *  | if other instanceof Bullet
	 *  |	then new.isTerminated() == true && (new other).isTerminated() == true
	 * @effect If the other entity is a minor planet, a minor planet collision will be handled.
	 *  | if other instanceof MinorPlanet
	 *  |	this.minorCollide(other)
	 */
	@Override
	public void collide(Entity other) {
		if (other instanceof MinorPlanet)
			this.minorCollide((MinorPlanet)other);
		else if (other instanceof Ship)
			other.terminate();
		else if (other instanceof Bullet)
			other.collide(this);
	}
	
	/**
	 * Terminates this asteroid.
	 * 
	 * @post This asteroid is no longer assigned to a world.
	 *  | new.getWorld() == null
	 * @post This asteroid is terminated.
	 *  | new.isTerminated == true
	 */
	@Override
	public void terminate(){
		if (this.getWorld() != null)
			this.getWorld().removeEntity(this);
		this.terminated = true;
	}
	


}
