package asteroids.expressions;

import asteroids.part3.programs.SourceLocation;
import asteroids.statements.BreakException;
import asteroids.statements.TimerException;

public class ReadParameterExpression<E> extends Expression<Object> {
	
	public ReadParameterExpression(String parameterName, SourceLocation sourceLocation) {
		this.setName(parameterName);
		this.setLocation(sourceLocation);
	}
	
	private String parametername;
	
	public String getName() {
		return this.parametername;
	}
	
	public void setName(String name) {
		this.parametername = name;
	}

	@Override
	public Object evaluate() throws BreakException, TimerException {
		if (this.getFunction() == null)
			throw new IllegalArgumentException("Read parameter outside function");
		int pos = Integer.valueOf(this.getName().replace("$", ""));
		try {
		return this.getFunction().getParameterData().peek().get(pos - 1);
		} catch (IndexOutOfBoundsException i) {
			throw new IllegalArgumentException("Not enough arguments!");
		}
	}

	@Override
	public void setSubProgram() {
	}

	@Override
	public void setSubFunction() {
	}

}
