package asteroids.statements;

import asteroids.expressions.EqualityExpression;
import asteroids.expressions.Expression;
import asteroids.expressions.LessThanExpression;
import asteroids.expressions.NotExpression;
import asteroids.model.Function;
import asteroids.model.Program;
import asteroids.part3.programs.SourceLocation;

public class WhileStatement extends Statement {
	
	public WhileStatement(Expression<?> condition, Statement body, SourceLocation location) {
		this.condition = condition;
		this.body = body;
		this.setLocation(location);
	}
	
	private Expression<?> condition;
	
	private Statement body;
	
	public Expression<?> getCondition() {
		return this.condition;
	}
	
	public Statement getBody() {
		return this.body;
	}
	
	@Override
	public void evaluate() throws BreakException, TimerException {
		if(canHaveAsCondition(this.getCondition()))
			try {
			while ((boolean)this.getCondition().evaluate() == true) {
				getBody().evaluate();
				getBody().complete();
			}
			} catch (BreakException e) {}
		else
			throw new IllegalArgumentException("Condition in while statement is incorrect");
	}

	@Override
	public void setSubProgram(Program program) {
		this.getCondition().setProgram(program);
		this.getBody().setProgram(program);
	}

	@Override
	public void setSubFunction(Function function) {
		this.getCondition().setFunction(function);
		this.getBody().setFunction(function);
	}
	
	public boolean canHaveAsCondition(Expression<?> expression) {
		return (expression.getClass() == EqualityExpression.class) || (expression.getClass() == LessThanExpression.class) ||
				(expression.getClass() == NotExpression.class);
	}
	
	@Override
	public void complete() {
		this.getBody().complete();
	}

}
