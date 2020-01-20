package asteroids.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Ship;
import asteroids.facade.Facade;
import asteroids.part1.facade.IFacade;
import asteroids.util.ModelException;

public class Part1TestPartial {
	
	private static final double EPSILON = 0.0001;

	IFacade facade;

	@Before
	public void setUp() {
		facade = new Facade();
	}

	@Test
	public void testCreateShip() throws ModelException {
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI);
		assertNotNull(ship);
		double[] position = facade.getShipPosition(ship);
		assertNotNull(position);
		assertEquals(100, position[0], EPSILON);
		assertEquals(200, position[1], EPSILON);
		assertEquals(20, facade.getShipRadius(ship), EPSILON);
	}
	
	@Test
	public void testCreateEmptyShip() throws ModelException {
		Ship ship = facade.createShip();
		assertNotNull(ship);
		double[] position = facade.getShipPosition(ship);
		assertNotNull(position);
		assertEquals(0, position[0], EPSILON);
		assertEquals(0, position[1], EPSILON);
		assertEquals(10, facade.getShipRadius(ship), EPSILON);
		double[] speed = facade.getShipVelocity(ship);
		assertEquals(0, speed[0], EPSILON);
		assertEquals(0, speed[1], EPSILON);
		assertEquals(0, facade.getShipOrientation(ship), EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testCreateShipXIsNan() throws ModelException {
		facade.createShip(Double.NaN, 200, 10, -10, 20, -Math.PI);
	}
	
	@Test(expected = ModelException.class)
	public void testCreateShipXIsNegInf() throws ModelException {
		facade.createShip(Double.NEGATIVE_INFINITY, 200, 10, -10, 20, -Math.PI);
	}
	
	@Test(expected = ModelException.class)
	public void testCreateShipXIsPosInf() throws ModelException {
		facade.createShip(Double.POSITIVE_INFINITY, 200, 10, -10, 20, -Math.PI);
	}
	
	@Test
	public void testCreateShipSpeedLarge() throws ModelException {
		Ship ship = facade.createShip(100, 200, 100000000, 100000000, 20, Math.PI);
		double[] speed = facade.getShipVelocity(ship);
		assertEquals(300000, Math.sqrt(Math.pow(speed[0],2) + Math.pow(speed[1],2)), EPSILON);
	}
	
	@Test
	public void testCreateShipSpeed2neg() throws ModelException {
		Ship ship = facade.createShip(100, 200, -20 , -30, 20, Math.PI);
		double[] speed = facade.getShipVelocity(ship);
		assertEquals(36.0555, Math.sqrt(Math.pow(speed[0],2) + Math.pow(speed[1],2)), EPSILON);
	}
	
	@Test
	public void testCreateShipSpeed1neg() throws ModelException {
		Ship ship = facade.createShip(100, 200, -20 , 30, 20, Math.PI);
		double[] speed = facade.getShipVelocity(ship);
		assertNotNull(speed);
		assertEquals(36.0555, Math.sqrt(Math.pow(speed[0],2) + Math.pow(speed[1],2)), EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testCreateShipRadiusNegative() throws ModelException {
		facade.createShip(100, 200, 10, -10, -20, -Math.PI);
	}
	
	@Test(expected = AssertionError.class)
	public void testOrientation() throws ModelException {
		facade.createShip(100, 100, 30, -15, 20, 10);
	}

	@Test
	public void testMove() throws ModelException {
		Ship ship = facade.createShip(100, 100, 30, -15, 20, 0);
		facade.move(ship, 1);
		double[] position = facade.getShipPosition(ship);
		assertNotNull(position);
		assertEquals(130, position[0], EPSILON);
		assertEquals(85, position[1], EPSILON);
	}
	
	@Test(expected = ModelException.class)
	public void testMoveNegativeTime() throws ModelException {
		Ship ship = facade.createShip(100, 100, 30, -15, 20, 0);
		facade.move(ship, -1);
	}
	
	@Test
	public void testMoveZero() throws ModelException {
		Ship ship = facade.createShip(100, 100, 30, -15, 20, 0);
		facade.move(ship, 0);
		double[] position = facade.getShipPosition(ship);
		assertNotNull(position);
		assertEquals(100, position[0], EPSILON);
		assertEquals(100, position[1], EPSILON);
	}
	
	@Test
	public void testTurn() throws ModelException {
		Ship ship = facade.createShip(100, 100, 30, -15, 20, 0);
		facade.turn(ship, 0.5);
		double angle = facade.getShipOrientation(ship);
		assertEquals(0.5, angle, EPSILON);
	}
	
	@Test
	public void testFullTurn() throws ModelException {
		Ship ship = facade.createShip(100, 100, 30, -15, 20, 2*Math.PI);
		facade.turn(ship, 0.5);
		double angle = facade.getShipOrientation(ship);
		assertEquals(0.5, angle, EPSILON);
	}
	
	@Test
	public void testFullTurn2() throws ModelException {
		Ship ship = facade.createShip(100, 100, 30, -15, 20, 0);
		facade.turn(ship, -1*Math.PI);
		double angle = facade.getShipOrientation(ship);
		assertEquals(Math.PI, angle, EPSILON);
	}
	
	@Test
	public void testThrust1() throws ModelException {
		Ship ship = facade.createShip(100, 200, 30, -15, 20, 0);
		facade.thrust(ship, 2);
		double[] speed = facade.getShipVelocity(ship);
		assertNotNull(speed);
		assertEquals(32, speed[0], EPSILON);
		assertEquals(-15, speed[1], EPSILON);
	}
	
	@Test
	public void testThrust2() throws ModelException {
		Ship ship = facade.createShip(100, 200, 30, -15, 20, Math.PI/4);
		facade.thrust(ship, 2);
		double[] speed = facade.getShipVelocity(ship);
		assertNotNull(speed);
		assertEquals(31.4142, speed[0], EPSILON);
		assertEquals(-13.5857, speed[1], EPSILON);
	}
	
	@Test
	public void testThrustNeg() throws ModelException {
		Ship ship = facade.createShip(100, 200, 30, -15, 20, Math.PI/4);
		facade.thrust(ship, -3);
		double[] speed = facade.getShipVelocity(ship);
		assertNotNull(speed);
		assertEquals(30, speed[0], EPSILON);
		assertEquals(-15, speed[1], EPSILON);
	}
	
	@Test
	public void testDistanceZero() throws ModelException {
		Ship ship1 = facade.createShip(100, 200, 3, 5, 10, 2);
		Ship ship2 = facade.createShip(130, 200, 3, 5, 20, 2);
		double dist = facade.getDistanceBetween(ship1, ship2);
		assertEquals(0, dist, EPSILON);
		boolean overlap = facade.overlap(ship1, ship2);
		assertEquals(false, overlap);
	}
	
	@Test
	public void testDistanceNegative() throws ModelException {
		Ship ship1 = facade.createShip(100, 200, 3, 5, 10, 2);
		Ship ship2 = facade.createShip(120, 200, 3, 5, 20, 2);
		double dist = facade.getDistanceBetween(ship1, ship2);
		assertEquals(-10, dist, EPSILON);
		boolean overlap = facade.overlap(ship1, ship2);
		assertEquals(true, overlap);
	}
	
	@Test
	public void testDistance() throws ModelException {
		Ship ship1 = facade.createShip(100, 200, 3, 5, 10, 2);
		Ship ship2 = facade.createShip(100, 260, 3, 5, 20, 2);
		double dist = facade.getDistanceBetween(ship1, ship2);
		assertEquals(30, dist, EPSILON);
		boolean overlap = facade.overlap(ship1, ship2);
		assertEquals(false, overlap);
	}
	
	@Test
	public void testOverlapSelf() throws ModelException {
		Ship ship1 = facade.createShip(100, 200, 3, 5, 10, 2);
		boolean overlap = facade.overlap(ship1, ship1);
		assertEquals(true, overlap);
	}
	
	@Test
	public void testTimeToCollision() throws ModelException {
		Ship ship1 = facade.createShip(100, 100, 3, 5, 10, 2);
		Ship ship2 = facade.createShip(100, 200, 3, -5, 20, 2);
		double time = facade.getTimeToCollision(ship1, ship2);
		assertEquals(7, time, EPSILON);
	}
	
	@Test
	public void testTimeToCollisionInf() throws ModelException {
		Ship ship1 = facade.createShip(100, 100, 3, 5, 10, 2);
		Ship ship2 = facade.createShip(100, 200, -3, -5, 20, 2);
		double time = facade.getTimeToCollision(ship1, ship2);
		assertEquals(Double.POSITIVE_INFINITY, time, EPSILON);
	}
	
	@Test(expected = ModelException.class)
	public void testTimeToCollisionOverlap() throws ModelException {
		Ship ship1 = facade.createShip(100, 100, 3, 5, 10, 2);
		Ship ship2 = facade.createShip(105, 105, -3, -5, 20, 2);
		facade.getTimeToCollision(ship1, ship2);
	}
	
	@Test
	public void testCollisionPosition() throws ModelException {
		Ship ship1 = facade.createShip(100, 100, 3, 5, 10, 2);
		Ship ship2 = facade.createShip(100, 200, 3, -5, 20, 2);
		double[] pos = facade.getCollisionPosition(ship1, ship2);
		assertEquals(121, pos[0], EPSILON);
		assertEquals(145, pos[1], EPSILON);
	}
	
	@Test
	public void testCollisionPositionNull() throws ModelException {
		Ship ship1 = facade.createShip(100, 100, 3, 5, 10, 2);
		Ship ship2 = facade.createShip(100, 200, -3, -5, 20, 2);
		double[] pos = facade.getCollisionPosition(ship1, ship2);
		assertEquals(null, pos);
	}
	
	@Test(expected = ModelException.class)
	public void testCollisionPositionOverlap() throws ModelException {
		Ship ship1 = facade.createShip(100, 100, 3, 5, 10, 2);
		Ship ship2 = facade.createShip(105, 105, -3, -5, 20, 2);
		facade.getCollisionPosition(ship1, ship2);
	}
}
