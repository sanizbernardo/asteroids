package asteroids.expressions;

import asteroids.model.Ship;
import asteroids.part3.programs.SourceLocation;

public class ThisExpression<E> extends Expression<Ship> implements EntityExpression<Ship> {
	
	public ThisExpression(SourceLocation location) {
		this.setLocation(location);
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

	@Override
	public Ship getEntity() {
		return this.getProgram().getShip();
	}
	
}