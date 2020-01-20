package asteroids.facade;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import asteroids.expressions.Expression;
import asteroids.model.Asteroid;
import asteroids.model.Bullet;
import asteroids.model.Entity;
import asteroids.model.Function;
import asteroids.model.Planetoid;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part2.CollisionListener;
import asteroids.part3.facade.IFacade;
import asteroids.model.Program;
import asteroids.model.ProgramFactory;
import asteroids.part3.programs.IProgramFactory;
import asteroids.statements.BreakException;
import asteroids.statements.Statement;
import asteroids.util.ModelException;

public class Facade implements IFacade {


	@Override
	public double[] getShipPosition(Ship ship) {
		double[] pos = {ship.getPos().getX(),ship.getPos().getY()};
		return pos;
	}

	@Override
	public double[] getShipVelocity(Ship ship){
		double[] vel = {ship.getVel().getX(),ship.getVel().getY()};
		return vel;
	}

	@Override
	public double getShipRadius(Ship ship) {
		return ship.getRadius();
	}

	@Override
	public double getShipOrientation(Ship ship){
		return ship.getOrientation();
	}

	@Override
	public void turn(Ship ship, double angle) {
		ship.turn(angle);
	}

	@Override
	public double getDistanceBetween(Ship ship1, Ship ship2) throws ModelException {
		try{
			return ship1.getDistanceBetween(ship2);
		}catch(NullPointerException e){
			throw new ModelException("NullPointer getDistanceBetween");
		}
	}

	@Override
	public boolean overlap(Ship ship1, Ship ship2) throws ModelException {
		return ship1.overlap(ship2);
		}

	@Override
	public double getTimeToCollision(Ship ship1, Ship ship2) throws ModelException {
		try {
			return ship1.getTimeToCollision(ship2);
		} catch (IllegalArgumentException e){
			throw new ModelException("Exception getTimeToCollision");
		}
	}

	@Override
	public double[] getCollisionPosition(Ship ship1, Ship ship2) throws ModelException {
		try {
			return ship1.getCollisionPosition(ship2);
		} catch(IllegalArgumentException e){
			throw new ModelException("Exception getCollisionPosition");
		}
	}

	@Override
	public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double direction, double mass)
			throws ModelException {
		try {
		return new Ship(x, y, xVelocity, yVelocity, radius, direction, mass);
		} catch (IllegalArgumentException e) {
			throw new ModelException("Illegal argument for creating ship!");
		}
	}

	@Override
	public void terminateShip(Ship ship) {
		ship.terminate();
	}

	@Override
	public boolean isTerminatedShip(Ship ship) {
		return ship.isTerminated();
	}

	@Override
	public double getShipMass(Ship ship) {
		return ship.getTotalMass();
	}

	@Override
	public World getShipWorld(Ship ship) {
		return ship.getWorld();
	}

	@Override
	public boolean isShipThrusterActive(Ship ship) {
		return ship.checkThruster();
	}

	@Override
	public void setThrusterActive(Ship ship, boolean active) {
		if (active)
			ship.thrustOn();
		else ship.thrustOff();
	}

	@Override
	public double getShipAcceleration(Ship ship) {
		return ship.getAcceleration();
	}

	@Override
	public Bullet createBullet(double x, double y, double xVelocity, double yVelocity, double radius) throws ModelException {
		try{
			return new Bullet(x, y, xVelocity, yVelocity, radius);
		} catch (IllegalArgumentException e){
			throw new ModelException("Illegal argument for creating bullet!");
		}
	}

	@Override
	public void terminateBullet(Bullet bullet) {
		bullet.terminate();
	}

	@Override
	public boolean isTerminatedBullet(Bullet bullet) {
		return bullet.isTerminated();
	}

	@Override
	public double[] getBulletPosition(Bullet bullet) {
		double[] pos = {bullet.getPos().getX(), bullet.getPos().getY()};
		return pos;
	}

	@Override
	public double[] getBulletVelocity(Bullet bullet){
		double[] vel = {bullet.getVel().getX(),bullet.getVel().getY()};
		return vel;
	}

	@Override
	public double getBulletRadius(Bullet bullet) {
		return bullet.getRadius();
	}

	@Override
	public double getBulletMass(Bullet bullet) {
		return bullet.getMass();
	}

	@Override
	public World getBulletWorld(Bullet bullet) {
		return bullet.getWorld();
	}

	@Override
	public Ship getBulletShip(Bullet bullet) {
		return bullet.getShip();
	}

	@Override
	public Ship getBulletSource(Bullet bullet) {
		return bullet.getSource();
	}

	@Override
	public World createWorld(double width, double height) {
		return new World(width, height);
	}

	@Override
	public void terminateWorld(World world){
		world.terminate();
	}

	@Override
	public boolean isTerminatedWorld(World world) {
		return world.isTerminated();
	}

	@Override
	public double[] getWorldSize(World world) {
		double[] size = {world.getWidth(), world.getHeight()};
		return size;
	}

	@Override
	public Set<? extends Ship> getWorldShips(World world) {
		return world.getSpecificList(Ship.class);
	}

	@Override
	public Set<? extends Bullet> getWorldBullets(World world) {
		return world.getSpecificList(Bullet.class);
	}

	@Override
	public void addShipToWorld(World world, Ship ship) throws ModelException {
		try {world.addEntity(ship);
		} catch (Throwable e) {
			throw new ModelException("Ship out of bounds!");
		}
	}

	@Override
	public void removeShipFromWorld(World world, Ship ship) throws ModelException {
		try {world.removeEntity(ship);
		} catch (Throwable e) {
			throw new ModelException("Ship is not in world!");
		}
	}

	@Override
	public void addBulletToWorld(World world, Bullet bullet) throws ModelException {
		try { world.addEntity(bullet);
		} catch (IllegalArgumentException e) {
			throw new ModelException("Bullet could not be added!");
		}
	}

	@Override
	public void removeBulletFromWorld(World world, Bullet bullet) throws ModelException {
		try {world.removeEntity(bullet);
		} catch (IllegalArgumentException e) {
			throw new ModelException("Bullet is not in world!");
		}
	}

	@Override
	public Set<? extends Bullet> getBulletsOnShip(Ship ship) {
		return ship.getBulletList();
	}

	@Override
	public int getNbBulletsOnShip(Ship ship) {
		return ship.getBulletList().size();
	}

	@Override
	public void loadBulletOnShip(Ship ship, Bullet bullet) throws ModelException {
		try{
			ship.addBullet(bullet);
		}catch(Throwable e){
			throw new ModelException("Illegal bullet!");
		}
	}

	@Override
	public void loadBulletsOnShip(Ship ship, Collection<Bullet> bullets) throws ModelException {
		try {
		ship.addSeveralBullets(bullets);
		} catch (Throwable e){
			throw new ModelException("Illegal bullets!");
		}
	}

	@Override
	public void removeBulletFromShip(Ship ship, Bullet bullet) throws ModelException {
		try{
			ship.removeBullet(bullet);
		} catch(IllegalArgumentException e){
			throw new ModelException("Bullet does not belong to this ship!"); 
		}
	}

	@Override
	public void fireBullet(Ship ship) throws ModelException {
		try {
			ship.fireBullet();
		} catch(IllegalArgumentException e){
			//so be it!
		}
		
	}

	@Override
	public double getTimeCollisionBoundary(Object object) {
		return ((Entity)object).getTimeToCollision();
	}

	@Override
	public double[] getPositionCollisionBoundary(Object object) {
		return ((Entity)object).getCollisionPosition();
	}

	@Override
	public double getTimeCollisionEntity(Object entity1, Object entity2) throws ModelException {
		try{
			return ((Entity)entity1).getTimeToCollision((Entity)entity2);
		} catch(IllegalArgumentException e){
			throw e;//new ModelException("Exception getTimeCollisionEntity");
		}
	}

	@Override
	public double[] getPositionCollisionEntity(Object entity1, Object entity2) throws ModelException {
		try{
			return ((Entity)entity1).getCollisionPosition((Entity)entity2);
		} catch(IllegalArgumentException e){
			throw e;//new ModelException("Exception getPositionCollisionEntity");
		}
	}

	@Override
	public double getTimeNextCollision(World world) {
		return world.getTimeNextCollision();
	}

	@Override
	public double[] getPositionNextCollision(World world) {
		return world.getPositionNextCollision();
	}

	@Override
	public void evolve(World world, double dt, CollisionListener collisionListener) throws ModelException {
		try{
			world.evolve(dt, collisionListener);
		} catch(IllegalArgumentException evolve){
			throw new ModelException("Exception evolve");
		}
	}

	@Override
	public Object getEntityAt(World world, double x, double y) {
		return world.getEntityAt(x, y);
	}

	@Override
	public Set<? extends Object> getEntities(World world) {
		return world.getSpecificList(Entity.class);
	}

	@Override
	public int getNbStudentsInTeam() {
		return 2;
	}

	@Override
	public Set<? extends Asteroid> getWorldAsteroids(World world) throws ModelException {
		return world.getSpecificList(Asteroid.class);
	}

	@Override
	public void addAsteroidToWorld(World world, Asteroid asteroid) throws ModelException {
		try {
		world.addEntity(asteroid);
		} catch (Throwable e) {
			throw new ModelException("Asteroid is out of bounds!");
		}
	}

	@Override
	public void removeAsteroidFromWorld(World world, Asteroid asteroid) throws ModelException {
		world.removeEntity(asteroid);
	}

	@Override
	public Set<? extends Planetoid> getWorldPlanetoids(World world) throws ModelException {
		return world.getSpecificList(Planetoid.class);
	}

	@Override
	public void addPlanetoidToWorld(World world, Planetoid planetoid) throws ModelException {
		try {
		world.addEntity(planetoid);
		} catch (Throwable e) {
			throw new ModelException("Asteroid is out of bounds!");
		}
	}

	@Override
	public void removePlanetoidFromWorld(World world, Planetoid planetoid) throws ModelException {
		world.removeEntity(planetoid);
	}

	@Override
	public Asteroid createAsteroid(double x, double y, double xVelocity, double yVelocity, double radius)
			throws ModelException {
		try{
			return new Asteroid(x, y, xVelocity, yVelocity, radius);
		} catch (IllegalArgumentException e) {
			throw new ModelException ("Illegal argument for creating asteroid!");
		}
	}

	@Override
	public void terminateAsteroid(Asteroid asteroid) throws ModelException {
		asteroid.terminate();
	}

	@Override
	public boolean isTerminatedAsteroid(Asteroid asteroid) throws ModelException {
		return asteroid.isTerminated();
	}

	@Override
	public double[] getAsteroidPosition(Asteroid asteroid) throws ModelException {
		double[] pos = {asteroid.getPos().getX(), asteroid.getPos().getY()};
		return pos;
	}

	@Override
	public double[] getAsteroidVelocity(Asteroid asteroid) throws ModelException {
		double[] velocity = {asteroid.getVel().getX(), asteroid.getVel().getY()};
		return velocity;
	}

	@Override
	public double getAsteroidRadius(Asteroid asteroid) throws ModelException {
		return asteroid.getRadius();
	}

	@Override
	public double getAsteroidMass(Asteroid asteroid) throws ModelException {
		return asteroid.getMass();
	}

	@Override
	public World getAsteroidWorld(Asteroid asteroid) throws ModelException {
		return asteroid.getWorld();
	}

	@Override
	public Planetoid createPlanetoid(double x, double y, double xVelocity, double yVelocity, double radius,
			double totalTraveledDistance) throws ModelException {
		try{
			return new Planetoid(x, y, xVelocity, yVelocity, radius, totalTraveledDistance);
		} catch (IllegalArgumentException e) {
			throw new ModelException ("Illegal argument for creating planetoid!");
		}
	}

	@Override
	public void terminatePlanetoid(Planetoid planetoid) throws ModelException {
		planetoid.terminate();
	}

	@Override
	public boolean isTerminatedPlanetoid(Planetoid planetoid) throws ModelException {
		return planetoid.isTerminated();
	}

	@Override
	public double[] getPlanetoidPosition(Planetoid planetoid) throws ModelException {
		double[] pos = {planetoid.getPos().getX(), planetoid.getPos().getY()};
		return pos;
	}

	@Override
	public double[] getPlanetoidVelocity(Planetoid planetoid) throws ModelException {
		double[] velocity = {planetoid.getVel().getX(), planetoid.getVel().getY()};
		return velocity;
	}

	@Override
	public double getPlanetoidRadius(Planetoid planetoid) throws ModelException {
		return planetoid.getRadius();
	}

	@Override
	public double getPlanetoidMass(Planetoid planetoid) throws ModelException {
		return planetoid.getMass();
	}

	@Override
	public double getPlanetoidTotalTraveledDistance(Planetoid planetoid) throws ModelException {
		return planetoid.getTraveledDistance();
	}

	@Override
	public World getPlanetoidWorld(Planetoid planetoid) throws ModelException {
		return planetoid.getWorld();
	}

	@Override
	public Program getShipProgram(Ship ship) throws ModelException {
		return ship.getProgram();
	}

	@Override
	public void loadProgramOnShip(Ship ship, Program program) throws ModelException {
		ship.setProgram(program);
	}

	@Override
	public List<Object> executeProgram(Ship ship, double dt) throws ModelException {
		try {
		return ship.doProgram(dt);
		} catch (BreakException e) {
			throw new ModelException("Illegal BreakStatement");
		} catch (IllegalArgumentException e) {
			throw new ModelException("Error on run Program");
		}
	}

	@Override
	public IProgramFactory<?, ?, ?, ? extends Program> createProgramFactory() throws ModelException {
		return new ProgramFactory<Expression<Object>, Statement, Function, Program>();
	}

	

}
