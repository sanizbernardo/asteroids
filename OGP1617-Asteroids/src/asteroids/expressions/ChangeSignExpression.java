package asteroids.expressions;

import asteroids.part3.programs.SourceLocation;
import asteroids.statements.BreakException;
import asteroids.statements.TimerException;

public class ChangeSignExpression<D> extends DoubleExpression<Double> {

	public ChangeSignExpression(Expression<D> expression, SourceLocation location) {
		this.setSubEx(expression);
		this.setLocation(location);
	}
	
	private Expression<D> subexpression;
	
	public Expression<D> getSubEx() {
		return this.subexpression;
	}
	
	public void setSubEx(Expression<D> expression) {
		this.subexpression = expression;
	}
	
	@Override
	public Double evaluate() throws BreakException, TimerException {
		if (canHaveSubExpression(getSubEx()))
			return -1*(Double)this.getSubEx().evaluate();
		else
			throw new IllegalArgumentException("Incorrect ChangeSignExpression");
	}

	@Override
	public void setSubProgram() {
		this.getSubEx().setProgram(getProgram());
	}

	@Override
	public void setSubFunction() {
		this.getSubEx().setFunction(this.getFunction());
	}
	
}
