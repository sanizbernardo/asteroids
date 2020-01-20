package asteroids.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Asteroid;
import asteroids.model.Bullet;
import asteroids.model.Planetoid;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.facade.Facade;
import asteroids.part3.facade.IFacade;
import asteroids.util.ModelException;

public class Part2TestPartial {

	private static final double EPSILON = 0.0001;

	IFacade facade;

	@Before
	public void setUp() {
		facade = new Facade();
	}
	
	/**************
	 * WORLD: methods
	 *************/
	
	@Test
	public void testCreateWorld() throws ModelException {
		World world = facade.createWorld(1000, 800);
		assertEquals(1000, facade.getWorldSize(world)[0], EPSILON);
		assertEquals(800, facade.getWorldSize(world)[1], EPSILON);
		assertTrue(facade.getWorldShips(world).isEmpty());
		assertTrue(facade.getWorldBullets(world).isEmpty());
	}
	
	@Test
	public void testTerminateWorld() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship1 = facade.createShip(100, 100, 10, -10, 10, 0, 500);
		Ship ship2 = facade.createShip(300, 300, -10, -10, 10, 0, 500);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		facade.terminateWorld(world);
		assertTrue(facade.getShipWorld(ship1) == null);
		assertTrue(facade.getShipWorld(ship2) == null);
		assertTrue(facade.getWorldShips(world).isEmpty());
		assertTrue(facade.isTerminatedWorld(world));
	}
	
	@Test
	public void testAddShipToWorld() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(50, 50, 0, 0, 20, 0, 500);
		Ship ship2 = facade.createShip(500, 500, 0, 0, 20, 0, 500);
		assertTrue(facade.getWorldShips(world).isEmpty());
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		assertTrue(facade.getWorldShips(world).size() == 2);
		assertTrue(facade.getShipWorld(ship1) == world);
		assertTrue(facade.getShipWorld(ship2) == world);
	}
	
	@Test (expected = ModelException.class)
	public void testAddOverlappingShipsToWorld() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(50, 50, 0, 0, 20, 0, 500);
		Ship ship2 = facade.createShip(60, 50, 0, 0, 20, 0, 500);
		assertTrue(facade.getWorldShips(world).isEmpty());
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
	}
	
	@Test(expected = ModelException.class)
	public void testAddShipOutOfBoundsToWorld() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(50, 50, 0, 0, 20, 0, 500);
		Ship ship2 = facade.createShip(5500, 50, 0, 0, 20, 0, 500);
		assertTrue(facade.getWorldShips(world).isEmpty());
		facade.addShipToWorld(world, ship1);
		assertTrue(facade.getWorldShips(world).size() == 1);
		assertTrue(facade.getShipWorld(ship1) == world);
		facade.addShipToWorld(world, ship2);
	}
	
	@Test
	public void testAddBulletToWorld() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Bullet bullet1 = facade.createBullet(2500, 2500, 0, 0, 2);
		Bullet bullet2 = facade.createBullet(2000, 2500, 0, 0, 2);
		assertTrue(facade.getWorldBullets(world).isEmpty());
		facade.addBulletToWorld(world, bullet1);
		assertTrue(facade.getWorldBullets(world).size() == 1);
		assertTrue(facade.getBulletWorld(bullet1) == world);
		facade.addBulletToWorld(world, bullet2);
		assertTrue(facade.getWorldBullets(world).size() == 2);
		assertTrue(facade.getBulletWorld(bullet2) == world);
	}
	
	
	
	@Test(expected = ModelException.class)
	public void testAddBulletOutOfBoundsToWorld() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Bullet bullet = facade.createBullet(5500, 2500, 0, 0, 2);
		assertTrue(facade.getWorldBullets(world).isEmpty());
		facade.addBulletToWorld(world, bullet);
	}
	
	@Test (expected = ModelException.class)
	public void testAddBulletOverlappingWithShipToWorld()throws ModelException{
		World world = facade.createWorld(5000, 5000);
		Ship ship = facade.createShip(2500, 2500, 0, 0, 20, 0, 500);
		Bullet bullet = facade.createBullet(2490, 2500, 0, 0, 3);
		assertTrue(facade.getWorldBullets(world).isEmpty());
		assertTrue(facade.getWorldShips(world).isEmpty());
		facade.addShipToWorld(world, ship);
		assertTrue(facade.getWorldShips(world).size() == 1);
		facade.addBulletToWorld(world, bullet);
	}
	
	@Test(expected = ModelException.class)
	public void testAddBulletFromShipToWorld() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Ship ship = facade.createShip(2500, 2500, 0, 0, 20, 0, 500);
		Bullet bullet = facade.createBullet(200, 2500, 0, 0, 3);
		facade.addShipToWorld(world, ship);
		facade.loadBulletOnShip(ship, bullet);
		assertEquals(1, world.getEntityList().size());
		assertTrue(world.getSpecificList(Bullet.class).isEmpty());
		facade.addBulletToWorld(world, bullet);	
	}
	
	@Test
	public void testGetSpecificList() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Ship ship = facade.createShip(2500, 2500, 0, 0, 20, 0, 500);
		Bullet bullet = facade.createBullet(200, 2500, 0, 0, 3);
		facade.addShipToWorld(world, ship);
		facade.addBulletToWorld(world, bullet);
		assertEquals(2,facade.getEntities(world).size());
		assertEquals(1,facade.getWorldBullets(world).size());
		assertEquals(1,facade.getWorldShips(world).size());
	}
	
	@Test 
	public void testGetEntityAt() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Bullet bullet =  facade.createBullet(200, 100, -20, 0, 1);
		Ship ship1 = facade.createShip(100, 100, 20, 0, 10, 0, 500);
		Ship ship2  = facade.createShip(105, 100, 20, 0, 10, 0, 500);
		Ship ship3 = facade.createShip(200, 200, 20, 0, 10, 0, 500);
		facade.addBulletToWorld(world, bullet);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship3);
		boolean caught = false;
		try{
			facade.addShipToWorld(world, ship2);
		} catch (ModelException e) {
			caught = true;
			assertEquals(3, facade.getEntities(world).size());
			assertEquals(3, world.getEntityPositions().size());
			assertTrue(facade.getEntityAt(world, 100.0, 100.0).equals(ship1));
			assertTrue(facade.getEntityAt(world, 200.0, 200.0).equals(ship3));
			assertTrue(facade.getEntityAt(world, 200.0, 100.0).equals(bullet));
		}
		assertTrue(caught);
	}
	
	@Test
	public void testGetEntityAtAfterMove() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Bullet bullet =  facade.createBullet(200, 100, -10, 0, 1);
		Ship ship1  = facade.createShip(100, 100, 20, 0, 10, 0, 500);
		Ship ship2 = facade.createShip(200, 200, 20, 0, 10, 0, 500);
		facade.addBulletToWorld(world, bullet);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		facade.evolve(world, 20, null);
		assertEquals(1, facade.getEntities(world).size());
		assertEquals(1, world.getEntityPositions().size());
		assertTrue(facade.getEntityAt(world, 600.0, 200.0).equals(ship2));
	}
	
	@Test
	public void testEvolveShipWithActiveThruster() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Ship ship = facade.createShip(100, 120, 10, 0, 50, Math.PI, 1.1E18);
		facade.addShipToWorld(world, ship);
		facade.setThrusterActive(ship, true);
		assertEquals(1.0, facade.getShipAcceleration(ship), EPSILON);
		assertTrue(facade.isShipThrusterActive(ship));
		facade.evolve(world, 1, null);
		assertEquals(9, facade.getShipVelocity(ship)[0], EPSILON);
		assertEquals(0, facade.getShipVelocity(ship)[1], EPSILON);
	}
	
	@Test
	public void testGetTimeNextCollision() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(200,100,100,0,100,0,500);
		Ship ship2 = facade.createShip(4800,100,-100,0,100,0,500);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		assertEquals(22, facade.getTimeNextCollision(world), EPSILON);
	}
	@Test
	public void testGetPositionNextCollision() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(200,100,100,0,100,0,500);
		Ship ship2 = facade.createShip(4800,100,-100,0,100,0,500);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		assertEquals(2500, facade.getPositionNextCollision(world)[0], EPSILON);
		assertEquals(100, facade.getPositionNextCollision(world)[1], EPSILON);
	}
	
	@Test
	public void testEvolveTwoShipsOnlyX() throws ModelException{
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(1200,500,-100,0,100,0,500);
		Ship ship2 = facade.createShip(3800,500,100,0,100,0,500);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		world.evolve(40, null);
		assertEquals(1800,ship1.getPos().getX(),EPSILON);
		assertEquals(500,ship1.getPos().getY(),EPSILON);
		assertEquals(3200,ship2.getPos().getX(),EPSILON);
		assertEquals(500,ship2.getPos().getY(),EPSILON);
		assertEquals(-100,ship1.getVel().getX(),EPSILON);
		assertEquals(0,ship1.getVel().getY(),EPSILON);
		assertEquals(100,ship2.getVel().getX(),EPSILON);
		assertEquals(0,ship2.getVel().getY(),EPSILON);
	}
	
	@Test
	public void testEvolveFourShipsOnlyX() throws ModelException{
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(1200,500,-100,0,100,0,500);
		Ship ship2 = facade.createShip(3800,500,100,0,100,0,500);
		Ship ship3= facade.createShip(1200,1000,-100,0,100,0,500);
		Ship ship4 = facade.createShip(3000,1000,100,0,100,0,500);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		facade.addShipToWorld(world, ship3);
		facade.addShipToWorld(world, ship4);
		world.evolve(40, null);
		assertEquals(1800,ship1.getPos().getX(),EPSILON);
		assertEquals(500,ship1.getPos().getY(),EPSILON);
		assertEquals(3200,ship2.getPos().getX(),EPSILON);
		assertEquals(500,ship2.getPos().getY(),EPSILON);
		assertEquals(2600,ship3.getPos().getX(),EPSILON);
		assertEquals(1000,ship3.getPos().getY(),EPSILON);
		assertEquals(3200,ship4.getPos().getX(),EPSILON);
		assertEquals(1000,ship4.getPos().getY(),EPSILON);
		assertEquals(-100,ship1.getVel().getX(),EPSILON);
		assertEquals(0,ship1.getVel().getY(),EPSILON);
		assertEquals(100,ship2.getVel().getX(),EPSILON);
		assertEquals(0,ship2.getVel().getY(),EPSILON);
		assertEquals(-100,ship3.getVel().getX(),EPSILON);
		assertEquals(0,ship3.getVel().getY(),EPSILON);
		assertEquals(100,ship4.getVel().getX(),EPSILON);
		assertEquals(0,ship4.getVel().getY(),EPSILON);
		
	}
	
	
	@Test
	public void testEvolveTwoShipsBothDirections() throws ModelException{
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(2000,2500,100,0,100,0,500);
		Ship ship2 = facade.createShip(2500,2050,0,50,100,0,500);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		world.evolve(7, null);
		assertEquals(2700,ship1.getPos().getX(),EPSILON);
		assertEquals(2600,ship1.getPos().getY(),EPSILON);
		assertEquals(2500,ship2.getPos().getX(),EPSILON);
		assertEquals(2300,ship2.getPos().getY(),EPSILON);
		assertEquals(100,ship1.getVel().getX(),EPSILON);
		assertEquals(50,ship1.getVel().getY(),EPSILON);
		assertEquals(0,ship2.getVel().getX(),EPSILON);
		assertEquals(0,ship2.getVel().getY(),EPSILON);
	}
	
	@Test
	public void testEvolveThreeShipsAndBullet() throws ModelException{
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(1200,500,-100,0,100,0,500);
		Ship ship2 = facade.createShip(3800,500,100,0,100,0,500);
		Ship ship3 = facade.createShip(1200,1000,-100,0,100,0,500);
		Bullet bullet = facade.createBullet(1000, 690, 0, 100, 10);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		facade.addShipToWorld(world, ship3);
		facade.addBulletToWorld(world, bullet);
		world.evolve(17, null);
		assertEquals(700,ship1.getPos().getX(),EPSILON);
		assertEquals(500,ship1.getPos().getY(),EPSILON);
		assertEquals(4300,ship2.getPos().getX(),EPSILON);
		assertEquals(500,ship2.getPos().getY(),EPSILON);
		assertEquals(100,ship1.getVel().getX(),EPSILON);
		assertEquals(0,ship1.getVel().getY(),EPSILON);
		assertEquals(-100,ship2.getVel().getX(),EPSILON);
		assertEquals(0,ship2.getVel().getY(),EPSILON);
		assertTrue(bullet.isTerminated());
		assertTrue(ship3.isTerminated());
		assertEquals(2,world.getEntityList().size());
		assertTrue(world.getSpecificList(Bullet.class).isEmpty());
		assertEquals(2,world.getEntityList().size());
	}
	
	@Test
	public void testEvolveBullet() throws ModelException{
		World world = facade.createWorld(2000, 2000);
		Bullet bullet = facade.createBullet(1000, 1000, 0, 100, 100);
		facade.addBulletToWorld(world, bullet);
		assertEquals(1,world.getEntityList().size());
		assertEquals(1,world.getSpecificList(Bullet.class).size());
		world.evolve(9, null);
		assertEquals(1000,bullet.getPos().getX(),EPSILON);
		assertEquals(1900,bullet.getPos().getY(),EPSILON);
		assertEquals(0,bullet.getVel().getX(),EPSILON);
		assertEquals(-100,bullet.getVel().getY(),EPSILON);
		assertEquals(2,bullet.getHitCount());
		world.evolve(18, null);
		assertEquals(1000,bullet.getPos().getX(),EPSILON);
		assertEquals(100,bullet.getPos().getY(),EPSILON);
		assertEquals(0,bullet.getVel().getX(),EPSILON);
		assertEquals(100,bullet.getVel().getY(),EPSILON);
		assertEquals(1,bullet.getHitCount());
		world.evolve(18, null);
		assertTrue(bullet.isTerminated());
		assertTrue(world.getSpecificList(Bullet.class).isEmpty());
		assertTrue(world.getEntityList().isEmpty());
		
	}

	
	/**************
	 * Entity: methods
	 *************/
	@Test
	public void testIsValidPosition()throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(200,100,100,0,100,0,500);
		Ship ship2 = facade.createShip(5800,100,-100,0,100,0,500);
		Ship ship3 = facade.createShip(4000,10000,-100,0,100,0,500);
		assertTrue(ship1.isValidXPosition(world, ship1.getPos().getX()));
		assertTrue(ship1.isValidYPosition(world, ship1.getPos().getY()));
		assertTrue(!ship2.isValidXPosition(world, ship2.getPos().getX()));
		assertTrue(ship2.isValidYPosition(world, ship2.getPos().getY()));
		assertTrue(ship3.isValidXPosition(world, ship3.getPos().getX()));
		assertTrue(!ship3.isValidYPosition(world, ship3.getPos().getY()));
	}
	
	@Test
	public void testSetSpeed() throws ModelException {
		Ship ship = facade.createShip(200,100,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,100,0,500);
		double[] velocity = facade.getShipVelocity(ship);
		assertEquals(2.121320344e5, velocity[0],EPSILON*1e5);
		assertEquals(-2.121320344e5, velocity[1], EPSILON*1e5);
	}
	
	@Test
	public void testOverlap() throws ModelException{
		Ship ship1 = facade.createShip(2500,2500,100,0,100,0,500);
		Ship ship2 = facade.createShip(2450,2500,-100,0,100,0,500);
		Ship ship3 = facade.createShip(4000,1000,-100,0,100,0,500);
		assertTrue(ship1.overlap(ship1));
		assertTrue(ship1.overlap(ship2));
		assertTrue(!ship1.overlap(ship3));
		assertTrue(ship2.overlap(ship2));
		assertTrue(!ship2.overlap(ship3));
		assertTrue(ship3.overlap(ship3));
	}
	
	@Test
	public void testMass() throws ModelException{
		Ship ship = facade.createShip(2500,2500,100,0,100,0,10);
		Bullet bullet = facade.createBullet(2000, 2000, 250, 0, 1);
		assertEquals(5.94808e18,ship.getMass(),EPSILON*1e18);
		assertEquals(3.267256e13,bullet.getMass(),EPSILON*1e13);
		
	}
	
	@Test
	public void testTimeToCollision() throws ModelException {
		World world = facade.createWorld(2000, 2000);
		Ship ship1 = facade.createShip(100, 100, 3, 5, 10, 2, 10);
		Ship ship2 = facade.createShip(100, 200, 3, -5, 20, 2, 10);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		double time = facade.getTimeToCollision(ship1, ship2);
		assertEquals(7, time, EPSILON);
	}
	
	@Test
	public void testTimeToCollisionInf() throws ModelException {
		Ship ship1 = facade.createShip(100, 100, 3, 5, 10, 2, 10);
		Ship ship2 = facade.createShip(100, 200, -3, -5, 20, 2, 10);
		double time = facade.getTimeToCollision(ship1, ship2);
		assertEquals(Double.POSITIVE_INFINITY, time, EPSILON);
	}
	
	@Test
	public void testTimeToCollisionOverlap() throws ModelException {
		Ship ship1 = facade.createShip(100, 100, 3, 5, 10, 2, 10);
		Ship ship2 = facade.createShip(105, 105, -3, -5, 20, 2, 10);
		facade.getTimeToCollision(ship1, ship2);
	}
	
	@Test
	public void testCollisionPosition() throws ModelException {
		World world = facade.createWorld(2000, 2000);
		Ship ship1 = facade.createShip(100, 100, 3, 5, 10, 2, 10);
		Ship ship2 = facade.createShip(100, 200, 3, -5, 20, 2, 10);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		double[] pos = facade.getCollisionPosition(ship1, ship2);
		assertEquals(121, pos[0], EPSILON);
		assertEquals(145, pos[1], EPSILON);
	}
	
	@Test
	public void testCollisionPositionNull() throws ModelException {
		Ship ship1 = facade.createShip(100, 100, 3, 5, 10, 2, 10);
		Ship ship2 = facade.createShip(100, 200, -3, -5, 20, 2, 10);
		double[] pos = facade.getCollisionPosition(ship1, ship2);
		assertEquals(null, pos);
	}
	
	@Test
	public void testCollisionPositionOverlap() throws ModelException {
		Ship ship1 = facade.createShip(100, 100, 3, 5, 10, 2, 10);
		Ship ship2 = facade.createShip(105, 105, -3, -5, 20, 2, 10);
		facade.getCollisionPosition(ship1, ship2);
	}
	
	@Test
	public void testGetTimeToCollisionWorld() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(100, 100, -10 , 0, 10, 2, 10);
		facade.addShipToWorld(world, ship);
		assertEquals(9,facade.getTimeCollisionBoundary(ship),EPSILON);
	}
	
	@Test
	public void testGetTimeToCollisionWorldDiagonal() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(100, 100, 1 , 5, 10, 2, 10);
		facade.addShipToWorld(world, ship);
		assertEquals(178,facade.getTimeCollisionBoundary(ship),EPSILON);
	}
	
	@Test
	public void testGetCollisionPositionWorld()throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(100, 100, 0 , 5, 10, 2, 10);
		facade.addShipToWorld(world, ship);
		double[] pos = ship.getCollisionPosition();
		assertEquals(100,pos[0],EPSILON);
		assertEquals(1000,pos[1],EPSILON);
	}
	
	@Test
	public void testGetCollisionPositionWorldDiagonal()throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(100, 100, 1 , 5, 10, 2, 10);
		facade.addShipToWorld(world, ship);
		double[] pos = ship.getCollisionPosition();
		assertEquals(278,pos[0],EPSILON);
		assertEquals(1000,pos[1],EPSILON);
	}
	
	
	/**************
	 * SHIP: methods
	 *************/
	
	@Test
	public void testCreateShip() throws ModelException {
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI, 200);
		assertNotNull(ship);
		double[] position = facade.getShipPosition(ship);
		assertNotNull(position);
		assertEquals(100, position[0], EPSILON);
		assertEquals(200, position[1], EPSILON);
		assertEquals(20, facade.getShipRadius(ship), EPSILON);
		assertEquals(4.7584656e16,facade.getShipMass(ship),EPSILON*1e16);
	}

	@Test (expected = ModelException.class)
	public void testCreateShipXIsNan() throws ModelException {
		facade.createShip(Double.NaN, 200, 10, -10, 20, Math.PI,200);
	}
	
	@Test
	public void testCreateShipYIsNegInf() throws ModelException {
		Ship ship = facade.createShip(20, Double.NEGATIVE_INFINITY, 10, -10, 20, Math.PI,20);
		assertTrue(Double.isInfinite(facade.getShipPosition(ship)[1]));
	}
	
	@Test
	public void testCreateShipXIsPosInf() throws ModelException {
		Ship ship = facade.createShip(Double.POSITIVE_INFINITY, 200, 10, -10, 20, Math.PI,20);
		assertTrue(Double.isInfinite(facade.getShipPosition(ship)[0]));
	}
	
	@Test
	public void testAddBulletToShip() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI, 200);
		Bullet bullet =  facade.createBullet(100, 200, -20, 0, 1);
		facade.addShipToWorld(world, ship);
		facade.loadBulletOnShip(ship, bullet);
		assertEquals(1,world.getEntityList().size());
		assertEquals(1,ship.getBulletList().size());
		assertTrue(world.getSpecificList(Bullet.class).isEmpty());
	}
	
	@Test(expected = ModelException.class)
	public void testAddBulletToShipLargerRadius() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI, 200);
		Bullet bullet =  facade.createBullet(200, 100, -20, 0, 25);
		facade.addShipToWorld(world, ship);
		facade.loadBulletOnShip(ship, bullet);
	}
	
	@Test(expected = ModelException.class)
	public void testAddBulletToShipSameRadius() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI, 200);
		Bullet bullet =  facade.createBullet(200, 100, -20, 0, 20);
		facade.addShipToWorld(world, ship);
		facade.loadBulletOnShip(ship, bullet);
	}
	
	@Test
	public void testLoadBulletOnShipOverlappingBullets() throws ModelException {
		Ship ship = facade.createShip(100, 120, 10, 5, 500, 0, 1.0E20);
		Bullet bullet1 = facade.createBullet(100, 120, 10, 5, 50);
		Bullet bullet2 = facade.createBullet(130, 110, 10, 5, 30);
		facade.loadBulletOnShip(ship, bullet1);
		facade.loadBulletOnShip(ship, bullet2);
		assertEquals(2, facade.getNbBulletsOnShip(ship));
	}
	
	@Test
	public void testAddSeveralBullets() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI, 200);
		facade.addShipToWorld(world, ship);
		Bullet bullet1 = facade.createBullet(100, 200, 10, 5, 10);
		Bullet bullet2 = facade.createBullet(130, 200, 10, 5, 10);
		Bullet bullet3 = facade.createBullet(100, 200, 10, 5, 10);
		Bullet bullet4 = facade.createBullet(100, 200, 10, 5, 10);
		Bullet bullet5 = facade.createBullet(100, 200, 10, 5, 10);	
		List <Bullet> bullets = new ArrayList<Bullet>();
		bullets.add(bullet1);
		bullets.add(bullet2);
		bullets.add(bullet3);
		bullets.add(bullet4);
		bullets.add(bullet5);
		Collection <Bullet> col = bullets;
		facade.loadBulletsOnShip(ship, col);
		assertEquals(4, facade.getNbBulletsOnShip(ship));
		assertEquals(1,world.getEntityList().size());
		assertTrue(world.getSpecificList(Bullet.class).isEmpty());
	}
	
	@Test
	public void testRemoveBullet() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI, 200);
		Bullet bullet = facade.createBullet(100, 200, 0, 0, 10);
		facade.addShipToWorld(world, ship);
		facade.loadBulletOnShip(ship, bullet);
		assertTrue(world.getSpecificList(Bullet.class).isEmpty());
		assertEquals(1,world.getEntityList().size());
		facade.removeBulletFromShip(ship, bullet);
		assertTrue(ship.getBulletList().isEmpty());		
	}
	
	@Test
	public void testSetWorld() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI, 200);
		ship.setWorld(world);
		assertEquals(world, ship.getWorld());
	}
	
	@Test
	public void testSetWorldNull() throws ModelException{
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI, 200);
		ship.setWorld(null);
		assertEquals(null, ship.getWorld());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetWorldOverlappingShips() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship1 = facade.createShip(500, 200, 10, -10, 100, Math.PI, 200);
		Ship ship2 = facade.createShip(450, 200, 10, -10, 100, Math.PI, 200);
		ship1.setWorld(world);
		world.getEntityList().add(ship1);
		assertEquals(world, ship1.getWorld());
		ship2.setWorld(world);
	}
	
	@Test
	public void testTotalMass() throws ModelException{
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI, 200);
		Bullet bullet1 = facade.createBullet(100, 200, 250, 0, 1);
		Bullet bullet2 = facade.createBullet(100, 200, 250, 0, 1);
		facade.loadBulletOnShip(ship, bullet1);
		facade.loadBulletOnShip(ship, bullet2);
		assertEquals(4.765e16,ship.getTotalMass(),EPSILON*1e16);
	}
	
	@Test
	public void testGetAcceleration() throws ModelException{
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI, 200);
		Bullet bullet1 = facade.createBullet(100, 200, 250, 0, 1);
		Bullet bullet2 = facade.createBullet(100, 200, 250, 0, 1);
		facade.setThrusterActive(ship, true);
		facade.loadBulletOnShip(ship, bullet1);
		facade.loadBulletOnShip(ship, bullet2);
		assertEquals(23.0849,ship.getAcceleration(),EPSILON);
	}
	
	@Test
	public void testAccelerate() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI, 200);
		Bullet bullet1 = facade.createBullet(100, 200, 250, 0, 1);
		Bullet bullet2 = facade.createBullet(100, 200, 250, 0, 1);
		facade.addShipToWorld(world, ship);
		facade.loadBulletOnShip(ship, bullet1);
		facade.loadBulletOnShip(ship, bullet2);
		ship.thrustOn();
		ship.accelerate(2);
		assertEquals(-36.16998,ship.getVel().getX(),EPSILON);
		assertEquals(-10,ship.getVel().getY(),EPSILON);		
	}
	
	@Test
	public void testMove() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI, 200);
		Bullet bullet1 = facade.createBullet(100, 200, 250, 0, 1);
		Bullet bullet2 = facade.createBullet(100, 200, 250, 0, 1);
		facade.addShipToWorld(world, ship);
		facade.loadBulletOnShip(ship, bullet1);
		facade.loadBulletOnShip(ship, bullet2);
		ship.thrustOn();
		ship.move(2);
		assertEquals(120, ship.getPos().getX(),EPSILON);
		assertEquals(180, ship.getPos().getY(),EPSILON);
		assertEquals(-36.16998,ship.getVel().getX(),EPSILON);
		assertEquals(-10,ship.getVel().getY(),EPSILON);
		assertEquals(ship,world.getEntityAt(120, 180));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMoveNegativeTime() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI, 200);
		Bullet bullet1 = facade.createBullet(100, 200, 250, 0, 1);
		Bullet bullet2 = facade.createBullet(100, 200, 250, 0, 1);
		facade.addShipToWorld(world, ship);
		facade.loadBulletOnShip(ship, bullet1);
		facade.loadBulletOnShip(ship, bullet2);
		ship.thrustOn();
		ship.move(-2);
	}
	
	@Test
	public void testMoveAgainstWorld() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(900, 200, 10, -10, 20, Math.PI, 200);
		Bullet bullet1 = facade.createBullet(900, 200, 250, 0, 1);
		Bullet bullet2 = facade.createBullet(900, 200, 250, 0, 1);
		facade.addShipToWorld(world, ship);
		facade.loadBulletOnShip(ship, bullet1);
		facade.loadBulletOnShip(ship, bullet2);
		ship.thrustOn();
		ship.move(world.getTimeNextCollision());
		assertEquals(980, ship.getPos().getX(),EPSILON);
		assertEquals(120, ship.getPos().getY(),EPSILON);
		assertEquals(-174.6799,ship.getVel().getX(),EPSILON);
		assertEquals(-10,ship.getVel().getY(),EPSILON);
		assertEquals(ship,world.getEntityAt(980, 120));
		
	}
	
	@Test
	public void testTerminatesShip() throws ModelException {
		Ship ship = facade.createShip(0, 0, 0, 0, 10, 0, 100);
		Bullet bullet = facade.createBullet(0, 0, 0, 0, 1);
		facade.loadBulletOnShip(ship, bullet);
		facade.terminateShip(ship);
		assertEquals(0,facade.getNbBulletsOnShip(ship));
		assertTrue(facade.getBulletShip(bullet) == null);
		assertTrue(facade.isTerminatedShip(ship));
	}
	
	/**************
	 * BULLET: methods
	 *************/
	@Test
	public void testCreateBullet() throws ModelException {
		Bullet bullet = facade.createBullet(100, 200, 10, -10, 20);
		assertNotNull(bullet);
		double[] position = facade.getBulletPosition(bullet);
		double[] velocity = facade.getBulletVelocity(bullet);
		assertNotNull(position);
		assertEquals(100, position[0], EPSILON);
		assertEquals(200, position[1], EPSILON);
		assertEquals(10, velocity[0], EPSILON);
		assertEquals(-10, velocity[1], EPSILON);
		assertEquals(20, facade.getBulletRadius(bullet), EPSILON);
		assertEquals(2.613805e17,facade.getBulletMass(bullet),EPSILON*1e16);
	}
	
	@Test (expected = ModelException.class)
	public void testCreateBulletWrongRadius() throws ModelException {
		facade.createBullet(100, 200, 10, -10, 0.5);
	}

	@Test (expected = ModelException.class)
	public void testCreateBulletXIsNan() throws ModelException {
		facade.createBullet(Double.NaN, 200, 10, -10, 20);
	}
	
	@Test
	public void testCreateBulletYIsNegInf() throws ModelException {
		Bullet bullet = facade.createBullet(20, Double.NEGATIVE_INFINITY, 10, -10, 20);
		assertTrue(Double.isInfinite(facade.getBulletPosition(bullet)[1]));
	}
	
	@Test
	public void testCreateBulletXIsPosInf() throws ModelException {
		Bullet bullet = facade.createBullet(Double.POSITIVE_INFINITY, 200, 10, -10, 20);
		assertTrue(Double.isInfinite(facade.getBulletPosition(bullet)[0]));
	}
	
	@Test(expected = ModelException.class)
	public void testCreateBulletXIsPosInfInWorld() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Bullet bullet = facade.createBullet(Double.POSITIVE_INFINITY, 200, 10, -10, 20);
		facade.addBulletToWorld(world, bullet);
	}
	
	@Test
	public void testSetShip() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Bullet bullet =  facade.createBullet(100, 100, -20, 0, 1);
		Ship ship = facade.createShip(100, 100, 20, 0, 10, 0, 500);
		facade.addShipToWorld(world, ship);
		facade.loadBulletOnShip(ship, bullet);
		assertEquals(ship, bullet.getShip());
		assertTrue(world.getSpecificList(Bullet.class).isEmpty());
	}
	
	@Test
	public void testGetSource() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Bullet bullet =  facade.createBullet(100, 100, -20, 0, 1);
		Ship ship = facade.createShip(100, 100, 20, 0, 10, 0, 500);
		facade.addShipToWorld(world, ship);
		facade.loadBulletOnShip(ship, bullet);
		facade.fireBullet(ship);
		assertEquals(ship,bullet.getSource());
	}
	
	@Test
	public void testSetWorldBullet() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Bullet bullet =  facade.createBullet(200, 100, -20, 0, 1);
		bullet.setWorld(world);
		assertEquals(world,bullet.getWorld());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetWorldBulletOutOfBounds() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Bullet bullet =  facade.createBullet(2000, 100, -20, 0, 1);
		bullet.setWorld(world);
	}
	
	@Test (expected = ModelException.class)
	public void testSetWorldBulletOverlapShip() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Bullet bullet =  facade.createBullet(100, 100, -20, 0, 1);
		Ship ship = facade.createShip(100, 100, 20, 0, 10, 0, 500);
		facade.addShipToWorld(world, ship);
		facade.addBulletToWorld(world, bullet);
	}
	
	@Test
	public void testMoveBullet() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Bullet bullet =  facade.createBullet(100, 100, 20, 10, 1);
		facade.addBulletToWorld(world, bullet);
		bullet.move(1);
		assertEquals(120,bullet.getPos().getX(),EPSILON);
		assertEquals(110,bullet.getPos().getY(),EPSILON);
		assertEquals(bullet,world.getEntityAt(120, 110));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMoveBulletNegativeTime() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Bullet bullet =  facade.createBullet(100, 100, -20, 0, 1);
		facade.addBulletToWorld(world, bullet);
		bullet.move(-1);
	}
	
	@Test
	public void testTerminateBullet() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Bullet bullet =  facade.createBullet(100, 100, -20, 0, 1);
		facade.addBulletToWorld(world, bullet);
		bullet.terminate();
		assertTrue(bullet.isTerminated());
		assertTrue(world.getSpecificList(Bullet.class).isEmpty());
		assertTrue(world.getEntityList().isEmpty());
	}
	
	/**************
	 * ASTEROID: methods
	 *************/
	
	@Test
	public void testCreateAsteroid() throws ModelException {
		Asteroid asteroid = facade.createAsteroid(100, 200, 10, -10, 20);
		assertNotNull(asteroid);
		double[] position = facade.getAsteroidPosition(asteroid);
		double[] velocity = facade.getAsteroidVelocity(asteroid);
		assertNotNull(position);
		assertEquals(100, position[0], EPSILON);
		assertEquals(200, position[1], EPSILON);
		assertEquals(10, velocity[0], EPSILON);
		assertEquals(-10, velocity[1], EPSILON);
		assertEquals(20, facade.getAsteroidRadius(asteroid), EPSILON);
		assertEquals(8.880235e16,facade.getAsteroidMass(asteroid),EPSILON*1e16);
	}
	
	@Test (expected = ModelException.class)
	public void testCreateAsteroidWrongRadius() throws ModelException {
		facade.createAsteroid(100, 200, 10, -10, 4);
	}

	@Test (expected = ModelException.class)
	public void testCreateAsteroidXIsNan() throws ModelException {
		facade.createAsteroid(Double.NaN, 200, 10, -10, 20);
	}
	
	@Test
	public void testCreateAsteroidYIsNegInf() throws ModelException {
		Asteroid asteroid = facade.createAsteroid(20, Double.NEGATIVE_INFINITY, 10, -10, 20);
		assertTrue(Double.isInfinite(facade.getAsteroidPosition(asteroid)[1]));
	}
	
	@Test
	public void testCreateAsteroidXIsPosInf() throws ModelException {
		Asteroid asteroid = facade.createAsteroid(Double.POSITIVE_INFINITY, 200, 10, -10, 20);
		assertTrue(Double.isInfinite(facade.getAsteroidPosition(asteroid)[0]));
	}
	
	@Test(expected = ModelException.class)
	public void testCreateAsteroidXIsPosInfInWorld() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Asteroid asteroid = facade.createAsteroid(Double.POSITIVE_INFINITY, 200, 10, -10, 20);
		facade.addAsteroidToWorld(world, asteroid);
	}
	
	@Test
	public void testTerminateAsteroid() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Asteroid asteroid = facade.createAsteroid(100, 100, -20, 0, 10);
		facade.addAsteroidToWorld(world, asteroid);
		asteroid.terminate();
		assertTrue(asteroid.isTerminated());
		assertTrue(world.getSpecificList(Asteroid.class).isEmpty());
		assertTrue(world.getEntityList().isEmpty());
	}
	
	/**************
	 * PLANETOID: methods
	 *************/
	
	@Test
	public void testCreatePlanetoid() throws ModelException {
		Planetoid planetoid = facade.createPlanetoid(100, 200, 10, -10, 20, 0);
		assertNotNull(planetoid);
		double[] position = facade.getPlanetoidPosition(planetoid);
		double[] velocity = facade.getPlanetoidVelocity(planetoid);
		assertNotNull(position);
		assertEquals(100, position[0], EPSILON);
		assertEquals(200, position[1], EPSILON);
		assertEquals(10, velocity[0], EPSILON);
		assertEquals(-10, velocity[1], EPSILON);
		assertEquals(20, facade.getPlanetoidRadius(planetoid), EPSILON);
		assertEquals(3.0728964e16,facade.getPlanetoidMass(planetoid),EPSILON*1e16);
	}
	
	@Test 
	public void testCreatePlanetoidWrongRadius() throws ModelException {
		Planetoid planetoid = facade.createPlanetoid(100, 200, 10, -10, 2, 0);
		assertTrue(planetoid.isTerminated());
		
	}

	@Test
	public void testTravelDistance() throws ModelException {
		Planetoid planetoid = facade.createPlanetoid(100, 200, 10, -10, 20, 500000);
		assertNotNull(planetoid);
		assertEquals(19.5, facade.getPlanetoidRadius(planetoid), EPSILON);
		assertEquals(2.8481429e16,facade.getPlanetoidMass(planetoid),EPSILON*1e16);
	}
	
	@Test (expected = ModelException.class)
	public void testCreatePlanetoidXIsNan() throws ModelException {
		facade.createPlanetoid(Double.NaN, 200, 10, -10, 20, 0);
	}
	
	@Test
	public void testCreatePlanetoidYIsNegInf() throws ModelException {
		Planetoid planetoid = facade.createPlanetoid(100, Double.NEGATIVE_INFINITY, 10, -10, 20, 0);		
		assertTrue(Double.isInfinite(facade.getPlanetoidPosition(planetoid)[1]));
	}
	
	@Test
	public void testCreatePlanetoidXIsPosInf() throws ModelException {
		Planetoid planetoid = facade.createPlanetoid(Double.POSITIVE_INFINITY, 200, 10, -10, 20, 0);		
		assertTrue(Double.isInfinite(facade.getPlanetoidPosition(planetoid)[0]));
	}
	
	@Test(expected = ModelException.class)
	public void testCreatePlanetoidXIsPosInfInWorld() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Planetoid planetoid = facade.createPlanetoid(Double.POSITIVE_INFINITY, 200, 10, -10, 20, 0);
		facade.addPlanetoidToWorld(world, planetoid);
	}
	
	@Test
	public void testMovePlanetoid() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Planetoid planetoid = facade.createPlanetoid(100, 200, 60, 80, 6.1, 500000);
		facade.addPlanetoidToWorld(world, planetoid);
		planetoid.move(5);
		assertEquals(400, planetoid.getPos().getX(),EPSILON);
		assertEquals(600, planetoid.getPos().getY(),EPSILON);
		assertEquals(60,planetoid.getVel().getX(),EPSILON);
		assertEquals(80,planetoid.getVel().getY(),EPSILON);
		assertEquals(planetoid,world.getEntityAt(400, 600));
		assertEquals(5.0995, planetoid.getRadius(),EPSILON);
	}
	@Test
	public void testMovePlanetoidTerminate() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Planetoid planetoid = facade.createPlanetoid(100, 200, 60, 80, 5.6, 500000);
		assertEquals(5.1, planetoid.getRadius(),EPSILON);
		facade.addPlanetoidToWorld(world, planetoid);
		planetoid.move(5);
		assertTrue(planetoid.isTerminated());
		assertTrue(world.getSpecificList(Planetoid.class).isEmpty());
		assertTrue(world.getEntityList().isEmpty());
	}
	
	@Test
	public void testTeleportShip() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(160, 200, -10, 0, 10, 0, 500);
		Planetoid planetoid = facade.createPlanetoid(100, 200, 60, 80, 5.6, 0);
		facade.addShipToWorld(world, ship);
		planetoid.teleport(ship);
		assertEquals(1, world.getEntityList().size(),EPSILON);
		assertEquals(1, world.getSpecificList(Ship.class).size(),EPSILON);
		assertTrue(! ship.isTerminated());
		
	}
	
	@Test
	public void testTerminatePlanetoid() throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Planetoid planetoid = facade.createPlanetoid(100, 100, -20, 0, 10, 10);
		facade.addPlanetoidToWorld(world, planetoid);
		planetoid.terminate();
		assertTrue(planetoid.isTerminated());
		assertTrue(world.getSpecificList(Planetoid.class).isEmpty());
		assertTrue(world.getEntityList().isEmpty());
	}
	
	@Test
	public void testTerminatePlanetoidWithSplit() throws ModelException  {
		World world = facade.createWorld(1000, 1000);
		Planetoid planetoid = facade.createPlanetoid(100, 100, -20, 0, 40, 0);
		facade.addPlanetoidToWorld(world, planetoid);
		facade.terminatePlanetoid(planetoid);
		assertTrue(world.getSpecificList(Planetoid.class).isEmpty());
		assertTrue(planetoid.isTerminated());
		assertTrue(planetoid.getWorld() == null);
		Set<? extends Asteroid> asteroids = facade.getWorldAsteroids(world);
		Asteroid asteroid1 = asteroids.iterator().next();
		Asteroid asteroid2 = asteroids.iterator().next();
		assertEquals(planetoid.getTotalSpeed()*1.5,asteroid1.getTotalSpeed(),EPSILON);
		assertEquals(planetoid.getTotalSpeed()*1.5,asteroid2.getTotalSpeed(),EPSILON);
		assertEquals(planetoid.getRadius()/2, asteroid1.getRadius(),EPSILON);
		assertEquals(planetoid.getRadius()/2,asteroid2.getRadius(),EPSILON);
		assertEquals(2, world.getSpecificList(Asteroid.class).size(),EPSILON);

	}
	
	/**************
	 * METHODS RELATED TO FIRING BULLETS 
	 *************/
	
	@Test
	public void testFireBullet() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Bullet bullet =  facade.createBullet(100, 100, -20, 0, 1);
		Ship ship = facade.createShip(100, 100, 20, 0, 10, 0, 500);
		facade.addShipToWorld(world, ship);
		facade.loadBulletOnShip(ship, bullet);
		facade.fireBullet(ship);
		assertTrue(facade.getBulletSource(bullet).equals(ship));
		assertEquals(250, facade.getBulletVelocity(bullet)[0], EPSILON);
		assertEquals(111, facade.getBulletPosition(bullet)[0], EPSILON);
		assertEquals(100, facade.getBulletPosition(bullet)[1], EPSILON);
		facade.evolve(world, 1, null);
		assertEquals(361, facade.getBulletPosition(bullet)[0], EPSILON);
		assertEquals(100, facade.getBulletPosition(bullet)[1], EPSILON);
	}
	
	@Test
	public void testFireMultipleBullets() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Bullet bullet1 =  facade.createBullet(100, 100, -20, 0, 1);
		Bullet bullet2 =  facade.createBullet(100, 100, -20, 0, 1);
		Ship ship = facade.createShip(100, 100, 0, 0, 10, 0, 500);
		facade.addShipToWorld(world, ship);
		facade.loadBulletOnShip(ship, bullet1);
		facade.fireBullet(ship);
		assertEquals(111, facade.getBulletPosition(bullet1)[0], EPSILON);
		assertEquals(100, facade.getBulletPosition(bullet1)[1], EPSILON);
		facade.evolve(world, 1, null);
		assertEquals(361, facade.getBulletPosition(bullet1)[0], EPSILON);
		assertEquals(100, facade.getBulletPosition(bullet1)[1], EPSILON);
		facade.loadBulletOnShip(ship, bullet2);
		facade.fireBullet(ship);
		assertEquals(111, facade.getBulletPosition(bullet2)[0], EPSILON);
		assertEquals(100, facade.getBulletPosition(bullet2)[1], EPSILON);
		assertTrue(! facade.isTerminatedBullet(bullet1));
		assertTrue(! facade.isTerminatedBullet(bullet2));
		assertEquals(3, world.getEntityList().size());
		assertTrue(bullet1.getSource().equals(ship));
		assertTrue(bullet2.getSource().equals(ship));
		facade.evolve(world, 2, null);
		assertEquals(861, facade.getBulletPosition(bullet1)[0], EPSILON);
		assertEquals(100, facade.getBulletPosition(bullet1)[1], EPSILON);
		assertEquals(611, facade.getBulletPosition(bullet2)[0], EPSILON);
		assertEquals(100, facade.getBulletPosition(bullet2)[1], EPSILON);
	}
	

	/**************
	 * METHODS RELATED TO COLLISIONS 
	 *************/
	@Test
	public void testShipCollideWithBoundary() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(500, 985, 0, 1, 10, 0, 500);
		facade.evolve(world, 10, null);
		assertEquals(985, facade.getShipPosition(ship)[1], EPSILON);
		assertEquals(500, facade.getShipPosition(ship)[0], EPSILON);
	}
	
	@Test
	public void testCollideShipsX() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(100, 100, 1, 0, 10, 0, 500);
		Ship ship2 = facade.createShip(200, 100, -1, 0, 10, 0, 500);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		facade.evolve(world, 80, null);
		assertEquals(100, facade.getShipPosition(ship1)[0], EPSILON);
		assertEquals(100, facade.getShipPosition(ship1)[1], EPSILON);
		assertEquals(200, facade.getShipPosition(ship2)[0], EPSILON);
		assertEquals(100, facade.getShipPosition(ship2)[1], EPSILON);
	}
	
	@Test
	public void testCollideShipsY() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(100, 100, 0, 1, 10, 0, 500);
		Ship ship2 = facade.createShip(100, 200, 0, -1, 10, 0, 500);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		facade.evolve(world, 80, null);
		assertEquals(100, facade.getShipPosition(ship1)[0], EPSILON);
		assertEquals(100, facade.getShipPosition(ship1)[1], EPSILON);
		assertEquals(100, facade.getShipPosition(ship2)[0], EPSILON);
		assertEquals(200, facade.getShipPosition(ship2)[1], EPSILON);
	}
	
	@Test
	public void testCollideShipsDiagonal() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(100, 100, 1, 1, 10, 0, 500);
		Ship ship2 = facade.createShip(200, 200, -1, -1, 10, 0, 500);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		facade.evolve(world, 85.85786438, null);
		assertEquals(100, facade.getShipPosition(ship1)[0], EPSILON);
		assertEquals(100, facade.getShipPosition(ship1)[1], EPSILON);
		assertEquals(200, facade.getShipPosition(ship2)[0], EPSILON);
		assertEquals(200, facade.getShipPosition(ship2)[1], EPSILON);
	}
	
	@Test
	public void testBulletCollideShip() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Bullet bullet =  facade.createBullet(200, 100, -20, 0, 1);
		Ship ship = facade.createShip(100, 100, 20, 0, 10, 0, 500);
		facade.addShipToWorld(world, ship);
		facade.addBulletToWorld(world, bullet);
		facade.evolve(world, 100, null);
		assertTrue(facade.isTerminatedBullet(bullet));
		assertTrue(facade.isTerminatedShip(ship));
		assertTrue(facade.getWorldBullets(world).isEmpty());
		assertTrue(facade.getWorldShips(world).isEmpty());
	}
	@Test
	public void testBulletCollideBoundary() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Bullet bullet =  facade.createBullet(500, 500, 250, 0, 1);
		facade.addBulletToWorld(world, bullet);
		facade.evolve(world, 4, null);
		assertEquals(-250, facade.getBulletVelocity(bullet)[0], EPSILON);
		facade.evolve(world, 5, null);
		assertEquals(250, facade.getBulletVelocity(bullet)[0], EPSILON);
		facade.evolve(world, 15, null);
		assertTrue(facade.isTerminatedBullet(bullet));
	}
	
	@Test
	public void testBulletCollideSource() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Bullet bullet =  facade.createBullet(100, 100, -20, 0, 1);
		Ship ship = facade.createShip(100, 100, 20, 0, 10, 0, 500);
		facade.addShipToWorld(world, ship);
		facade.loadBulletOnShip(ship, bullet);
		facade.fireBullet(ship);
		facade.evolve(world, 20, null);
		assertTrue(facade.getWorldBullets(world).isEmpty());
		assertTrue(facade.getBulletShip(bullet).equals(ship));
		assertTrue(facade.getBulletsOnShip(ship).contains(bullet));
	}
	
	@Test
	public void testAlmostCollision() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship1 = facade.createShip(100, 80, 10, 0, 10, 0, 5000);
		Ship ship2 = facade.createShip(200, 100, -10, 0, 10, 0, 5000);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		facade.evolve(world, 10, null);
		assertEquals(200, facade.getShipPosition(ship1)[0], EPSILON);
		assertEquals(80, facade.getShipPosition(ship1)[1], EPSILON);
		assertEquals(100, facade.getShipPosition(ship2)[0], EPSILON);
		assertEquals(100, facade.getShipPosition(ship2)[1], EPSILON);
		
	}
	
	@Test
	public void testOverlapOrCollide() throws ModelException {
		World world = facade.createWorld(1000, 1000);
		Ship ship1 = facade.createShip(100, 80, 0, 0, 10, 0, 5000);
		Ship ship2 = facade.createShip(100, 100, 0, 0, 10, 0, 5000);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		assertTrue(ship1.apparentlyCollides(ship2));
		assertTrue(! ship1.overlap(ship2));
		ship2.setYPosition(100.2);
		assertTrue(! ship1.apparentlyCollides(ship2));
		assertTrue(! ship1.overlap(ship2));
		ship2.setYPosition(99.8);
		assertTrue(! ship1.apparentlyCollides(ship2));
		assertTrue(ship1.overlap(ship2));
		ship2.setYPosition(99.9);
		assertTrue(ship1.apparentlyCollides(ship2));
		assertTrue(! ship1.overlap(ship2));
		
	}
	
	@Test
	public void testApparentlyCollideXWorld()throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(50, 80, 0, 0, 10, 0, 5000);
		facade.addShipToWorld(world, ship);
		ship.setXPosition(10.1);
		assertTrue(! ship.apparentlyCollides(world));
		ship.setXPosition(10);
		assertTrue( ship.apparentlyCollides(world));
		ship.setXPosition(9.9);
		// Ship is in fact out of the boundaries. However, according to the formula, it doesn't collide.
		assertTrue(! ship.apparentlyCollides(world));
		ship.setXPosition(989.9);
		assertTrue(! ship.apparentlyCollides(world));
		ship.setXPosition(990);
		assertTrue( ship.apparentlyCollides(world));
		ship.setXPosition(990.1);
		assertTrue(! ship.apparentlyCollides(world));
	}
	
	@Test
	public void testApparentlyCollideYWorld()throws ModelException{
		World world = facade.createWorld(1000, 1000);
		Ship ship = facade.createShip(50, 80, 0, 0, 10, 0, 5000);
		facade.addShipToWorld(world, ship);
		ship.setYPosition(10.1);
		assertTrue(! ship.apparentlyCollides(world));
		ship.setYPosition(10);
		assertTrue( ship.apparentlyCollides(world));
		ship.setYPosition(9.9);
		// Here the ship is at an invalid position, but should still not collide according to the method.
		assertTrue(! ship.apparentlyCollides(world));
		ship.setYPosition(989.9);
		assertTrue(! ship.apparentlyCollides(world));
		ship.setYPosition(990);
		assertTrue( ship.apparentlyCollides(world));
		ship.setYPosition(990.1);
		assertTrue(! ship.apparentlyCollides(world));
	}
	
	@Test
	public void testCollisionShips() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(200,100,100,0,100,0,500);
		Ship ship2 = facade.createShip(4800,100,-100,0,100,0,500);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		assertEquals(22, facade.getTimeCollisionEntity(ship1, ship2), EPSILON);
		assertEquals(2500, facade.getCollisionPosition(ship1, ship2)[0], EPSILON);
		assertEquals(100, facade.getCollisionPosition(ship1, ship2)[1], EPSILON);
	}
	
	@Test
	public void testGetCollisionBoundary() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Ship ship = facade.createShip(2500, 2500, -10, 0, 100, 0, 500);
		facade.addShipToWorld(world, ship);
		assertEquals(240, facade.getTimeNextCollision(world), EPSILON);
		assertEquals(0, facade.getPositionNextCollision(world)[0], EPSILON);
		assertEquals(2500, facade.getPositionNextCollision(world)[1], EPSILON);
	}
	
	@Test
	public void testMultipleCollisions() throws ModelException{
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(2000, 2500, 10, 0, 100, 0, 500);
		Ship ship2 = facade.createShip(2500, 2500, -10, 0, 100, 0, 500);
		Ship ship3 = facade.createShip(2500, 1500, -10, 0, 100, 0, 500);
		Ship ship4 = facade.createShip(2000, 1500, 10, 0, 100, 0, 500);
		Ship ship5 = facade.createShip(250, 1500, -10, 0, 100, 0, 500);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		facade.addShipToWorld(world, ship3);
		facade.addShipToWorld(world, ship4);
		facade.addShipToWorld(world, ship5);
		facade.evolve(world, 16, null);
	}

	// Because of this test, we know our program doesn't work for collisions between multiple entities.
	// Unfortunately we didn't find a solution, but some research showed that it is actually a very difficult problem.
	@Test
	public void testMultipleCollisionsAgainstOneShip() throws ModelException{
		World world = facade.createWorld(5000, 5000);
		Ship ship1 = facade.createShip(2000, 2500, 0, 0, 100, 0, 500);
		Ship ship2 = facade.createShip(2500, 2500, -10, 0, 100, 0, 500);
		Ship ship3 = facade.createShip(1500, 2500, 10, 0, 100, 0, 500);
		facade.addShipToWorld(world, ship1);
		facade.addShipToWorld(world, ship2);
		facade.addShipToWorld(world, ship3);
		facade.evolve(world, 31, null);
		assertEquals(0,ship1.getVel().getX(),EPSILON);
		assertEquals(10,ship2.getVel().getX(),EPSILON);
		assertEquals(-10,ship3.getVel().getX(),EPSILON);
		assertEquals(2000,ship1.getPos().getX(),EPSILON);
		assertEquals(2210,ship2.getPos().getX(),EPSILON);
		assertEquals(1790,ship3.getPos().getX(),EPSILON);
		assertTrue(! ship1.overlap(ship3));
	}
	
	@Test
	public void testCollisionMinorPlanet() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Asteroid asteroid1 = facade.createAsteroid(100, 100, 10, 0, 10);
		Asteroid asteroid2 = facade.createAsteroid(160, 100, -10, 0, 10);
		facade.addAsteroidToWorld(world, asteroid1);
		facade.addAsteroidToWorld(world, asteroid2);
		world.evolve(2, null);
		assertEquals(-10,asteroid1.getVel().getX(),EPSILON);
		assertEquals(10,asteroid2.getVel().getX(),EPSILON);
		world.evolve(1, null);
		assertEquals(110,asteroid1.getPos().getX(),EPSILON);
		assertEquals(150,asteroid2.getPos().getX(),EPSILON);
	}

	@Test
	public void testCollisionAsteroidAndShip() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Asteroid asteroid = facade.createAsteroid(100, 100, 10, 0, 10);
		Ship ship = facade.createShip(160, 100, -10, 0, 10, 0, 500);
		facade.addAsteroidToWorld(world, asteroid);
		facade.addShipToWorld(world, ship);
		world.evolve(2, null);
		assertEquals(1, world.getEntityList().size(),EPSILON);
		assertTrue(world.getSpecificList(Ship.class).isEmpty());
		assertTrue(ship.getWorld() == null);
		assertTrue(ship.isTerminated());
	}
	
	@Test
	public void testCollisionAsteroidAndBullet() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Asteroid asteroid = facade.createAsteroid(100, 100, 10, 0, 10);
		Bullet bullet = facade.createBullet(160, 100, -10, 0, 10);
		facade.addAsteroidToWorld(world, asteroid);
		facade.addBulletToWorld(world, bullet);
		world.evolve(2, null);
		assertEquals(0, world.getEntityList().size(),EPSILON);
		assertTrue(world.getSpecificList(Asteroid.class).isEmpty());
		assertTrue(world.getSpecificList(Bullet.class).isEmpty());
		assertTrue(bullet.getWorld() == null);
		assertTrue(asteroid.getWorld() == null);
		assertTrue(bullet.isTerminated());
		assertTrue(asteroid.isTerminated());
	}
	
	@Test
	public void testCollisionPlanetoidAndBullet() throws ModelException {
		World world = facade.createWorld(5000, 5000);
		Planetoid planetoid = facade.createPlanetoid(100, 100, 0, 0, 10, 0);
		Bullet bullet = facade.createBullet(160, 100, -10, 0, 10);
		facade.addPlanetoidToWorld(world, planetoid);
		facade.addBulletToWorld(world, bullet);
		world.evolve(4, null);
		assertEquals(0, world.getEntityList().size(),EPSILON);
		assertTrue(world.getSpecificList(Asteroid.class).isEmpty());
		assertTrue(world.getSpecificList(Bullet.class).isEmpty());
		assertTrue(bullet.getWorld() == null);
		assertTrue(planetoid.getWorld() == null);
		assertTrue(bullet.isTerminated());
		assertTrue(planetoid.isTerminated());
	}
	
}
