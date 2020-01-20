package asteroids.expressions;

import asteroids.part3.programs.SourceLocation;
import asteroids.statements.BreakException;
import asteroids.statements.TimerException;

public class EqualityExpression<B> extends Expression<Boolean> {
	
	public EqualityExpression(Expression<?> leftexpression, Expression<?> rightexpression, SourceLocation location) {
		this.setLeftEx(leftexpression);
		this.setRightEx(rightexpression);
		this.setLocation(location);
	}
	
	private Expression<?> leftexpression;
	
	private Expression<?> rightexpression;

	public Expression<?> getLeftEx() {
		return this.leftexpression;
	}

	public void setLeftEx(Expression<?> expression) {
		this.leftexpression = expression;
	}

	public Expression<?> getRightEx() {
		return this.rightexpression;
	}
	
	public void setRightEx(Expression<?> expression) {
		this.rightexpression = expression;
	}

	@Override
	public Boolean evaluate() throws BreakException, TimerException {
		return this.getLeftEx().evaluate() == this.getRightEx().evaluate();
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
