package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of bullets that can be loaded onto and fired by Ships. A subclass of Entity.
 * Bullets destroy other entities when they touch them.
 * 
 * @invar Each bullet belongs in either a ship or a world, but not both.
 * 
 * @version 2.0
 * @author Bernardo Saniz, Elien Vlaeyen
 */
public class Bullet extends Entity {
	
	/**
	 * Creates a bullet with the given position, given velocity, and its radius equal to the minimum radius.
	 * 
	 * @param xpos
	 *  The new position in the x direction
	 * @param ypos
	 *  The new position in the y direction
	 * @param xvel
	 *  The new velocity in the x direction
	 * @param yvel
	 *  The new velocity in the y direction
	 *  
	 * @post This bullet's x position is equal to the given coordinates if they are valid.
	 * 	| if isValidXPosition(xpos)
	 *  |	then new.getPos().getX() == xpos
	 * @post This bullet's y position is equal to the given coordinates if they are valid.
	 * 	| if isValidYPosition(ypos)
	 *  |	then new.getPos().getY() == ypos
	 * @post This bullet's x and y velocities are equal to the given values if the total speed is valid.
	 * 		 Otherwise they are rescaled so that the total speed is equal to this bullet's maximum speed.
	 *  | if isValidSpeed(Math.sqrt(xvel^2 + yvel^2))
	 *  |	then new.getVel().getX() == xvel && new.getVel().getY() == yvel
	 *  | else new.getTotalSpeed() == this.getMaxSpeed()
	 * @post The radius of the bullet is equal to the minimum value for its radius.
	 * 	| new.getRadius() == this.getMinRadius()
	 * @post This bullet's mass is equal to the minimum mass for a bullet with the given radius.
	 *  | new.getMass() == this.getMinMass()
	 * @throws IllegalArgumentException if the given x position or y position is invalid.
	 *  | if ! isValidXPosition(xpos) || ! isValidYPosition(ypos)
	 *  |	then throw new IllegalArgumentException()
	 */
	@Raw
	public Bullet(double xpos,double ypos, double xvel, double yvel) {
		
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
		
		this.setRadius(this.getMinRadius());
		
		this.setMass(this.getMinMass());
		
	}
	
	/**
	 * Creates a bullet with the given position, velocity, and radius.
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
	* @post This bullet's x position is equal to the given coordinates if they are valid.
	 * 	| if isValidXPosition(xpos)
	 *  |	then new.getPos().getX() == xpos
	 * @post This bullet's y position is equal to the given coordinates if they are valid.
	 * 	| if isValidYPosition(ypos)
	 *  |	then new.getPos().getY() == ypos
	 * @post This bullet's x and y velocities are equal to the given values if the total speed is valid.
	 * 		 Otherwise they are rescaled so that the total speed is equal to this bullet's maximum speed.
	 *  | if isValidSpeed(Math.sqrt(xvel^2 + yvel^2))
	 *  |	then new.getVel().getX() == xvel && new.getVel().getY() == yvel
	 *  | else new.getTotalSpeed() == this.getMaxSpeed()
	 * @post The radius of this bullet is equal to the given value.
	 * 	| if isValidRadius(radius)
	 *  | 	then new.getRadius() == radius
	 * @post This bullet's mass is equal to the minimum mass for a bullet with the given radius.
	 *  | new.getMass() == this.getMinMass()
	 * @throws IllegalArgumentException if the given x position or y position is invalid.
	 *  | if ! isValidXPosition(xpos) || ! isValidYPosition(ypos)
	 *  |	then throw new IllegalArgumentException()
	 * @throws IllegalArgumentException if the given radius is invalid.
	 *  | if ! isValidRadius(radius)
	 *  |	then throw new IllegalArgumentException()
	 */
	@Raw
	public Bullet(double xpos,double ypos, double xvel, double yvel, double radius) {
		
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
	 * The ship to which this bullet belongs, if any
	 */
	private Ship ship;
	
	/**
	 * Returns the ship to which this bullet belongs, if any.
	 * 
	 * @return The ship that this bullet is assigned to.
	 *  | result == this.ship
	 */
	@Basic
	public Ship getShip() {
		return this.ship;
	}
	
	/**
	 * Places the bullet in a given ship.
	 * 
	 * @param ship
	 * 	The ship that this bullet will be placed in
	 * @post The bullet belongs to the given ship.
	 * 	| new.getShip() == ship
	 */
	public void setShip(Ship ship) {
		this.ship = ship;
	}

	
	
	
	/**
	 * The ship that last contained this bullet, if any
	 */
	private Ship source;

	/**
	 * Returns the ship that last contained this bullet, if any.
	 * 
	 * @return The ship that last contained this bullet.
	 *  | result == this.source
	 */
	@Basic
	public Ship getSource() {
		return this.source;
	}

	/**
	 * Sets the bullet's source to a given ship.
	 * 
	 * @param ship
	 * 	The ship that fired the bullet
	 * @post The bullet's source is set to the given ship.
	 * 	| new.getSource() == ship
	 */
	public void setSource(Ship ship) {
		this.source = ship;
	}

	
	
	
	/**
	 * Returns the world that this bullet belongs to, if any.
	 * 
	 * @return The world that this bullet is assigned to.
	 *  | result == this.world
	 */
	@Basic
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Places this bullet in the given world.
	 * 
	 * @param world
	 *  The world this bullet will be placed in
	 * @post This bullet is assigned to the given world.
	 *  | new.getWorld() == world
	 * @post If this bullet overlapped with an entity, both are terminated
	 *  | if this.overlap(entity) && ! this.equals(entity)
	 *  | 	then new.isTerminated() && (new other).isTerminated()
	 * @throws IllegalArgumentException if this bullet is not within the boundaries of the given world.
	 *  | if ! this.isInBoundaries(world)
	 *  |	then throw new IllegalArgumentException()
	 * @throws IllegalArgumentException if this bullet is terminated.
	 *  | if this.isTerminated
	 *  |   then throw new IllegalArgumentException()
	 * @throws IllegalArgumentException if this bullet does not originate from a ship and would overlap with another entity.
	 *  | if this.getSource() == null
	 *  |   then throw new IllegalArgumentException()
	 */
	public void setWorld(World world) throws IllegalArgumentException {
		if (this.isTerminated())
			throw new IllegalArgumentException("Bullet is dead");
		if (! this.isInBoundaries(world))
			throw new IllegalArgumentException("The bullet is out of bounds!");
		if (this.getShip() != null)
			this.getShip().removeBullet(this);
		this.world = world;
		if (world == null)
			return;
		Entity collided = null;
		for (Entity entity : world.getEntityList())
			// No catch for overlap. The entities are in the same world. 
			if (this.overlap(entity) && (! this.equals(entity)))
				if (this.getSource() == null)
					throw new IllegalArgumentException("Bullet could not be added!");
				else
					collided = entity;
		if (collided != null)
			this.collide(collided);
	}
	
	
	/**
	 * The number of times this bullet can collide with a wall
	 */
	private int hitcount = 3;

	/**
	 * Returns the number of boundary collisions this bullet can withstand before being terminated.
	 * 
	 * @return The number of boundary collisions this bullet can withstand.
	 *  | result == this.hitcount
	 */
	@Basic
	public int getHitCount() {
		return this.hitcount;
	}
	
	/**
	 * Sets the number of boundary collisions this bullet can withstand to the given integer value.
	 * 
	 * @param value
	 *  The value to be set
	 * @post The bullet's hit count will be equal to the given value.
	 *  | new.getHitCount() == value
	 */
	public void setHitCount(int value) {
		this.hitcount = value;
	}
	
	
	
	
	
	/**
	 * Resolves a collision between this bullet and another entity. If the other entity is this bullet's source,
	 * this bullet will be placed within it. Otherwise both entities will be terminated.
	 * 
	 * @param other
	 *  The entity with which this bullet is colliding.
	 * @post If the other entity is this bullet's source, this bullet will be placed within it.
	 *  | if other == this.getSource()
	 *  | 	then new.getWorld() == null && new.getShip() == other && (new other).getBulletList().contains(this)
	 *  |		&& new.getHitCount() == 3
	 * @post If the other entity is not this bullet's source, both entities are terminated.
	 *  | if other != this.getSource()
	 *  | 	then new.isTerminated() && (new other).isTerminated()
	 */
	@Override
	public void collide(Entity other) {
		if (this.getSource() == null) {
			other.terminate();
			this.terminate();
		}
		else if (this.getSource().equals(other)) {
			if (this.getHitCount() == 3)
				return;
			this.getWorld().removeEntity(this);
			((Ship) other).recoverBullet(this);
		}
		else {
			other.terminate();
			this.terminate();
		}
	}
	
	/**
	 * Resolves a collision between this bullet and the edge of the given world.
	 * 
	 * @param world
	 *  The world with which this bullet will collide
	 * @post This bullet's hit count will be diminished by 1.
	 *  | new.getHitCount() == this.getHitCount() - 1
	 * @effect This bullet will bounce off of the boundary of the given world.
	 *  | ((Entity) this).collide(world)
	 */
	public void collide(World world) {
		this.setHitCount(this.getHitCount() - 1);
		if (this.getHitCount() == 0) {
			this.terminate();
			return;
		}
		super.collide(world);
	}
	
	
	/**
	 * Terminates this bullet.
	 * 
	 * @post This bullet is no longer assigned to a world.
	 *  | new.getWorld() == null
	 * @post This bullet is no longer assigned to a ship.
	 *  | new.getShip() == null
	 * @post This bullet is terminated.
	 *  | new.isTerminated == true
	 */
	public void terminate() {
		if(this.getWorld() != null)
			this.getWorld().removeEntity(this);
		if(this.getShip() != null)
			this.getShip().removeBullet(this);
		this.terminated = true;
	}
}
