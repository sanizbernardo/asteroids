package asteroids.expressions;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

import asteroids.model.Entity;
import asteroids.model.Ship;
import asteroids.part3.programs.SourceLocation;

public class ShipExpression<E> extends Expression<Ship> implements EntityExpression<Ship> {
	
	public ShipExpression(SourceLocation location) {
		this.setLocation(location);
	}
	
	public Ship getEntity() {
		Set<Entity> entities = this.getProgram().getShip().getWorld().getEntityList();
		List<Entity> ships = entities.stream()
									 .filter(e -> e instanceof Ship)
									 .filter(e -> ! e.equals(getProgram().getShip()))
									 .collect(Collectors.toList());
		
		if (ships.size() == 0)
			return null;
		
		Collections.sort(ships, new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				double d1 = getProgram().getShip().getDistanceBetween((Entity)e1);
				double d2 = getProgram().getShip().getDistanceBetween((Entity)e2);
				
				int v = Double.compare(d1, d2);
				return v;
			}
		});
		
		Ship closest = (Ship)ships.iterator().next();
		return closest;
		
		
	}
	
	@Override
	public Ship evaluate() {
		return this.getEntity();
	}

	@Override
	public void setSubProgram() {
	}

	@Override
	public void setSubFunction() {
	}
	
}