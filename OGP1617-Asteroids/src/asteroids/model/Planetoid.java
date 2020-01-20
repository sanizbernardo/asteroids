package asteroids.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of planetoids that teleport ships to a random location when they touch them.
 * A subclass of MinorPlanet. Planetoids decrease in size as they travel through space.
 * When a large planetoid is destroyed, it spawns two smaller asteroids travelling in opposite directions.
 * 
 * @version 1.0
 * @author Bernardo Saniz, Elien Vlaeyen
 */
public class Planetoid extends MinorPlanet {
	
	/**
	 * Creates a planetoid with the given position, velocity, radius and traveled distance.
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
	 * @param traveleddistance
	 *  The distance that this planetoid has already traveled
	 *  
	 * @post This planetoid's x position is equal to the given coordinates if they are valid.
	 * 	| if isValidXPosition(xpos)
	 *  |	then new.getPos().getX() == xpos
	 * @post This planetoid's y position is equal to the given coordinates if they are valid.
	 * 	| if isValidYPosition(ypos)
	 *  |	then new.getPos().getY() == ypos
	 * @post This planetoid's x and y velocities are equal to the given values if the total speed is valid.
	 * 		 Otherwise they are rescaled so that the total speed is equal to this planetoid's maximum speed.
	 *  | if isValidSpeed(Math.sqrt(xvel^2 + yvel^2))
	 *  |	then new.getVel().getX() == xvel && new.getVel().getY() == yvel
	 *  | else new.getTotalSpeed() == this.getMaxSpeed()
	 * @post This planetoid's traveled distance is equal to the given value.
	 *  | new.getTraveledDistance() == traveleddistance
	 * @post If the given radius, minus 0.0001% of this planetoid's traveled distance, is valid,
	 * 		 then this planetoid's radius is equal to the given value minus 0.0001% of its traveled distance.
	 * 		 Otherwise this planetoid will be terminated.
	 * 	| if isValidRadius(radius - 0.000001*this.getTraveledDistance())
	 *  | 	then new.getRadius() == radius - 0.000001*this.getTraveledDistance()
	 *  | else new.isTerminated() == true
	 * @throws IllegalArgumentException if the given x position or y position is invalid.
	 *  | if ! isValidXPosition(xpos) || ! isValidYPosition(ypos)
	 *  |	then throw new IllegalArgumentException()
	 */
	@Raw
	public Planetoid(double xpos,double ypos, double xvel, double yvel, double radius, double traveleddistance) {
		
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
		
		this.setTraveledDistance(traveleddistance);
		
		try {
			this.setRadius(radius - 0.000001*this.getTraveledDistance());
		} catch (IllegalArgumentException e) {
			this.terminate();
		}
		
		this.setMass(this.getMinMass());
		
	}
	
	/**
	 * The total distance that this planetoid has traveled.
	 */
	private double traveleddistance;
	
	/**
	 * Returns the total distance traveled by this planetoid.
	 */
	@Basic
	public double getTraveledDistance() {
		return this.traveleddistance;
	}
	
	/**
	 * Sets the traveled distance of this planetoid to the given value.
	 * 
	 * @param distance
	 * 	The new traveleddistance of this planetoid.
	 * @post The new traveledddistance is equal to the given value.
	 * 	| new.traveleddistance == distance
	 */
	public void setTraveledDistance(double distance) {
		this.traveleddistance = distance;
	}

	/**
	 * Changes this planetoid's position depending on its speed and a given time duration.
	 * 
	 * @param time
	 *  The time duration for the movement
	 * @post This planetoid's new x position is equal to its old x position plus its x velocity multiplied by the time duration.
	 *  | new.getPos().getX() == this.getPos().getX() + this.getVel().getX()*time
	 * @post This planetoid's new y position is equal to its old y position plus its y velocity multiplied by the time duration.
	 *  | new.getPos().getY() == this.getPos().getY() + this.getVel().getY()*time
	 * @post This planetoid's traveled distance will have increased by an amount equal to the distance it moved.
	 *  | Vector distance = new Vector(time*this.getVel().getX(), time*this.getVel().getY());
	 *  | new.getTraveledDistance() = this.getTraveledDistance() + distance.getMagnitude()
	 * @post This planetoid's radius will have decreased with 0.0001% of the distance it has traveled.
	 *  | new.getRadius() == this.getRadius() - 0.000001*new.getTraveledDistance()
	 * @throws IllegalArgumentException If the given time is not a positive value.
	 *  | if time < 0
	 *  | 	then throw new IllegalArgumentException()
	 */
	@Override
	public void move(double time) throws IllegalArgumentException{
		super.move(time);
		Vector dist = new Vector(time*this.getVel().getX(), time*this.getVel().getY());
		double distance = dist.getMagnitude();
		this.setTraveledDistance(this.getTraveledDistance() + distance);
		try {
		this.setRadius(this.getRadius() - 0.000001*this.getTraveledDistance());
		} catch (IllegalArgumentException e) {
			this.terminate();
		}
	}
	
	/**
	 * Resolves a collision between this planetoid and another entity.
	 * 
	 * @param other
	 *  The entity that this planetoid will collide with
	 * @effect If the other entity is a ship, it is teleported to random location.
	 *  | if other instanceof Ship
	 *  |	then other.teleport()
	 * @effect if the other entity is a minor planet, the two entities will bounce off each other.
	 *  | if other instanceof MinorPlanet
	 *  |	then this.minorCollide(other)
	 * @post If the other entity is a bullet, both entities will be terminated.
	 *  | if other instanceof Bullet
	 *  |	then new.isTerminated() == true && (new other).isTerminted() == true
	 */
	@Override
	public void collide(Entity other) {
		if (other instanceof MinorPlanet)
			this.minorCollide((MinorPlanet)other);
		else if (other instanceof Ship) {
			this.teleport((Ship)other);
		}
		else if (other instanceof Bullet)
			other.collide(this);
	}
	
	/**
	 * Teleports the given ship to a random location.
	 * 
	 * @param ship
	 * 	The ship to be teleported
	 * @post The ship's position is valid.
	 *  | isValidXPosition(ship.getXPosition) == true && isValidYPosition(ship.getYPosition)
	 * @post If the ship would collide with another entity as a result, the ship will be terminated.
	 *  | if ship.overlap(otherentity)
	 *  |	then (new ship).isTerminated() == true
	 */
	@Raw
	public void teleport(Ship ship) {
		double maxx = ship.getWorld().getWidth() - ship.getRadius();
		double maxy = ship.getWorld().getHeight() - ship.getRadius();
		Random r = new Random();
		double randomx = ship.getRadius() + (maxx - ship.getRadius())*r.nextDouble();
		double randomy = ship.getRadius() + (maxy - ship.getRadius())*r.nextDouble();
		ship.getWorld().removePosition(ship.getPos().getX(), ship.getPos().getY());
		ship.setXPosition(randomx);
		ship.setYPosition(randomy);
		ship.getWorld().addPosition(ship);
		boolean collided = false;
		for (Entity entity : ship.getWorld().getEntityList())
			if (ship.overlap(entity) && ! ship.equals(entity))
				collided = true;
		if (collided)
			ship.terminate();
			
	}
	
	
	

	/**
	 * Terminates this planetoid.
	 * 
	 * @post This planetoid is no longer assigned to a world.
	 *  | new.getWorld() == null
	 * @post This planetoid is terminated.
	 *  | new.isTerminated == true
	 * @effect If this planetoid's radius is larger than 30, it is split.
	 *  | if this.getRadius() >= 30
	 *  |	then this.split(this.getWorld())
	 */
	@Override
	public void terminate() {
		World world = this.getWorld();
		if (world != null){
			world.removeEntity(this);
			if (this.getRadius() >= 30)
				split(world);}
		this.terminated = true;
	}
	
	
	/**
	 * Creates two asteroids which travel in opposite directions.
	 * 
	 * @param world
	 *  The world the new asteroids will be placed in.
	 * @post The radius of both new asteroids is equal to half of this planetoid's radius.
	 * 	| (new asteroid1).getRadius() == this.getRadius()/2
	 * 	| (new asteroid2).getRadius() == this.getRadius()/2
	 * @post The speed of both new asteroids is equal to 1.5 times the speed of this planetoid.
	 * 	| (new asteroid1).getTotalSpeed() == 1.5*this.getTotalSpeed()
	 * 	| (new asteroid2).getTotalSpeed() == 1.5*this.getTotalSpeed()
	 * @post This planetoid's world will contain both of the created asteroids.
	 *  | (new world).getEntities().contains(asteroid1)
	 *  | (new world).getEntities().contains(asteroid2)
	 */
	public void split(World world) {
		Random r = new Random();
		double direction = Math.PI*r.nextDouble();
		double dx  = Math.cos(direction)*this.getRadius()/2;
		double dy  = Math.sin(direction)*this.getRadius()/2;
		double xvel = 1.5*Math.cos(direction)*this.getTotalSpeed();
		double yvel = 1.5*Math.sin(direction)*this.getTotalSpeed();
		Asteroid child1 = new Asteroid(this.getPos().getX() + dx, this.getPos().getY() + dy, xvel, yvel, this.getRadius()/2);
		Asteroid child2 = new Asteroid(this.getPos().getX() - dx, this.getPos().getY() - dy, -xvel, -yvel, this.getRadius()/2);
		world.addEntity(child1);
		world.addEntity(child2);
	}
	
}
