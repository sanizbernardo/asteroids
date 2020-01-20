package asteroids.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import asteroids.model.Program;
import asteroids.statements.BreakException;
import asteroids.statements.TimerException;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;



/**
 * A class of ships that can turn, fire bullets, accelerate by turning in a thruster, and execute programs.
 * A subclass of Entity.
 * 
 * @invar The orientation of the ship is given by an angle that must remain between 0 and 2 times pi.
 *  | isValidOrientation(this.getOrientation())
 * 
 * @version 3.0
 * @author Bernardo Saniz, Elien Vlaeyen
 */
public class Ship extends Entity {
	
	/**
	 * Creates a new ship with a given position, speed, radius, orientation and mass.
	 * 
	 * @param xpos
	 * 	The position of this ship in the x direction in kilometers
	 * @param ypos
	 * 	The position of this ship in the y direction in kilometers
	 * @param xvel
	 * 	The velocity of this ship in the x direction in kilometers
	 * @param yvel
	 * 	The velocity of this ship in the y direction in kilometers
	 * @param radius
	 * 	The radius of this ship in kilometers
	 * @param orientation
	 * 	The orientation of this ship expressed as an angle between 0 and 2*PI
	 * @param mass
	 *  The mass of this ship expressed in kilograms
	 * 
	 * 
	 * @pre The given orientation must be between 0 and 2*PI.
	 * 	| isValidOrientation(orientation)
	 * @post This ship's x position is equal to the given coordinates if they are valid.
	 * 	| if isValidXPosition(xpos)
	 *  |	then new.getPos().getX() == xpos
	 * @post This ship's y position is equal to the given coordinates if they are valid.
	 * 	| if isValidYPosition(ypos)
	 *  |	then new.getPos().getY() == ypos
	 * @post This ship's x and y velocities are equal to the given values if the total speed is valid.
	 * 		 Otherwise they are rescaled so that the total speed is equal to this ship's maximum speed.
	 *  | if isValidSpeed(Math.sqrt(xvel^2 + yvel^2))
	 *  |	then new.getVel().getX() == xvel && new.getVel().getY() == yvel
	 *  | else new.getTotalSpeed() == this.getMaxSpeed()
	 * @post The radius of this ship is equal to the given value.
	 * 	| if isValidRadius(radius)
	 *  | 	then new.getRadius() == radius	
	 *  | else new.getRadius() == this.getMinRadius() 
	 * @post The orientation of this ship is equal to the given value.				
	 * 	| new.getOrientation() == orientation
	 * @post This ship's mass is set to the given value if it is valid. Otherwise it is set to this ship's minimal mass.
	 *  | if mass < this.getMinMass()
	 *  | 	then new.getMass() == this.getMinMass
	 *  | else new.getMass() == mass
	 * @throws IllegalArgumentException if the given x position or y position is invalid.
	 *  | if ! isValidXPosition(xpos) || ! isValidYPosition(ypos)
	 *  |	then throw new IllegalArgumentException()
	 * @throws IllegalArgumentException if the given radius is invalid.
	 *  | if ! isValidRadius(radius)
	 *  |	then throw new IllegalArgumentException()
	 * @throws IllegalArgumentException if the given orientation is invalid.
	 *  | if ! isValidOrientation(orientation)
	 *  |	then throw new IllegalArgumentException()
	 */
	@Raw
	public Ship(double xpos, double ypos, double xvel, double yvel, double radius, double orientation, double mass) throws IllegalArgumentException {
		
		try{
			this.setXPosition(xpos);
		} catch (IllegalArgumentException e){
			throw e;
		}
		
		try{
			this.setYPosition(ypos);
		} catch (IllegalArgumentException e){
			throw e;

		}
		
		this.setSpeed(xvel, yvel);
		
		try {
			this.setRadius(radius);
		} catch (IllegalArgumentException e) {
			throw e;
		}
		
		if (! isValidOrientation(orientation))
			throw new IllegalArgumentException("Orientation is not valid!");
		
		this.setOrientation(orientation);
		
		this.setMass(mass);
	}
	
	
	
	
	/**
	 * The orientation of the ship expressed as an angle in respect to the x-axis, with 0 corresponding to the right and pi to the left
	 */
	private double orientation;
	
	/**
	 * The smallest value for the orientation of the ship
	 */
	private static final double minAngle = 0;
	
	/**
	 * The largest value allowed for the orientation of the ship
	 */
	private static final double maxAngle = 2* Math.PI;
	
	/**
	 * Returns this ship's orientation.
	 */
	@Basic
	public double getOrientation() {
		return this.orientation;
	}
	
	/**
	 * Returns The minimal value for the orientation of the ship.
	 */
	@Basic @Immutable
	public static final double getMinAngle() {
		return minAngle;
	}
	
	/**
	 * Returns the maximal value for the orientation of the ship.
	 */
	@Basic @Immutable
	public static final double getMaxAngle() {
		return maxAngle;
	}
	
	/**
	 * Check whether the given orientation is valid.
	 * 
	 * @param orientation
	 * 	The orientation to be checked
	 * @return True if and only if the given orientation is between the minimum angle and the maximum angle.
	 * 	| result == (orientation >= getMinAngle()) && (orientation <= getMaxAngle())
	 */
	public boolean isValidOrientation(double orientation) {
		return (orientation >= getMinAngle()) && (orientation <= getMaxAngle());
	}

	/**
	 * Set the orientation of the entity to a given value.
	 * 
	 * @param orientation
	 * 	The new value for the orientation
	 * @pre The orientation must be a value between 0 and 2*pi.
	 * 	| isValidOrientation(orientation)
	 * @post The new orientation is equal to the given value.
	 *  | new.getOrientaion == orientation
	 */
	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}
	
	
	
	
	/**
	 * Check whether the given turn is valid.
	 * 
	 * @param turn
	 * 	The turn to be checked
	 * @return True if and only if the given turn is between -pi and pi.
	 * 	| result == ( (turn >= -Math.PI) && (turn <= Math.PI) )
	 */
	public boolean isValidTurn(double turn){
		return (turn >= -Math.PI) && (turn <= Math.PI);
	}
	
	/**
	 * Change the orientation of the ship by adding a given angle to its current orientation.
	 * 
	 * @param angle
	 * 	The angle over which the ship will turn
	 * @pre The given angle must be a valid value.
	 *  | isValidTurn(angle) == true
	 * @post The new orientation is valid.
	 *  | isValidOrientation(new.getOrientation()) == true
	 * @post If the new angle is smaller than zero, 2*pi is added so the new angle is a valid orientation.
	 *  | if (angle < 0)
	 *  | 	then new.getOrientation() == angle + 2*Math.PI;
	 * @post If the new angle is larger than 2*pi, 2*pi is subtracted from the new angle to get a valid orientation.
	 *  | if (angle > 2*Math.PI)
	 * @post Else, the new orientation is equal to the old orientation plus the given angle. 
	 * 	| new.getOrientation() == angle + this.getOrientation();
	 */
	public void turn(double angle) {
		double newangle = angle + this.getOrientation();
		if (newangle < 0)
			newangle = newangle + 2*Math.PI;
		else if (newangle > 2*Math.PI)
			newangle = newangle - 2*Math.PI;
		this.setOrientation(newangle);
	}
	
	
	
	
	/**
	 * A set containing all the bullets belonging to this ship
	 */
	private Set<Bullet> bulletlist = new HashSet<Bullet>();
	
	/**
	 * Return a set containing all the bullets belonging to this ship.
	 */
	@Basic
	public Set<Bullet> getBulletList() {
		Set<Bullet> shipbullets = new HashSet<Bullet>();
		shipbullets.addAll(bulletlist);
		return shipbullets;
	}

	
	/**
	 * Adds a given bullet to this ship.
	 * 
	 * @param bullet
	 * 	The bullet to be added
	 * @post This ship's bullet list contains the given bullet.
	 * 	| new.getBulletList().contains(bullet)
	 * @post The given bullet has this ship assigned to it.
	 * 	| bullet.setShip(this)
	 * @post The given bullet has the same position as this ship.
	 *  | bullet.getPos().getX() = this.getPos().getX()
	 *  | bullet.getPos().getY() = this.getPos().getY()
	 * @post The given bullet has no world assigned to it.
	 *  | bullet.getWorld() == null
	 * @post The given bullet has this ship set as its source.
	 *  | bullet.getSource() == this
	 */
	// No catch statements for setXPosition or getPos().getY. The ship is always within the boundaries of the world,
	// and the bullet is always smaller than the ship. The bullet's position is therefore always valid.
	@Raw
	public void recoverBullet(Bullet bullet) throws IllegalArgumentException, NullPointerException {
		bullet.setXPosition(this.getPos().getX());
		bullet.setYPosition(this.getPos().getY());
		this.bulletlist.add(bullet);
		bullet.setWorld(null);
		bullet.setShip(this);
		
	}
	
	/**
	 * Adds a given bullet to this ship.
	 * 
	 * @param bullet
	 * 	The bullet to be added
	 * @post This ship's bullet list contains the given bullet.
	 * 	| new.getBulletList().contains(bullet)
	 * @post The given bullet has this ship assigned to it.
	 * 	| bullet.setShip(this)
	 * @post The given bullet has this ship set as its source.
	 *  | bullet.getSource() == this
	 * @throws IllegalArgumentException if the given bullet is already in a world or ship.
	 *  | if bullet.getWorld() != null || bullet.getShip() != null
	 *  |	then throw new IllegalArgumentException()
	 * @throws IllegalArgumentException if the bullet is not within the bounds of this ship.
	 *  | if this.getDistanceCenters(bullet) > this.getRadius() - bullet.getRadius()
	 *  |	then throw new IllegalArgumentException()
	 */
	public void addBullet(Bullet bullet) throws IllegalArgumentException {
		if (this.getDistanceCenters(bullet) > this.getRadius() - bullet.getRadius())
			throw new IllegalArgumentException("Bullet is not within bounds of ship!");
		if (bullet.getWorld() != null || bullet.getShip() != null)
			throw new IllegalArgumentException("Bullet is already assigned");
		this.bulletlist.add(bullet);
		bullet.setShip(this);
		bullet.setSource(this);
	}
	
	/**
	 * Adds a collection of bullets to this ship.
	 * 
	 * @param bullets
	 * 	The collection of bullets to be added.
	 * @effect Adds each bullet in the collection to the ship.
	 * 	| for each (bullet : bullets)
	 *  | 	this.addbullet(bullet)
	 */
	public void addSeveralBullets(Collection<Bullet> bullets) {
		for (Bullet bullet : bullets) 
			try {
				this.addBullet(bullet);
			} catch (IllegalArgumentException e) {
				continue;
			} catch (NullPointerException n) {
				for(Bullet wrong : bullets)
					this.removeBullet(wrong);
			}
	}

	/**
	 * Removes a given bullet from this ship, if it is present in the ship.
	 * 
	 * @param bullet
	 * 	The bullet to be removed
	 * @post The ship does not contain the given bullet.
	 * 	| new.bulletlist.contains(bullet) == false
	 * @post The given bullet does not have a ship assigned to it.
	 *  | bullet.getShip == null
	 * @throws IllegalArgumentException If the ship does not contain the given bullet.
	 *  | if bullet.getShip() != this
	 *  | 	then throw new IllegalArgumentException()
	 */
	public void removeBullet(Bullet bullet) throws IllegalArgumentException{
		if (bullet.getShip() != this)
			throw new IllegalArgumentException("Bullet does not belong to this ship!");
		bullet.setShip(null);
		this.bulletlist.remove(bullet);
	}
	
	/**
	 * Gives a given bullet the correct initial position and velocity to be fired.
	 * 
	 * @param bullet
	 *  The bullet to be fired
	 * @post The bullet's total speed is equal to 250.
	 *  | bullet.getTotalSpeed() == 250
	 * @post The bullet is touching the ship.
	 *  | new.getDistancebetween(bullet) = this.getRadius() + bullet.getRadius()
	 * @throws IllegalArgumentException if the bullet is outside the bounds of the world.
	 *  | if ! bullet.isInBoundaries(this.getWorld())
	 *  | 	then throw new IllegalArgumentException()
	 */
	@Raw
	public void prepareToFireBullet(Bullet bullet) throws IllegalArgumentException {
		bullet.setXPosition(this.getPos().getX() + (bullet.getRadius() + this.getRadius())*Math.cos(this.getOrientation()));
		bullet.setYPosition(this.getPos().getY() + (bullet.getRadius() + this.getRadius())*Math.sin(this.getOrientation()));
		bullet.setSpeed(250*Math.cos(this.getOrientation()), 250*Math.sin(this.getOrientation()));
		if(! bullet.isInBoundaries(this.getWorld()))
			throw new IllegalArgumentException("Bullet is out of boundaries!");
	}
	
	/**
	 * Fires a bullet from this ship.
	 * 
	 * @effect A bullet from this ship is placed in the correct position to be fired.
	 *  | this.prepareToFireBullet(this.getBulletList().iterator().next())
	 * @post The fired bullet's hit count will be 3.
	 *  | (new firedbullet).getHitCount() == 3
	 * @post This ship's world contains the fired bullet.
	 *  | this.getWorld().contains(firedbullet) == true
	 * @post If this method would place the bullet outside of this ship's world, the bullet is terminated.
	 *  | if ! isValidXPosition(this.getWorld(), (new bullet).getPos().getX) ||
	 *  |		! isValidYPosition(this.getWorld(), (new bullet).getPos().getY)
	 *  |	then (new bullet).isTerminated() == true
	 * @throws IllegalArgumentException if this ship is not in a world.
	 *  | if this.getWorld() == null
	 *  |	then throw new IllegalArgumentException()
	 * @throws IllegalArgumentException if there are no bullets loaded onto this ship.
	 *  | if this.getBulletList().isEmpty()
	 *  | 	then throw new IllegalArgumentException()
	 */
	@Raw
	public void fireBullet() throws IllegalArgumentException {
		if (this.getWorld() == null)
			throw new IllegalArgumentException("Ship is not in a world!");
		if (this.getBulletList().isEmpty())
			throw new IllegalArgumentException("No bullets loaded!");
		Bullet bullet = this.getBulletList().iterator().next();
		try{
			this.prepareToFireBullet(bullet);
		} catch (IllegalArgumentException e){
			bullet.terminate();
			return;
		}
		bullet.setHitCount(3);
		this.getWorld().addEntity(bullet);
	}

	
	
	
	/**
	 * True when the ship's thruster is turned on
	 */
	private boolean thruster;
	
	/**
	 * Returns whether the ship's thruster is active or not.
	 */
	@Basic
	public boolean checkThruster() {
		return this.thruster;
	}

	/**
	 * Activates the ship's thruster.
	 * 
	 * @post the thruster is active.
	 *  | new.checkThruster() == true
	 */
	public void thrustOn() {
		this.thruster = true;
	}

	/**
	 * Deactivates the ship's thruster.
	 * 
	 * @post the thruster is not active.
	 *  | new.checkThruster() == false
	 */
	public void thrustOff() {
		this.thruster = false;
	}

	
	
	
	/**
	 * The force exerted by the ship's thruster
	 */
	private final double force = 1.1e18;
	
	/**
	 * Returns the force exerted by the ship's thruster.
	 */
	@Basic @Immutable
	public double getForce() {
		return this.force;
	}

	/**
	 * Returns the mass of this ship and all bullets loaded onto it.
	 * 
	 * @return The mass of this ship and all bullets loaded onto it.
	 *  | result == this.getMass() + bullet.getMass() for each (bullet : this.getBulletList())
	 */
	public double getTotalMass() {
		double bullets = 0;
		for (Bullet bullet : this.getBulletList())
			bullets += bullet.getMass();
		return (this.getMass() + bullets);
	}
	
	/**
	 * Returns the acceleration of the ship depending on its mass and the force of its thruster.
	 * 
	 * @return Zero if this ship's thruster is deactivated.
	 *  | if this.checkThruster() == false
	 *  | 	then result == 0
	 * @return The acceleration achieved by the ship's thruster.
	 *  | result == this.getForce()/this.getTotalMass()
	 */
	public double getAcceleration() {
		if (! this.checkThruster())
			return 0;
		double a = this.getForce()/this.getTotalMass();
		if (a < 0)
			a = 0;
		return a;
	}
	
	
	
	/**
	 * Increases this ship's speed depending on its acceleration and a given time duration.
	 * 
	 * @param time
	 *  The time duration for the acceleration
	 * @post This ship's speed is valid.
	 *  | isValidSpeed(new.getTotalspeed()) == true
	 * @post This ship's speed is equal to its old speed plus its acceleration multiplied by the time duration.
	 *  | new.getTotalSpeed() == this.getTotalSpeed() + this.getAcceleration*time
	 */
	public void accelerate(double time) {
		double a = this.getAcceleration();
		double newxspeed = this.getVel().getX() + time*a*Math.cos(this.getOrientation());
		double newyspeed = this.getVel().getY() + time*a*Math.sin(this.getOrientation());
		this.setSpeed(newxspeed, newyspeed);
	}
	
	/**
	 * Changes this ship's position depending on its speed and a given time duration.
	 * 
	 * @param time
	 *  The time duration for the movement
	 * @post This ship's new x position is equal to its old x position plus its x velocity multiplied by the time duration.
	 *  | new.getPos().getX() == this.getPos().getX() + this.getVel().getX()*time
	 * @post This ship's new y position is equal to its old y position plus its y velocity multiplied by the time duration.
	 *  | new.getPos().getY() == this.getPos().getY() + this.getVel().getY()*time
	 * @post All the bullets in this ship are at the same position as this ship.
	 *  | for bullet : this.getBulletList()
	 *  |	(new bullet).getPos().getX == new.getPos().getX() && (new bullet).getPos().getY == new.getPos().getY()
	 * @effect This ship's speed will have changed depending on the time duration.
	 *  | this.accelerate(time)
	 * @throws IllegalArgumentException If the given time is not a positive value.
	 *  | if time < 0
	 *  | 	then throw new IllegalArgumentException()
	 */
	@Override
	public void move(double time) throws IllegalArgumentException {
		super.move(time);
		this.accelerate(time);
		for (Bullet bullet : this.getBulletList()) {
			bullet.setXPosition(this.getPos().getX());
			bullet.setYPosition(this.getPos().getY());
		}	
	}
	
	/**
	 * Resolves a collision between this ship and another entity. If the other entity is a ship,
	 * they will bounce off of each other. If the other entity is a bullet, both entities will be terminated.
	 * 
	 * @param other
	 *  The entity with which this ship is colliding.
	 * @post If the other entity is a ship, momentum is conserved.
	 *  | if other instanceof Ship
	 *  | 	then new.getTotalspeed()*this.getTotalMass + othernew.getTotalspeed()*other.getTotalMass() ==
	 *  | 		this.getTotalspeed()*this.getTotalMass + other.getTotalspeed()*other.getTotalMass()
	 * @effect If the other entity is not a ship, the collision will be handled according to the rules of that entity.
	 *  | if ! other instanceof Ship
	 *  |	then other.collide(this)
	 */
	@Override
	public void collide(Entity other) {
		if (other instanceof Ship) {
			double sigma = this.getRadius() + other.getRadius();
			double dx = other.getXDifference(this);
			double dy = other.getYDifference(this);
			double dvx = other.getVel().getX() - this.getVel().getX();
			double dvy = other.getVel().getY() - this.getVel().getY();
			double dvr = dvx*dx + dvy*dy;
		
			double j = (2*this.getTotalMass()*((Ship)other).getTotalMass()*dvr)/(sigma*(this.getTotalMass() + ((Ship)other).getTotalMass()));
			double jx = j*dx/sigma;
			double jy = j*dy/sigma;
		
			this.setSpeed(this.getVel().getX() + jx/this.getTotalMass(), this.getVel().getY() + jy/this.getTotalMass());
			other.setSpeed(other.getVel().getX() - jx/((Ship)other).getTotalMass(), other.getVel().getY() - jy/((Ship)other).getTotalMass());
		}
		
		else
			other.collide(this);
	}
	
	/**
	 * Terminates this ship.
	 * 
	 * @post This ship is no longer assigned to a world.
	 *  | new.getWorld() == null
	 * @post This ship has no bullets loaded onto it.
	 *  | new.getBulletList().isEmpty() == true
	 * @post This ship is terminated.
	 *  | new.isTerminated == true
	 */
	public void terminate() {
		if (this.getWorld() != null)
			this.getWorld().removeEntity(this);
		for (Bullet bullet : this.getBulletList())
			bullet.setShip(null);
		this.bulletlist.clear();
		this.terminated = true;
	}
	
	
	
	
	/**
	 * A list containing the objects that were printed during the execution of this ship's program
	 */
	private List<Object> printeditems = new ArrayList<Object>();
	
	/**
	 * Returns a list containing the objects that were printed during the execution of this ship's program.
	 */
	@Basic
	public List<Object> getItems() {
		return this.printeditems;
	}
	
	/**
	 * The program loaded onto this ship
	 */
	private Program program;
	
	/**
	 * Returns the program currently loaded onto this ship.
	 */
	@Basic
	public Program getProgram() {
		return this.program;
	}
	
	/**
	 * Loads the given program onto this ship.
	 * 
	 * @param program
	 *  The program to be loaded
	 * @post The given program will be loaded onto this ship.
	 *  | new.getProgram() == program
	 */
	public void setProgram(Program program) {
		this.program = program;
		program.setShip(this);
	}
	
	/**
	 * Runs the program loaded on this ship for a given time duration.
	 * 
	 * @param time
	 *  The time duration for the execution of the program
	 * @return The list of objects printed during the execution of this ship's program.
	 *  | result == this.getItems()
	 * @throws BreakException When a break statement called incorrectly.
	 */
	public List<Object> doProgram(double time) throws BreakException {
		this.getProgram().getTimer().addTime(time);
		try {
		this.getProgram().run();
		return this.getItems();
		} catch (TimerException e) {
			return null;
		}
	}
	
}
