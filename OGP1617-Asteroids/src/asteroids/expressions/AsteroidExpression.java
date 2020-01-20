package asteroids.expressions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import asteroids.model.Asteroid;
import asteroids.model.Entity;
import asteroids.part3.programs.SourceLocation;

public class AsteroidExpression<E> extends Expression<Asteroid> implements EntityExpression<Asteroid> {
	
	public AsteroidExpression(SourceLocation location) {
		this.setLocation(location);
	}
	
	public Asteroid getEntity() {
		Set<Entity> entities = this.getProgram().getShip().getWorld().getEntityList();
		List<Entity> asteroids = entities.stream()
										 .filter(e -> e instanceof Asteroid)
										 .collect(Collectors.toList());
		
		if (asteroids.size() == 0)
			return null;
		
		Collections.sort(asteroids, new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				double d1 = getProgram().getShip().getDistanceBetween((Entity)e1);
				double d2 = getProgram().getShip().getDistanceBetween((Entity)e2);
				
				int v = Double.compare(d1, d2);
				return v;
			}
		});
		
		Asteroid closest = (Asteroid)asteroids.iterator().next();
		return closest;
	}
	
	@Override
	public Asteroid evaluate() {
		return this.getEntity();
	}

	@Override
	public void setSubProgram() {
	}

	@Override
	public void setSubFunction() {
	}
	
}