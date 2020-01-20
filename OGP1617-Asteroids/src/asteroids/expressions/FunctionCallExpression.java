package asteroids.expressions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import asteroids.model.Function;
import asteroids.part3.programs.SourceLocation;
import asteroids.statements.BreakException;
import asteroids.statements.TimerException;

public class FunctionCallExpression<E> extends Expression<Object> {
	
	public FunctionCallExpression (String functionName, List<Expression<?>> actualArgs, SourceLocation location) {
		this.setFName(functionName);
		this.setArgs(actualArgs);
		this.setLocation(location);
	}
	
	private String functionName;
	
	public String getFName() {
		return this.functionName;
	}
	
	public void setFName(String functionName) {
		this.functionName = functionName;
	}
	
	private List<Expression<?>> actualArgs;
	
	public List<Expression<?>> getArgs() {
		return this.actualArgs;
	}
	
	public void setArgs(List<Expression<?>> actualArgs) {
		this.actualArgs = actualArgs;
	}
	
	@Override
	public Object evaluate() throws BreakException, TimerException {
		Function f = this.getProgram().getFunctions().get(functionName);
		if (f == null)
			throw new IllegalArgumentException("Incorrect FunctionCall");
		List<Object> values = new ArrayList<Object>();
		for (Expression<?> e : getArgs())
			values.add(e.evaluate());
		f.getParameterData().push(values);
		this.getProgram().getVariableData().push(new HashMap<String, Object>());
		Object value = f.run();
		this.getProgram().getVariableData().pop();
		f.getParameterData().pop();
		return value;
	}

	@Override
	public void setSubProgram() {
		for (Expression<?> e : this.getArgs())
			e.setProgram(getProgram());
	}

	@Override
	public void setSubFunction() {
		for (Expression<?> e : this.getArgs())
			e.setFunction(this.getFunction());
	}

}
