package asteroids.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import asteroids.part2.CollisionListener;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;


/**
 * A class of worlds that can contain ships and bullets.
 * Each world has a certain height and width expressed in kilometers.
 * 
 * @invar The height of the world is a value between 0 and the maximum height.
 *  | this.getHeight() > 0 && this.getHeight() <= getMaxHeight()
 * @invar The width of the world is a value between 0 and the maximum width.
 *  | this.getWidth() > 0 && this.getWidth() <= getMaxWidth()
 * 
 * @version 2.0
 * @author Bernardo Saniz, Elien Vlaeyen
 */
public class World {
	
	/**
	 * Creates a new world with a given height and width, containing no ships or bullets.
	 * 
	 * @param height
	 * 	The height of the new world expressed in kilometers
	 * @param width
	 * 	The width of the new world expressed in kilometers
	 */
	@Raw
	public World(double width, double height) {
		this.setWidth(width);
		this.setHeight(height);
	}
	
	
	
	
	/**
	 * The width of this world expressed in kilometers
	 */
	private double width;
	
	/**
	 * The maximum width for any world
	 */
	private static double maxwidth = Double.MAX_VALUE;

	/**
	 * Returns the width of this world.
	 */
	@Basic @Immutable
	public double getWidth() {
		return this.width;
	}
	
	/**
	 * Returns the largest value allowed for the width of a world.
	 */
	@Basic @Immutable
	public double getMaxWidth() {
		return maxwidth;
	}

	/**
	 * Sets the width of the world to the given value. If the value is less than zero, sets the width to 0.
	 * If the value is larger than the maximum width, sets the width to the maximum width.
	 * 
	 * @param width
	 * 	The new width of this world
	 * @post If the given width is not a number, this world's width is set to 0.
	 *  | if Double.isNaN(width)
	 *  |	then new.getWidth() == 0
	 * @post If the given width is smaller than 0, this world's width is set to 0.
	 * 	| if width < 0
	 * 	|	then new.getWidth() == 0
	 * @post If the given width is larger than the maximum width, this worlds width is set to the maximum value.
	 *  | if width > this.getMaxWidth()
	 * 	|	then new.getWidth() == this.getMaxWidth()
	 * @post This world's width will be set to the given value.
	 *  | new.getWidth() == width
	 */
	private void setWidth(double width) {
		if (Double.isNaN(width))
			this.width = 0;
		else if (width < 0)
			this.width = 0;
		else if (width > this.getMaxWidth())
			this.width = this.getMaxWidth();
		else
			this.width = width;
	}
	
	
	

	/**
	 * The height of the world expressed in kilometers
	 */
	private double height;
	
	/**
	 * The maximum height for any world
	 */
	private static double maxheight = Double.MAX_VALUE;

	/**
	 * Returns the height of this world.
	 */
	@Basic @Immutable
	public double getHeight() {
		return this.height;
	}

	/**
	 * Returns the largest value allowed for the height of a world.
	 */
	@Basic @Immutable
	public double getMaxHeight() {
		return maxheight;
	}

	/**
	 * Sets the height of the world to the given value. If the value is less than zero, sets the height to 0.
	 * If the value is larger than the maximum height, sets the height to the maximum height.
	 * 
	 * @param height
	 * 	The new height of the world
	 * @post If the given height is not a number, this world's height is set to 0.
	 *  | if Double.isNaN(height)
	 *  |	then new.getHeight() == 0
	 * @post If the given height is smaller than 0, this world's height is set to 0.
	 * 	| if height < 0
	 * 	|	then new.getHeight() == 0
	 * @post If the given height is larger than the maximum height, this worlds height is set to the maximum value.
	 *  | if height > this.getMaxHeight()
	 * 	|	then new.getHeight() == this.getMaxHeight()
	 * @post This world's height will be set to the given value.
	 *  | new.getHeight() == height
	 */
	private void setHeight(double height) {
		if (Double.isNaN(height))
			this.height = 0;
		else if (height < 0)
			this.height = 0;
		else if (height > this.getMaxHeight())
			this.height = this.getMaxHeight();
		else
			this.height = height;
	}
	



	/**
	 * A set containing all the entities in this world
	 */
	private Set<Entity> entitylist = new HashSet<Entity>();
	
	/**
	 * Places the given entity in this world.
	 * 
	 * @param entity
	 * 	The entity to be placed in this world
	 * @post This world's entity list contains the given entity, if it lies within the boundaries of this world and
	 * 		 it would not overlap with another entity in this world.
	 * 	| new.getEntityList().contains(entity)
	 * @post This world's entity positions map contains the given entity, if it lies within the boundaries of this world
	 * 		 and it would not overlap with another entity in this world.
	 *  | new.getEntityPositions().containsValue(entity)
	 * @effect The given entity is assigned to this world.
	 * 	| entity.setWorld(this)
	 * @throws IllegalArgumentException if the entity is outside the boundaries of this world.
	 * 	| if ! entity.isInBoudaries(this)
	 * 	| 	then throw new IllegalArgumentException()
	 * @throws IllegalArgumentException if the entity overlaps with another entity in this world.
	 * 	| if this.overlap(otherentity)
	 * 	| 	then throw new IllegalArgumentException()
	 */
	@Raw
	public void addEntity(Entity entity) throws IllegalArgumentException, NullPointerException {
		if (! entity.isInBoundaries(this))
			throw new IllegalArgumentException("Entity is out of bounds");
		try {
			this.entitylist.add(entity);
			this.addPosition(entity);
			entity.setWorld(this);
		} catch (IllegalArgumentException e) {
			this.removeEntity(entity);
			throw new IllegalArgumentException("Entity could not be added!");
		}
	}
	
	/**
	 * Removes the given entity from this world.
	 * 
	 * @param entity
	 * 	The entity to be removed from this world
	 * @post The given entity will not be in this world's entity list.
	 *  | ! new.getEntityList().contains(entity)
	 * @post This world's entity positions map no longer contains the given entity.
	 *  | ! new.getEntityPositions().containsValue(entity)
	 * @post The given entity will not be assigned to this world.
	 *  | entity.getWorld() == null
	 * @throws IllegalArgumentException if the given entity is not in this world.
	 * 	| if ! this.getEntityList().contains(entity)
	 *  | 	then throw new IllegalArgumentException()
	 */
	public void removeEntity(Entity entity) throws IllegalArgumentException, NullPointerException {
		if (entity.getWorld() != this)
			throw new IllegalArgumentException("Entity is not in world");
		this.entitylist.remove(entity);
		this.removePosition(entity.getPos().getX(), entity.getPos().getY());
		entity.setWorld(null);
		
	}
	
	/**
	 * Returns the list of all the entities in this world.
	 */
	@Basic
	public Set<Entity> getEntityList() {
		return this.entitylist;
	}
	
	/**
	 * Returns a set containing all entities of the given subclass within this world.
	 * 
	 * @param type
	 *  The subclass that the set is to contain
	 * @return A set containing all entities of the given subclass within this world.
	 *  | set = new HashSet()
	 *  | for entity : this.getEntityList()
	 *  |	if entity instanceof type
	 *  |		set.add(entity)
	 *  | result == set
	 */
	@SuppressWarnings("unchecked")
	public <T extends Entity> Set<T> getSpecificList(Class<? extends Entity> type) {
		Set<T> list = new HashSet<T>();
		if (type.equals(Entity.class))
			list.addAll((Set<T>) this.getEntityList());
		else
			list = (Set<T>) this.getEntityList().stream()
												.filter(e -> e.getClass().isAssignableFrom(type))
												.collect(Collectors.toSet());
		
		return list;
	}
	
	
	
	/**
	 * A map containing all the entities in this world, with their position as their key.
	 */
	private HashMap<String, Entity> entitypositions = new HashMap<String, Entity>();
	
	/**
	 * Returns a map containing all the entities in this world, mapped to their positions.
	 */
	@Basic
	public HashMap<String, Entity> getEntityPositions() {
		return this.entitypositions;
	}
	
	/**
	 * Returns the entity at the given position within the world. If no entity is present at that position,
	 * the method returns null.
	 * 
	 * @param xpos
	 *  The x position to be inspected
	 * @param ypos
	 * 	The y position to be inspected
	 * @return The entity in the world at position (xpos, ypos).
	 *  | result == this.getEntityPositions.get(Double.toString(xpos) + "x" + Double.toString(ypos) + "y")
	 * @return null if there is no entity at the given position.
	 *  | if ! this.getEntityPositions().contains(Double.toString(xpos) + "x" + Double.toString(ypos) + "y")
	 *  |	then result == null
	 */
	public Entity getEntityAt(double xpos, double ypos) {
		String key = Double.toString(xpos)+"x"+Double.toString(ypos)+"y"; 
		if (this.getEntityPositions().containsKey(key))
			return this.getEntityPositions().get(key);
		else{
			return null;
			}
	}
	
	/**
	 * Adds an entity to the map of entity positions of this world.
	 * 
	 * @param entity
	 *  The entity to be added
	 * @post The map will contain the given entity's position as a key.
	 *  | new.getEntityPositions().containsKey(
	 *  |		Double.toString(entity.getPos().getX()) + "x" + Double.toString(entity.getPos().getY()) + "y")
	 * @post The object mapped to the position of the entity is the entity itself.
	 *  | new.getEntityPositions().get(
	 *  | 		Double.toString(entity.getPos().getX()) + "x" + Double.toString(entity.getPos().getY()) + "y") == entity
	 */
	public void addPosition(Entity entity) {
		String key = Double.toString(entity.getPos().getX())+"x"+Double.toString(entity.getPos().getY())+"y"; 
		this.entitypositions.put(key, entity);
	}
	
	/**
	 * Removes the entity at the given position from the map.
	 * 
	 * @param xpos
	 * 	The x coordinate of the entity to be removed
	 * @param ypos
	 *  The y coordinate of the entity to be removed
	 * @post The map of positions will not contain the given position.
	 *  | ! new.getEntityPositions().containsKey(Double.toString(xpos) + "x" + Double.toString(ypos) + "y")
	 */
	public void removePosition(double xpos, double ypos) {
		String key = Double.toString(xpos)+"x"+Double.toString(ypos)+"y";
		this.entitypositions.remove(key);
	}
	
	
	
	
	/**
	 * Returns the time until the next collision in this world occurs.
	 * 
	 * @return The shortest time until one of the entities in this world collides with its boundaries or with another entity.
	 */
	public double getTimeNextCollision() {
		double time = Double.POSITIVE_INFINITY;
		for (Entity entity : this.getEntityList()) {
			double collisiontime = entity.getTimeToCollision();
			if (collisiontime < time && collisiontime > 0)
				time = collisiontime;
			for (Entity other : this.getEntityList()) {
				if(entity.equals(other))
					continue;
				collisiontime = entity.getTimeToCollision(other);
				if (collisiontime < time && collisiontime > 0)
					time = collisiontime;
			}
		}
		return time;
	}
	
	/**
	 * Returns the position of the next collision in this world.
	 * 
	 * @return The position of the next collision in this world.
	 */
	public double[] getPositionNextCollision() {
		double time = Double.POSITIVE_INFINITY;
		Entity collided1 = null;
		Entity collided2 = null;
		for (Entity entity : this.getEntityList()) {
			double collisiontime = entity.getTimeToCollision();
			if (collisiontime < time && collisiontime >= 0) {
				time = collisiontime;
				collided1 = entity;
				collided2 = null;
			}
			for (Entity other : this.getEntityList()) {
				if(entity.equals(other))
					continue;
				collisiontime = entity.getTimeToCollision(other);
				if (collisiontime < time && collisiontime >= 0) {
					time = collisiontime;
					collided1 = entity;
					collided2 = other;
				}
			}
		}
		double[] pos;
		if (time == Double.POSITIVE_INFINITY)
			return null;
		else if (collided2 == null)
			pos = collided1.getCollisionPosition();
		else
			pos = collided1.getCollisionPosition(collided2);
		return pos;
	}
	
	// No try/catch for move, exception is only thrown if time < 0; but here that's not possible.
	public void evolve(double time, CollisionListener collisionListener) throws IllegalArgumentException  {
		if (time < 0 || Double.isNaN(time) || time == Double.POSITIVE_INFINITY)
			throw new IllegalArgumentException("Time must be a real positive value!");
		double movetime;
		double collisiontime;
		while (time > 0) {
			movetime = time;
			boolean nocollision = true;
			collisiontime = this.getTimeNextCollision();
			if (collisiontime <= movetime) {
				movetime = collisiontime;
				nocollision = false;
			}
			for (Entity entity : this.getEntityList())
				entity.move(movetime);
			if (nocollision)
				return;
			doCollisions(collisionListener);
			time = time - movetime;
		}
	}
	
	public void doCollisions(CollisionListener collisionListener) {
		List<Entity> boundarycollisions = new ArrayList<Entity>();
		List<Entity> entitycollisions = new ArrayList<Entity>();
		for (Entity entity : this.getEntityList()) {
			if (entity.apparentlyCollides(this))
				boundarycollisions.add(entity);
			for (Entity other : this.getEntityList()) {
				if ((entity.apparentlyCollides(other))) {
					if (! entitycollisions.contains(entity)){
						entitycollisions.add(entity);
						entitycollisions.add(other);
					}
				}
			}
		}
		doBoundaryCollisions(boundarycollisions, collisionListener);
		doEntityCollisions(entitycollisions, collisionListener);
	}
	
	public void doBoundaryCollisions(List<Entity> boundarycollisions, CollisionListener collisionListener) {
		while (boundarycollisions.size() > 0) {
			Entity collided = boundarycollisions.iterator().next();
			collided.collide(this);
			boundarycollisions.remove(collided);
		}
	}
	
	public void doEntityCollisions(List<Entity> entitycollisions, CollisionListener collisionListener) {
		while (entitycollisions.size() > 0) {
			Entity collided1 = entitycollisions.iterator().next();
			entitycollisions.remove(collided1);
			Entity collided2 = entitycollisions.iterator().next();
			entitycollisions.remove(collided2);
			collided1.collide(collided2);
		}
	}
	
	
	
	
	/**
	 * True when this world is terminated
	 */
	private boolean terminated = false;

	/**
	 * Returns whether or not this world is terminated.
	 */
	@Basic
	public boolean isTerminated() {
		return terminated;
	}

	/**
	 * Terminates the world.
	 * 
	 * @post The world will be terminated.
	 *  | new.isTerminated() == true
	 * @post The world's bullet list will be empty.
	 *  | new.getBulletList().isEmpty() == true
	 * @post The world's ship list will be empty.
	 *  | new.getShipList().isEmpty() == true
	 * @post The world's entity positions map will be empty.
	 *  | new.getEntityPositions().isEmpty() == true
	 */
	public void terminate() {
		for (Entity entity : this.getEntityList())
			entity.setWorld(null);
		this.entitylist.clear();
		this.entitypositions.clear();
		this.terminated = true;
	}
}
