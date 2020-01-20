package asteroids.expressions;

import asteroids.part3.programs.SourceLocation;
import asteroids.statements.BreakException;
import asteroids.statements.TimerException;

public class AdditionExpression<D> extends DoubleExpression<D> {
	
	public AdditionExpression(Expression<D> leftexpression, Expression<D> rightexpression, SourceLocation location) {
		this.setLeftEx(leftexpression);
		this.setRightEx(rightexpression);
		this.setLocation(location);
	}
	
	private Expression<D> leftexpression;
	
	public Expression<D> getLeftEx() {
		return this.leftexpression;
	}
	
	public void setLeftEx(Expression<D> expression) {
		this.leftexpression = expression;
	}
	
	private Expression<D> rightexpression;
	
	public Expression<D> getRightEx() {
		return this.rightexpression;
	}
	
	public void setRightEx(Expression<D> expression) {
		this.rightexpression = expression;
	}

	@Override
	public Double evaluate() throws BreakException, TimerException {
		if (! canHaveSubExpression(getRightEx()) || ! canHaveSubExpression(getLeftEx()))
			throw new IllegalArgumentException("Incorrect AdditionExpression");
		else
			return (Double) this.getLeftEx().evaluate() + (Double) this.getRightEx().evaluate();
	}

	@Override
	public void setSubProgram() {
		getLeftEx().setProgram(getProgram());
		getRightEx().setProgram(getProgram());
	}

	@Override
	public void setSubFunction() {
		getLeftEx().setFunction(this.getFunction());
		getRightEx().setFunction(this.getFunction());
	}
	
}
