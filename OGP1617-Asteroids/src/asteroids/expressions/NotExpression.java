package asteroids.expressions;

import asteroids.part3.programs.SourceLocation;
import asteroids.statements.BreakException;
import asteroids.statements.TimerException;


public class NotExpression<B> extends Expression<Boolean> {
	
	public NotExpression(Expression<?> expression, SourceLocation location) {
		this.setSubEx(expression);
		this.setLocation(location);
		
	}

	private Expression<?> subexpression;
	
	public Expression<?> getSubEx() {
		return this.subexpression;
	}

	public void setSubEx(Expression<?> expression) {
		this.subexpression = expression;
	}
	
	public boolean canHaveSubExpression(Expression<?> expression) {
		return (expression.getClass() == EqualityExpression.class) || (expression.getClass() == LessThanExpression.class);
	}
	
	@Override
	public Boolean evaluate() throws BreakException, TimerException {
		if (canHaveSubExpression(this.getSubEx()))
			return ! (boolean)this.getSubEx().evaluate();
		else
			throw new IllegalArgumentException("Incorrect NotExpression");
	}

	@Override
	public void setSubProgram() {
		getSubEx().setProgram(getProgram());
	}

	@Override
	public void setSubFunction() {
		getSubEx().setFunction(this.getFunction());
	}

}