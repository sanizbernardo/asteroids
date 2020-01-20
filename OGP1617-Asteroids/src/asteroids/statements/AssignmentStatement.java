package asteroids.statements;

import java.util.HashMap;

import asteroids.expressions.Expression;
import asteroids.model.Function;
import asteroids.model.Program;
import asteroids.part3.programs.SourceLocation;

public class AssignmentStatement extends Statement{
	
	public AssignmentStatement(String variableName, Expression<?> value, SourceLocation sourceLocation) {
		this.setVariable(variableName);
		this.setValue(value);
		this.setLocation(sourceLocation);
		this.setExecuted(false);
	}
	
	private String variable;
	
	public String getVariableName() {
		return this.variable;
	}
	
	public void setVariable(String variable) {
		this.variable = variable;
	}
	
	private Expression<?> value;
	
	public Expression<?> getValue() {
		return this.value;
	}
	
	public void setValue(Expression<?> value) {
		this.value = value;
	}
	
	public HashMap<String, Object> getTop() {
		return this.getProgram().getVariableData().peek();
	}
	
	public HashMap<String, Object> getBottm() {
		return this.getProgram().getVariableData().firstElement();
	}
	
	@Override
	public void evaluate() throws BreakException, TimerException {
		if (this.wasExecuted() && this.getFunction() == null)
			return;
		Object newvalue = getValue().evaluate();
		if (this.getFunction() != null) {
			Object oldvalue = getTop().get(getVariableName());
			if (oldvalue == null)
				getTop().put(getVariableName(), newvalue);
			else if (oldvalue.getClass() != newvalue.getClass())
				throw new IllegalArgumentException("Cannot change variable type");
			else
				getTop().put(getVariableName(), newvalue);
		}
		else if (! this.getProgram().getFunctions().containsKey(getVariableName())) {
			Object oldvalue = getBottm().get(getVariableName());
			if (oldvalue == null)
				getBottm().put(getVariableName(), newvalue);
			else if (oldvalue.getClass() != newvalue.getClass())
				throw new IllegalArgumentException("Cannot change variable type");
			else
				getBottm().put(getVariableName(), newvalue);
		}
		else
			throw new IllegalArgumentException("Name already used for Function");
		this.setExecuted(true);
	}
	
	
	
	
	@Override
	public void complete() {
		this.setExecuted(false);
	}

	@Override
	public void setSubProgram(Program program) {
		this.getValue().setProgram(getProgram());
	}

	@Override
	public void setSubFunction(Function function) {
		this.getValue().setFunction(function);
	}
	
	

}
