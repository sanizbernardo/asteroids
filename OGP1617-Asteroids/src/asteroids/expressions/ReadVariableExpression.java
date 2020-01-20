package asteroids.expressions;

import java.util.HashMap;

import asteroids.part3.programs.SourceLocation;

public class ReadVariableExpression<E> extends Expression<Object> {
	
	public ReadVariableExpression(String variableName, SourceLocation sourceLocation) {
		this.setName(variableName);
		this.setLocation(sourceLocation);
	}
	
	private String variablename;
	
	public String getName() {
		return this.variablename;
	}
	
	public void setName(String name) {
		this.variablename = name;
	}
	
	public HashMap<String, Object> getTop() {
		return this.getProgram().getVariableData().peek();
	}
	
	public HashMap<String, Object> getBottm() {
		return this.getProgram().getVariableData().firstElement();
	}
	
	@Override
	public Object evaluate() {
		Object variable;
		if (this.getFunction() != null && getTop().containsKey(variablename))
			variable = getTop().get(variablename);
		else if (getBottm().containsKey(variablename))
			variable = getBottm().get(variablename);
		else
			throw new IllegalArgumentException("No value for this variable");
		if (variable == null)
			throw new IllegalArgumentException("Incorrect ReadVariable");
		return variable;
	}

	@Override
	public void setSubProgram() {
	}

	@Override
	public void setSubFunction() {
	}

}
