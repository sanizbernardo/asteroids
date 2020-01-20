package asteroids.expressions;

import asteroids.part3.programs.SourceLocation;

public class NullExpression<T> extends Expression<Object> {
	
	public NullExpression(SourceLocation location) {
		this.setLocation(location);
	}
	
	@Override
	public Object evaluate() {
		return null;
	}

	@Override
	public void setSubProgram() {
	}

	@Override
	public void setSubFunction() {
	}
	
}