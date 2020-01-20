package asteroids.expressions;

import asteroids.part3.programs.SourceLocation;

public class DirectionExpression<D> extends GetterExpression<Double> {
	
	public DirectionExpression(SourceLocation location) {
		this.setLocation(location);
	}
	
	@Override
	public Double evaluate() {
		return this.getProgram().getShip().getOrientation();
	}

	@Override
	public void setSubProgram() {
	}

	@Override
	public void setSubFunction() {
	}
}