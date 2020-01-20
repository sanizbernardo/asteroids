package asteroids.expressions;

import asteroids.part3.programs.SourceLocation;

public class DoubleLiteralExpression extends DoubleExpression<Double> {
	
	public DoubleLiteralExpression(double value, SourceLocation location) {
		this.setValue(value);
		this.setLocation(location);
	}
	
	private double value;
	
	public double getValue() {
		return this.value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public Double evaluate() {
		return this.getValue();
	}

	@Override
	public void setSubProgram() {
	}

	@Override
	public void setSubFunction() {
	}
	
}
