package asteroids.expressions;

import asteroids.statements.BreakException;
import asteroids.statements.TimerException;

public abstract class DoubleExpression<D> extends Expression<Double> {

	public boolean canHaveSubExpression(Expression<?> expression) throws BreakException, TimerException {
		return (expression instanceof DoubleExpression) || (expression instanceof GetterExpression) ||
				(expression instanceof FunctionCallExpression) || (expression instanceof ReadVariableExpression) ||
				(expression instanceof ReadParameterExpression);
	}
	
}