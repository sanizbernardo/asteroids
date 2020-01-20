package asteroids.statements;

import asteroids.expressions.Expression;
import asteroids.model.Function;
import asteroids.model.Program;
import asteroids.part3.programs.SourceLocation;

public class ReturnStatement extends Statement implements Returnable {
	
	public ReturnStatement(Expression<?> expression, SourceLocation location) {
		this.setExpression(expression);
		this.setLocation(location);
	}
	
	private Expression<?> expression;
	
	public Expression<?> getExpression() {
		return this.expression;
	}
	
	public void setExpression(Expression<?> expression) {
		this.expression = expression;
	}

	public Object returnValue() throws BreakException, TimerException {
		return this.getExpression().evaluate();
	}

	@Override
	public void evaluate() throws BreakException {
		throw new IllegalArgumentException("return outside fuction");
	}

	@Override
	public void setSubProgram(Program program) {
		this.getExpression().setProgram(program);
	}

	@Override
	public void setSubFunction(Function function) {
		this.getExpression().setFunction(function);
	}
	
	@Override
	public void complete() {
		this.setExecuted(false);
	}
	
}
