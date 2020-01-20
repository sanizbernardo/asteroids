package asteroids.expressions;

import java.util.Set;
import java.util.stream.Collectors;

import asteroids.model.Entity;
import asteroids.part3.programs.SourceLocation;

public class AnyExpression<E> extends Expression<Entity> implements EntityExpression<Entity> {
	
	public AnyExpression(SourceLocation location) {
		this.setLocation(location);
		}


	@Override
	public Entity getEntity() {
		Set<Entity> entities = this.getProgram().getShip().getWorld().getEntityList();
		entities = entities.stream()
						   .collect(Collectors.toSet());
	
		if (entities.size() == 0)
			return null;
		Entity entity = entities.iterator().next();
		return entity;
	}
	
	
	@Override
	public Entity evaluate() {
		return this.getEntity();
	}


	@Override
	public void setSubProgram() {
	}


	@Override
	public void setSubFunction() {
	}
}