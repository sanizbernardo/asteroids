package asteroids.statements;

import asteroids.expressions.Expression;
import asteroids.model.Function;
import asteroids.model.Program;
import asteroids.part3.programs.SourceLocation;

public class PrintStatement extends Statement {
	
	public PrintStatement(Expression<?> expression, SourceLocation location) {
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
	
	@Override
	public void evaluate() throws BreakException, TimerException {
		if (this.wasExecuted() && this.getFunction() == null)
			return;
		if (this.getFunction() != null)
			throw new IllegalArgumentException("Print in function");
		Object o = this.getExpression().evaluate();
		if (o != null)
			System.out.println(o.toString());
		this.getProgram().getShip().getItems().add(o);
		this.setExecuted(true);
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
