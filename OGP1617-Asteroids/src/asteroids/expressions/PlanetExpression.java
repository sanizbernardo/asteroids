package asteroids.expressions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import asteroids.model.Entity;
import asteroids.model.MinorPlanet;
import asteroids.part3.programs.SourceLocation;

public class PlanetExpression<E> extends Expression<MinorPlanet> implements EntityExpression<MinorPlanet> {

	public PlanetExpression(SourceLocation location) {
		this.setLocation(location);
	}
	
	public MinorPlanet getEntity() {
		Set<Entity> entities = this.getProgram().getShip().getWorld().getEntityList();
		List<Entity> planets = entities.stream()
									   .filter(e -> e instanceof MinorPlanet)
									   .collect(Collectors.toList());
		
		if (planets.size() == 0)
			return null;
		
		Collections.sort(planets, new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				double d1 = getProgram().getShip().getDistanceBetween((Entity)e1);
				double d2 = getProgram().getShip().getDistanceBetween((Entity)e2);
				
				int v = Double.compare(d1, d2);
				return v;
			}
		});
		
		MinorPlanet closest = (MinorPlanet)planets.iterator().next();
		return closest;
		
		
	}
	
	@Override
	public MinorPlanet evaluate() {
		return this.getEntity();
	}

	@Override
	public void setSubProgram() {
	}

	@Override
	public void setSubFunction() {
	}
	
}