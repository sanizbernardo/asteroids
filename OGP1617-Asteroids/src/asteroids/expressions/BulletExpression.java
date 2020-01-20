package asteroids.expressions;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import asteroids.model.Bullet;
import asteroids.model.Entity;
import asteroids.part3.programs.SourceLocation;

public class BulletExpression<E> extends Expression<Bullet> implements EntityExpression<Bullet>{
	
	public BulletExpression(SourceLocation location) {
		this.setLocation(location);
	}
	
	
	@Override
	public Bullet getEntity() {
		Set<Entity> entities = this.getProgram().getShip().getWorld().getEntityList();
		List<Entity> bullets = entities.stream()
									   .filter(e -> e instanceof Bullet)
									   .filter(e -> ((Bullet)e).getSource() != null)
									   .filter(e -> ((Bullet)e).getSource().equals(getProgram().getShip()))
									   .collect(Collectors.toList());
		
		if (bullets.size() == 0)
			return null;
		
		Bullet closest = (Bullet)bullets.iterator().next();
		return closest;
	}


	
	@Override
	public Bullet evaluate() {
		return this.getEntity();
	}


	@Override
	public void setSubProgram() {
	}


	@Override
	public void setSubFunction() {
	}

	
}