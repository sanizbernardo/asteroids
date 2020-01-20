package asteroids.model;


import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class of circular entities with a radius, a position and velocity in two dimensions and a mass.
 * Each entity can be assigned to a world, in which it will interact with other entities and the boundaries of the world.
 * 
 * @invar The speed of the entity must not exceed its maximum speed.
 *  | this.getTotalSpeed <= this.getMaxSpeed()
 * @invar The maximum speed of an entity must never exceed the speed of light.
 *  | this.getMaxSpeed() <= getLightSpeed()
 * @invar The radius of the entity must be larger than the minimum radius.
 *  | isValidRadius(this.getRadius))
 * @invar The entity is always within the bounds of its world.
 *  | this.isInBoudaries(this.getWorld())
 * @invar The entity's mass is at least its minimum mass.
 *  | this.getMass() >= this.getMinMass()
 * @invar Two entities in the same world will never overlap.
 *  | this.overlap(otherentity) == false
 * 
 * @version 2.0
 * @author Bernardo Saniz, Elien Vlaeyen
 */
public abstract class Entity {
	
	/**
	 * The position of the entity expressed as a vector with two coordinates
	 */
	private Vector pos = new Vector(0,0);
	
	/**
	 * Returns the position of this entity as a vector.
	 */
	@Basic
	public Vector getPos() {
		return this.pos;
	}
	
	/**
	 * Sets this entity's position to the given vector.
	 * 
	 * @param vector
	 *  The vector representing this entity's new position
	 * @post This entity's position is represented by the given vector.
	 *  | new.getPos() == vector
	 */
	public void setPos(Vector vector) {
		this.pos = vector;
	}
	

	/**
	 * Check whether the given x position is valid within the given world.
	 * 
	 * @param world
	 *  The world in which the given position position will be checked
	 * @param pos
	 * 	The x position to be checked
	 * @return True if the given position is within the bounds of the world this ship is in.
	 * 		   If the ship is not in a world, return true if and only if the given position is not "not a number".
	 *  | if world == null
	 *  | 	then result == ! Double.isNaN(pos)
	 *  | else
	 *  |	result == ( pos >= 0.99*this.getRadius() ) && ( pos <= world.getWidth() - 0.99*this.getRadius() )
	 */
	public boolean isValidXPosition(World world, double pos) {
		if (world == null)
			return ! Double.isNaN(pos);
		else
			return ((pos >= 0.99*this.getRadius()) && (pos <= (world.getWidth() - 0.99*this.getRadius())));
	}
	
	/**
	 * Set the x position of the entity to a given value.
	 * 
	 * @param xposition
	 *  The new value for the x position.
	 * @post The entity's x position is equal to the given value.
	 * 		| new.getPos().getX() == xposition
	 * @throws IllegalArgumentException if the given x position is not valid in the given world.
	 * 		| if ! isValidXPosition(getWorld(), xposition)
	 * 		| 	then throw new IllegalArgumentException()
	 */
	public void setXPosition(double xposition) throws IllegalArgumentException{
		if (! isValidXPosition(this.getWorld(), xposition))
			throw new IllegalArgumentException("Position is not a real value!");
		else
			this.setPos(new Vector(xposition, getPos().getY()));
	}

	

	/**
	 * Checks whether the given y position is valid within the given world.
	 * 
	 * @param world
	 *  The world in which the given position position will be checked
	 * @param pos
	 * 	The y position to be checked
	 * @return True if the given position is within the bounds of the world this ship is in.
	 * 		   If the ship is not in a world, return true if and only if the given position is not "not a number".
	 *  | if world == null
	 *  | 	then result == ! Double.isNaN(pos)
	 *  | else
	 *  |	result == pos >= 0.99*this.getRadius() && pos <= world.getHeight() - 0.99*this.getRadius()
	 */
	public boolean isValidYPosition(World world, double pos) {
		if (world == null)
			return ! Double.isNaN(pos);
		else
			return ((pos >= 0.99*this.getRadius()) && (pos <= (world.getHeight() - 0.99*this.getRadius())));
	}
	
	/**
	 * Set the y position of the entity to a given value.
	 * 
	 * @param yposition
	 *  The new value for the y position.
	 * @post The entity's y position is equal to the given value.
	 * 		| new.getPos().getY() == yposition
	 * @throws IllegalArgumentException if the given y position is not valid in the given world.
	 * 		| if ! isValidYPosition(getWorld(), yposition)
	 * 		| 	then throw new IllegalArgumentException()
	 */
	public void setYPosition(double yposition) throws IllegalArgumentException{
		if (! isValidYPosition(this.getWorld(), yposition))
			throw new IllegalArgumentException("Position is not a real value!");
		else
			this.setPos(new Vector(getPos().getX(), yposition));
	}
	
	
	
	
	/**
	 * Return the difference in x position between the centers of this entity and another.
	 * 
	 * @param other
	 * 	The other entity
	 * @return The difference in x position between the two entities.
	 *  | result == this.getPos().getX() - other.getPos().getX()
	 */
	public double getXDifference(Entity other) {
		return this.getPos().getX() - other.getPos().getX();
	}

	/**
	 * Return the difference in y position between the centers of this entity and another.
	 * 
	 * @param other
	 * 	The other entity
	 * @return The difference in y position between the two entities.
	 *  | result == this.getPos().getY() - other.getPos().getY()
	 */
	public double getYDifference(Entity other) {
		return  this.getPos().getY() - other.getPos().getY();
	}

	/**
	 * Returns the distance between the surfaces of two entities. The distance between a entity and itself is zero.
	 * The distance is negative if both entities overlap. The distance is found by summing the squares
	 * of the differences in coordinates, and then taking the square root of the sum. 
	 * The radii of both entities are then subtracted to find the distance between the surfaces.
	 * 
	 * @param other
	 * 	The other entity 
	 * @return The distance between the surfaces of this entity and the other.
	 * 	| result == Math.sqrt( this.getXDifference(other)^2 + this.getYDifference(other)^2 ) - this.getRadius() - other.getRadius()
	 */
	public double getDistanceBetween(Entity other) {
		Vector d = new Vector(this.getXDifference(other), this.getYDifference(other));
		return d.getMagnitude() - this.getRadius() - other.getRadius();
	}
	
	/**
	 * Returns the distance between the centers of two entities.
	 * 
	 * @param other
	 * 	The other entity 
	 * @return The distance between the centers of this entity and the other.
	 *  | result == Math.sqrt( this.getXDifference(other)^2 + this.getYDifference(other)^2 )
	 */
	public double getDistanceCenters(Entity other) {
		Vector d = new Vector(this.getXDifference(other), this.getYDifference(other));
		return d.getMagnitude();
	}

	/**
	 * Returns true if two entities overlap with each other. The distance between them is then smaller than zero.
	 * Two entities can only overlap if they are in the same world. Overlapping means that the entities share a world
	 * and the distance between them is less than 1% of the sum of the radii of the two entities.
	 * 
	 * @param other
	 * 	The other entity
	 * @return True if and only if the two entities overlap.
	 * 	| result == this.getDistanceBetween(other) < -0.01*(this.getRadius() + other.getRadius())
	 * @throws IllegalArgumentException if the two entities are not in the same world.
	 *  | if ! this.isInSameWorldAs(other)
	 *  |	then throw new IllegalArgumentException()
	 */
	// No catch for getDistanceBetween(), same exception is checked in overlap.
	public boolean overlap(Entity other) throws IllegalArgumentException {
		if(! this.isInSameWorldAs(other))
			throw new IllegalArgumentException("Entities are not in the same world!");
		if (other.equals(this))
			return true;
		else {
			double sigma = this.getRadius() + other.getRadius();
			return (this.getDistanceBetween(other) < (-0.01*sigma));
			}
	}

	
	
	
	/**
	 * Variable to express the size of a entity in kilometers
	 */
	private double radius;
	
	/**
	 * The minimal radius for a ship
	 */
	private static final double minRadiusShip = 10;
	
	/**
	 * The minimal radius for a bullet
	 */
	private static final double minRadiusBullet = 1;
	
	/**
	 * The minimal radius for a bullet
	 */
	private static final double minRadiusMinorPlanet = 5;
	
	/**
	 * Returns this entity's radius.
	 */
	@Basic @Immutable
	public double getRadius() {
		return this.radius;
	}

	/**
	 * Returns the minimal radius for this entity.
	 */
	@Basic @Immutable
	public double getMinRadius() {
		if (this instanceof Ship)
			return minRadiusShip;
		else if (this instanceof Bullet)
			return minRadiusBullet;
		else if (this instanceof MinorPlanet)
			return minRadiusMinorPlanet;
		return 0;
	}

	/**
	 * Check whether the given radius is valid.
	 * 
	 * @param radius
	 * 	The radius to be checked.
	 * @return True if and only if the given radius is greater than the minimal value allowed.
	 * 	| result == (radius >= getMinRadius())
	 */
	private boolean isValidRadius(double radius) {
		return radius >= getMinRadius();
	}

	/**
	 * Sets the radius of this entity to a given value.
	 * 
	 * @param radius
	 * 	The value for the radius
	 * @post The ship's radius is equal to the given value.
	 * 	| new.getRadius() == radius
	 * @throws IllegalArgumentException if the given radius is not valid.
	 *  | if (! isValidRadius(radius))
	 *  | 	then throw new IllegalArgumetnException
	 */
	public void setRadius(double radius) throws IllegalArgumentException{
		if (! isValidRadius(radius))
			throw new IllegalArgumentException("The given radius is invalid.");
		else
			this.radius = radius;
	}

	
	
	
	/**
	 * A vector representing the velocity of this entity
	 */
	private Vector vel  = new Vector(0,0);

	/**
	 * Returns the velocity of this entity as a vector.
	 */
	@Basic
	public Vector getVel() {
		return this.vel;
	}
	
	/**
	 * Sets this entity's velocity to the given vector.
	 * 
	 * @param vector
	 *  The vector representing this entity's new velocity
	 * @post This entity's velocity is represented by the given vector.
	 *  | new.getVel() == vector
	 */
	public void setVel(Vector vector) {
		this.vel = vector;
	}
	
	/**
	 * The largest value allowed for the total velocity of any entity, equal to the speed of light
	 */
	private static final double lightspeed = 300000;
	
	/**
	 * The largest total velocity that this entity can attain
	 */
	private double maxSpeed = lightspeed;
	
	/**
	 * Returns the total speed of the entity.
	 * 
	 * @return The magnitude of the velocity vector of this entity.
	 *  | result == this.getVel().getMagnitude()
	 */
	public double getTotalSpeed() {
		return this.getVel().getMagnitude();
	}
	
	/**
	 * Check whether the given velocity is valid.
	 * 
	 * @param speed
	 * 	The speed to be checked
	 * @return True if and only if the given velocity doesn't exceed the entity's maximum speed.
	 * 	| result == (speed <= getMaxSpeed())
	 */
	private boolean isValidSpeed(double speed) {
		return speed <= getMaxSpeed();
	}
	
	/**
	 * Changes the given velocity to a valid velocity, if necessary.
	 * 
	 * @param vel
	 *  The velocity to be altered
	 * @return vel if vel is a valid velocity.
	 *  | if vel != Double.NEGATIVE_INFINITY &&
	 *  |	 vel != Double.POSITIVE_INFINITY &&
	 *  |	 ! Double.isNaN(vel)
	 *  |	then result == vel
	 * @return 0 if vel is not a number.
	 *  | if Double.isNaN(vel)
	 *  | 	then result == 0
	 * @return The speed of light if vel is positive infinity.
	 *  | if vel == Double.POSITIVE_INFINITY
	 *  |	then result == getLightSpeed()
	 * @return Negative the speed of light if vel is negative infinity.
	 *  | if vel == Double.NEGATIVE_INFINITY
	 *  |	then result == -getLightSpeed()
	 */
	public double correctVelocity(double vel) {
		if (Double.isNaN(vel))
			return 0;
		else if (vel == Double.NEGATIVE_INFINITY)
			return -1*getLightSpeed();
		else if (vel == Double.POSITIVE_INFINITY)
			return getLightSpeed();
		else
			return vel;
	}
	
	/**
	 * Returns the greatest speed this entity can attain.
	 */
	@Basic
	private double getMaxSpeed() {
		return this.maxSpeed;
	}
	
	/**
	 * Check whether the given maximum speed is valid.
	 * 
	 * @param speed
	 * 	The maximum speed to be checked
	 * @return True if and only if the given maximum speed is larger than zero and doesn't exceed c.
	 * 	| result == (speed <= getLightSpeed())
	 */
	private boolean isValidMaxSpeed(double speed) {
		return (speed <= getLightSpeed()) && (speed > 0);
	}

	/**
	 * Returns the speed of light.
	 */
	@Basic @Immutable
	private double getLightSpeed() {
		return lightspeed;
	}

	/**
	 * Set the x and y velocities of the entity to the given values.
	 * If this results in a speed greater than the entity's maximum speed, the speed will be reduced to the maximum.
	 * 
	 * @param xvel
	 * 	The new velocity in the x direction	
	 * @param yvel
	 * 	The new velocity in the y direction
	 * 
	 * @effect The given values are corrected if necessary.
	 *  | (new xvel) == correctVelocity(xvel)
	 *  | (new yvel) == correctVelocity(yvel)
	 * @post If the total speed is a valid speed, the entity's new x and y velocities are equal to the corrected values.
	 * 	| Vector newspeed = new Vector(xvel, yvel);
	 * 	| if (isValidSpeed(newspeed.getMagnitude()))
	 * 	|	  then ( new.getVel().getX() == xvel
	 * 	|	   		 new.getVel().getY() == yvel )
	 * @post If the total speed is not valid, the given x and y speeds are rescaled so that the new total speed
	 * 		 is equal to the maximum speed of the entity without changing its orientation.
	 * 	| if ( ! isValidSpeed(newspeed.getMagnitude()))
	 * 	|	  then ( new.getVel().getX() ==  this.getMaxSpeed()*(xvel/newspeed.getMagnitude()) &&
	 * 	|	         new.getVel().getY() ==  this.getMaxSpeed()*(yvel/newspeed.getMagnitude()) )
	 */
	public void setSpeed(double xvel, double yvel) {
		double newxvel = correctVelocity(xvel);
		double newyvel = correctVelocity(yvel);
		Vector newspeed = new Vector(newxvel, newyvel);
		if (isValidSpeed(newspeed.getMagnitude())) {
			this.setVel(newspeed);
		}
		else {
			newspeed = new Vector(this.getMaxSpeed()*(newxvel/newspeed.getMagnitude()), this.getMaxSpeed()*(newyvel/newspeed.getMagnitude()));
			this.setVel(newspeed);
		}
	}

	/**
	 * The entity's maximum speed is changed to a given value. If this value is not valid,
	 * the maximum speed will be set to the closest valid value.
	 * 
	 * @param speed
	 * 	The new value for the maximum speed of this entity
	 * 
	 * @post If the given speed is valid, the maximum speed is set to the given value.
	 * 		 | if isValidSpeed(speed)
	 * 		 | 	then new.getMaxSpeed() == speed
	 * @post If the given speed is larger than the lightspeed, the maximum speed is set to lightspeed.
	 * 		 | if speed > getLightSpeed()
	 * 		 | 	then new.getMaxSpeed() == getLightSpeeds()
	 * @post If the given speed is smaller than 0, the maximum speed is set to 0.
	 * 		 | if speed < 0
	 * 		 | 	then new.getMaxSpeed() == 0
	 */
	public void setMaxSpeed(double speed) {
		if (isValidMaxSpeed(speed))
			this.maxSpeed = speed;
		else if (speed > getLightSpeed())
			this.maxSpeed = getLightSpeed();
		else
			this.maxSpeed = 0;
	}
	
	
	
	/**
	 * The density of entities of the Ship subclass
	 */
	private static final double shipdensity = 1.42e12;
	
	/**
	 * The density of entities of the Bullet subclass
	 */
	private static final double bulletdensity = 7.8e12;
	
	/**
	 * The density of entities of the Asteroid subclass
	 */
	private static final double asteroiddensity = 2.65e12;
	
	/**
	 * The density of entities of the Planetoid subclass
	 */
	private static final double planetoiddensity = 0.917e12;
	
	/**
	 * Returns the density of this entity.
	 */
	@Basic @Immutable
	private double getDensity() {
		if (this instanceof Ship)
			return shipdensity;
		else if (this instanceof Bullet)
			return bulletdensity;
		else if (this instanceof Asteroid)
			return asteroiddensity;
		else if (this instanceof Planetoid)
			return planetoiddensity;
		return 0;
	}

	/**
	 * The mass of this entity
	 */
	private double mass;
	
	/**
	 * Returns this entity's mass.
	 */
	@Basic
	public double getMass() {
		return this.mass;
	}

	/**
	 * Returns the smallest mass this entity can have.
	 * 
	 * @return The minimum mass for this entity, given its subclass and radius.
	 *  | result == (this.getRadius()^3) * this.getDensity() * Math.PI * 4/3
	 */
	@Immutable
	public double getMinMass() {
		return Math.PI*Math.pow(this.getRadius(), 3)*this.getDensity()*4.0/3.0;
	}

	/**
	 * Sets the mass of this entity to the given value or the closest valid value.
	 * 
	 * @param mass
	 *  The new value for this entity's mass
	 * @post If this entity is of the subclass Ship, and the given mass is larger than the minimum mass for this entity,
	 *  	 this entity's new mass is equal to mass.
	 *  | if this instanceof Ship && mass > this.getMinMass()
	 *  |	then new.getMass() == mass
	 * @post This entity's new mass is equal to the minimum mass for this entity.
	 *  | new.mass = this.getMinMass()
	 */
	public void setMass(double mass) {
		if (this instanceof Ship && mass > this.getMinMass())
			this.mass = mass;
		else
			this.mass = this.getMinMass();
	}

	
	
	
	/**
	 * The world to which this entity is assigned
	 */
	protected World world;
	
	/**
	 * Returns the world that this entity is in, if any.
	 */
	@Basic
	public World getWorld() {
		return this.world;
	}

	/**
	 * Places this entity in the given world.
	 * 
	 * @param world
	 *  The world this entity will be placed in
	 * @post This entity is assigned to the given world.
	 *  | new.getWorld() == world
	 * @throws IllegalArgumentException if this entity is already in a world and will not yet be removed.
	 *  | if this.getWorld() != null && world != null
	 *  |	then throw new IllegalArgumentException()
	 * @throws IllegalArgumentException if this entity is not within the boundaries of the given world.
	 *  | if ! this.isInBoundaries(world)
	 *  |	then throw new IllegalArgumentException()
	 * @throws IllegalArgumentException if this entity would overlap with another entity in the given world.
	 *  | if this.overlap(entity) && (! this.equals(entity))
	 *  |	then throw new IllegalArgumentException()
	 */
	public void setWorld(World world) {
		if (! this.isInBoundaries(world))
			throw new IllegalArgumentException("The entity is out of bounds!");
		if (this.getWorld() != null && world != null)
			throw new IllegalArgumentException("The entity is already assigned!");
		this.world = world;
		if (world == null)
			return;
		for (Entity entity : this.getWorld().getEntityList())
			if (this.overlap(entity) && (! this.equals(entity)))
				throw new IllegalArgumentException("There is another entity at that position!");
	}

	
	
	/**
	 * True if and only if this entity is terminated
	 */
	protected boolean terminated = false;
	
	/**
	 * Returns whether or not this entity is terminated.
	 */
	@Basic
	public boolean isTerminated() {
		return terminated;
	}

	/**
	 * Terminates this entity.
	 * 
	 * @post This entity will be terminated.
	 *  | new.isTerminated == true
	 */
	public abstract void terminate();
	
	
	
	/**
	 * Returns whether this entity lies fully within the boundaries of the given world.
	 * 
	 * @param world
	 *  The world to be checked
	 * @return True if and only if both this ship's coordinates are valid.
	 *  | result == isValidXPosition(world, this.getPos().getX()) && isValidYPosition(world, this.getPos().getY())
	 */
	public boolean isInBoundaries(World world) {
		return (isValidXPosition(world, this.getPos().getX()) && isValidYPosition(world, this.getPos().getY()));
	}
	
	/**
	 * Returns whether or not this and the given entity are in the same world.
	 * 
	 * @param entity
	 *  The entity to be checked
	 * @return True if and only if this entity and the given entity have the same world.
	 *  | result == (this.getWorld() == entity.getWorld())
	 */
	private boolean isInSameWorldAs(Entity entity) {
		return (this.getWorld() == entity.getWorld());
	}
	
	
	
	
	/**
	 * Changes this bullet's position depending on its speed and a given time duration.
	 * 
	 * @param time
	 *  The time duration for the movement
	 * @post This bullet's new x position is equal to its old x position plus its x velocity multiplied by the time duration.
	 *  | new.getPos().getX() == this.getPos().getX() + this.getVel().getX()*time
	 * @post This bullet's new y position is equal to its old y position plus its y velocity multiplied by the time duration.
	 *  | new.getPos().getY() == this.getPos().getY() + this.getVel().getY()*time
	 * @throws IllegalArgumentException If the given time is not a positive value.
	 *  | if time < 0
	 *  | 	then throw new IllegalArgumentException()
	 */
	public void move(double time) throws IllegalArgumentException {
		if (time < 0)
			throw new IllegalArgumentException("Time must be a positive value!");
		this.getWorld().removePosition(this.getPos().getX(), this.getPos().getY());
		this.setPos(this.getPos().add(new Vector(time*this.getVel().getX(), time*this.getVel().getY())));
		this.getWorld().addPosition(this);
	}
	
	
	
	
	/**
	 * Returns whether or not this entity apparently collides with a given entity. Apparent collision occurs when
	 * the distance between two entities is between -1% and 1% of the sum of their radii. Entities can only collide if they
	 * are in the same world.
	 * 
	 * @param other
	 *  The given entity
	 * @return True if and only if the distance between two entities is between -1% and 1% of the sum of their radii.
	 *  | sigma = this.getRadius() + other.getRadius()
	 * 	| result == ( this.isInSameWorldAs(other) &&
	 *  | 	this.getDistanceBetween(other) < 0.01*sigma &&
	 *  | 	this.getDistanceBetween(other) > -0.01*sigma )
	 */
	public boolean apparentlyCollides(Entity other) {
		double sigma = this.getRadius() + other.getRadius();
		try {
			return (this.isInSameWorldAs(other) && this.getDistanceBetween(other) < 0.01*sigma && this.getDistanceBetween(other) > -0.01*sigma);
		} catch(IllegalArgumentException e){
			return false;
		}
	}
	
	/**
	 * Returns whether or not this entity is apparently colliding with the boundaries of the given world. 
	 * 
	 * @param world
	 * 	The world to be checked
	 * @return True if and only if the entity is colliding with one of the world's boundaries.
	 *  | result == this.collidesX(world) || this.collidesY(world)
	 */
	public boolean apparentlyCollides(World world) {
		if (this.getWorld() != world)
			return false;
		return (this.collidesX(world) || this.collidesY(world));
	}
	
	/**
	 * Returns whether or not this entity is colliding with a vertical boundary of its world.
	 * 
	 * @param world
	 *  The world to be checked
	 * @return True if the entity's x position is larger than 99% of its radius and smaller than 101% of its radius.
	 *  | if this.getPos().getX() > (0.99*this.getRadius()) && this.getPos().getX() < (1.01*this.getRadius())
	 *  |	then result == true
	 * @return True if the entity's x position, minus the width of its world,
	 *  		is smaller than 99% of its radius and larger than 101% of its radius.
	 *  | if this.getPos().getX() < (world.getWidth() - 0.99*this.getRadius()) && 
	 *  |		this.getPos().getX() > (world.getWidth() - 1.01*this.getRadius())
	 *  |	then result == true
	 */
	public boolean collidesX(World world) {
		boolean collidesleft = (this.getPos().getX() > (0.99*this.getRadius()) && this.getPos().getX() < (1.01*this.getRadius()));
		boolean collidesright = (this.getPos().getX() < (world.getWidth() - 0.99*this.getRadius()) 
								 && this.getPos().getX() > (world.getWidth() - 1.01*this.getRadius()));
		return (collidesleft || collidesright);
	}
	
	
	/**
	 * Returns whether or not this entity is colliding with a horizontal boundary of its world.
	 * 
	 * @param world
	 *  The world to be checked
	 * @return True if the entity's y position is larger than 99% of its radius and smaller than 101% of its radius.
	 *  | if this.getPos().getY() > (0.99*this.getRadius()) && this.getPos().getY() < (1.01*this.getRadius())
	 *  |	then result == true
	 * @return True if the entity's y position, minus the height of its world,
	 *  		is smaller than 99% of its radius and larger than 101% of its radius.
	 *  | if this.getPos().getY() < (world.getHeight() - 0.99*this.getRadius()) && 
	 *  |		this.getPos().getY() > (world.getHeight() - 1.01*this.getRadius())
	 *  |	then result == true
	 */
	public boolean collidesY(World world) {
		boolean collidesbottom = (this.getPos().getY() > (0.99*this.getRadius()) && this.getPos().getY() < (1.01*this.getRadius()));
		boolean collidestop = (this.getPos().getY() < (world.getHeight() - 0.99*this.getRadius()) 
							   && this.getPos().getY() > (world.getHeight() - 1.01*this.getRadius()));
		return (collidestop || collidesbottom);
	}
	
	
	
	
	/**
	 * Returns the time until two entities are going to collide with each other expressed in seconds.
	 * If they never collide, the method will return positive infinity.
	 * At the time of collision the distance between the two entities (sigma) will be equal to the sum of their radii.
	 * The distance between the entities is expressed as a function of time, which gives a quadratic equation in time.
	 * Solving this equation gives two solutions. The smallest of the two is returned, as the collision occurs
	 * the first time that the entities touch. The method does not work for entities that overlap. 
	 * 
	 * @param other
	 * 	The other entity
	 * @return Infinity if this entity and the other are not in the same world.
	 * 		   Else the time it will take for the two entities to collide, expressed in seconds.
	 *  | if (this.getWorld() == null || other.getWorld() == null || ! this.isInSameWorldAs(other))
	 *	|	return Double.POSITIVE_INFINITY;
	 * 	| else (this.getRadius() + other.getRadius())^2 ==
	 * 	| 	   (this.getPos().getX() + time*this.getVel().getX() - other.getPos().getX() - time*other.getVel().getX())^2 +
	 * 	| 	   (this.getPos().getY() + time*this.getVel().getY() - other.getPos().getY() - time*this.getVel().getY())^2
	 * 	| 	   result == time
	 * @throws IllegalArgumentException if the entities are overlapping.
	 * 	| if this.overlap(other) && (! this.equals(other))
	 *  | 	then throw new IllegalArgumentException()
	 */
	public double getTimeToCollision(Entity other) throws IllegalArgumentException{
		if (this.getWorld() == null || other.getWorld() == null || ! this.isInSameWorldAs(other))
			return Double.POSITIVE_INFINITY;
		if (this.overlap(other) && (! this.equals(other)))
			throw new IllegalArgumentException("The two entities overlap!");
		else {
			double sigma = this.getRadius() + other.getRadius();
			
			double dx = this.getXDifference(other);
			double dy = this.getYDifference(other);
			
			double dvx = this.getVel().getX() - other.getVel().getX();
			double dvy = this.getVel().getY() - other.getVel().getY();
			
			double drr = Math.pow(dx, 2) + Math.pow(dy, 2);
			double dvv = Math.pow(dvx, 2) + Math.pow(dvy, 2);
			double dvr = dvx*dx + dvy*dy;
			
			double d = Math.pow(dvr, 2) - dvv*(drr - Math.pow(sigma, 2));
			
			if (dvr >= 0)
				return Double.POSITIVE_INFINITY;
			else if (d<=0)
				return Double.POSITIVE_INFINITY;
			else
				return -(dvr + Math.sqrt(d))/dvv;
		}
	}
	
	/**
	 * Returns the time left until this entity collides with a vertical boundary of its world.
	 * 
	 * @return Infinity if this entity's x velocity is 0.
	 *  | if this.getVel().getX() == 0
	 *  | 	then result == Double.POSITIVE_INFINITY
	 * @return The time until this entity's next collision with a vertical boundary.
	 *  | if this.getVel().getX() > 0
	 *  | 	then result == Math.abs( (this.getWorld().getWidth() - this.getPos().getX() - this.getRadius())/this.getVel().getX() )
	 *  | else result == Math.abs( (this.getPos().getX() - this.getRadius())/this.getVel().getX() )
	 */
	public double getXTime() {
		double xtime;
		if (this.getVel().getX() == 0)
			return Double.POSITIVE_INFINITY;
		else if (this.getVel().getX() > 0)
			xtime = (this.getWorld().getWidth() - this.getPos().getX() - this.getRadius())/this.getVel().getX();
		else
			xtime = (this.getPos().getX() - this.getRadius())/this.getVel().getX();
		return Math.abs(xtime);
	}
	
	/**
	 * Returns the time left until this entity collides with a horizontal boundary of its world.
	 * 
	 * @return Infinity if this entity's y velocity is 0.
	 *  | if this.getVel().getY() == 0
	 *  | 	then result == Double.POSITIVE_INFINITY
	 * @return The time until this entity's next collision with a horizontal boundary.
	 *  | if this.getVel().getY() > 0
	 *  | 	then result == Math.abs( (this.getWorld().getHeight() - this.getPos().getY() - this.getRadius())/this.getVel().getY() )
	 *  | else result == Math.abs( (this.getPos().getY() - this.getRadius())/this.getVel().getY() )
	 */
	public double getYTime() {
		double ytime;
		if (this.getVel().getY() == 0)
			return Double.POSITIVE_INFINITY;
		else if (this.getVel().getY() > 0)
			ytime = (this.getWorld().getHeight() - this.getPos().getY() - this.getRadius())/this.getVel().getY();
		else
			ytime = (this.getPos().getY() - this.getRadius())/this.getVel().getY();
		return Math.abs(ytime);
	}
	
	/**
	 * Returns the time until this entity's next collision with the boundary of its world.
	 * 
	 * @return Infinity if this entity is not in a world.
	 *  | if this.getWorld() == null
	 *  | 	then result == Double.POSITIVE_INFINITY
	 * @return The shortest time until this entity collides with a boundary of its world.
	 *  | if this.getXTime() < this.getYTime()
	 *  |	then result == this.getXTime()
	 *  | else
	 *  |	result == this.getYTime()
	 */
	public double getTimeToCollision() {
		if (this.getWorld() == null)
			return Double.POSITIVE_INFINITY;
		double xtime = this.getXTime();
		double ytime = this.getYTime();
		if (xtime < ytime)
			return xtime;
		else
			return ytime;
	}
	
	/**
	 * Returns where two entities will collide. These coordinates are equal to the coordinates of this entity during
	 * the collision, minus the radius of the entity projected onto vector connecting the centers of the two entities.
	 * If they never collide, the method will return 'null'.
	 * The method does not work for entity who overlap.
	 * 
	 * @param other
	 * 	The other entity
 	 * @return null if the entity will never collide.
	 * 	| if this.getTimeToCollision(other) == Double.POSITIVE_INFINITY
	 * 	| 	then result == null
	 * @return The coordinates of the future collision between the two entities.
	 * 	| result == this.getFuturePosition() - this.getFutureDifference(other)*this.getRadius()
	 * 	|			/ (this.getRadius() + other.getRadius())
	 * @throws IllegalArgumentException if the two entities are overlapping
	 *  | if this.overlap(other) == true
	 * 	| 	then throw new IllegalArgumentException() 
	 */
	// No catch for overlapping in getTimeToCollision. Same exception is already checked in overlap.
	public double[] getCollisionPosition(Entity other) throws IllegalArgumentException{
		if (this.getWorld() == null || other.getWorld() == null|| (! this.isInSameWorldAs(other))){
			return null;}
		if (this.overlap(other) && (!this.equals(other))){
			throw new IllegalArgumentException("The two entities overlap!");}
		else {	
			double time;	
			time = this.getTimeToCollision(other);
				
			if (time == Double.POSITIVE_INFINITY){
				return null;}
			else {
				double sigma = this.getRadius() + other.getRadius();
				double dx = this.getXDifference(other) - other.getVel().getX()*time + this.getVel().getX()*time;
				double dy = this.getYDifference(other) - other.getVel().getY()*time + this.getVel().getY()*time;
					
				double xcollision = this.getPos().getX() + this.getVel().getX()*time - dx*this.getRadius()/sigma;
				double ycollision = this.getPos().getY() + this.getVel().getY()*time - dy*this.getRadius()/sigma;
				double[] collision = {xcollision, ycollision};
				return collision;
			}
		}
	}
	
	/**
	 * Returns the position of the next collision between this entity and the boundary of its world. If the time until a
	 * collision with a vertical boundary is the smallest, the x position of the collision will be either 0 or equal to
	 * the width of this entity's world, depending on the direction of the movement. If the time until a collision with
	 * a horizontal boundary is the smallest, the y position of the collision will be either 0 or equal to the height of
	 * this entity's world.
	 * 
	 * @return The position of this entity's next collision with a boundary.
	 *  | if this.getXTime < this.getYTime()
	 *  | 	then result[0] == 0 || this.getWorld().getWidth()
	 *  | if this.getYTime < this.getXTime()
	 *  | 	then result[1] == 0 || this.getWorld().getHeight()
	 */
	public double[] getCollisionPosition() {
		double time = this.getTimeToCollision();
		if (time == Double.POSITIVE_INFINITY)
			return null;
		double newx;
		double newy;
		if (this.getXTime() < this.getYTime()) {
			newy = this.getPos().getY() + time*this.getVel().getY();
			if (this.getVel().getX() > 0)
				newx = this.getWorld().getWidth();
			else
				newx = 0;
			}
		else {
			newx = this.getPos().getX() + time*this.getVel().getX();
			if (this.getVel().getY() > 0)
				newy = this.getWorld().getHeight();
			else
				newy = 0;
			}
		double[] pos = {newx, newy};
		return pos;
	}
	
	/**
	 * Handles a collision between this entity and another.
	 * 
	 * @param other
	 *  The entity this entity will collide with
	 */
	public abstract void collide(Entity other);
	
	/**
	 * Handles a collision between this entity and the boundary of the given world.
	 * 
	 * @param world
	 *  The world this entity will collide with
	 */
	public void collide(World world) {
		double newXVelocity = this.getVel().getX();
		double newYVelocity = this.getVel().getY();
		if (this.collidesX(world))
			newXVelocity = -newXVelocity;
		if (this.collidesY(world))
			newYVelocity = -newYVelocity;
		this.setSpeed(newXVelocity, newYVelocity);		
	}
}
