package asteroids.expressions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import asteroids.model.Planetoid;
import asteroids.model.Entity;
import asteroids.part3.programs.SourceLocation;

public class PlanetoidExpression<E> extends Expression<Planetoid> implements EntityExpression<Planetoid> {
	
	public PlanetoidExpression(SourceLocation location) {
		this.setLocation(location);
	}
	
	public Planetoid getEntity() {
		Set<Entity> entities = this.getProgram().getShip().getWorld().getEntityList();
		List<Entity> planetoids = entities.stream()
										  .filter(e -> e instanceof Planetoid)
										  .collect(Collectors.toList());
		
		if (planetoids.size() == 0)
			return null;
		
		Collections.sort(planetoids, new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				double d1 = getProgram().getShip().getDistanceBetween((Entity)e1);
				double d2 = getProgram().getShip().getDistanceBetween((Entity)e2);
				
				int v = Double.compare(d1, d2);
				return v;
			}
		});
		
		Planetoid closest = (Planetoid)planetoids.iterator().next();
		return closest;
		
		
	}
	
	@Override
	public Planetoid evaluate() {
		return this.getEntity();
	}

	@Override
	public void setSubProgram() {
	}

	@Override
	public void setSubFunction() {
	}
	
}