package asteroids.statements;

import asteroids.expressions.Expression;
import asteroids.model.Function;
import asteroids.model.Program;
import asteroids.part3.programs.SourceLocation;

public class IfStatement extends Statement implements Returnable {
	
	public IfStatement(Expression<?> condition, Statement statement1, Statement statement2, SourceLocation location) {
		this.setCondition(condition);
		this.setFirstStatement(statement1);
		this.setSecondStatement(statement2);
		this.setLocation(location);
		
	}
	
	private Expression<?> condition;
	
	public Expression<?> getCondition() {
		return this.condition;
	}
	
	public void setCondition(Expression<?> condition) {
		this.condition = condition;
	}
	
	private Statement firststatement;
	
	
	public Statement getStatement1() {
		return this.firststatement;
	}
	
	public void setFirstStatement(Statement statement) {
		this.firststatement = statement;
	}
	
	private Statement secondstatement;
	
	public Statement getStatement2() {
		return this.secondstatement;
	}
	
	public void setSecondStatement(Statement statement) {
		this.secondstatement = statement;
	}
	
	
	

	@Override
	public void evaluate() throws BreakException, TimerException {
		if (! (this.getCondition().evaluate() instanceof Boolean))
			throw new IllegalArgumentException("Non-boolean condition");
		if ((boolean)this.getCondition().evaluate() == true)
			this.getStatement1().evaluate();
		else
			if (getStatement2() != null)
				this.getStatement2().evaluate();
	}

	@Override
	public void setSubProgram(Program program) {
		this.getCondition().setProgram(program);
		this.getStatement1().setProgram(program);
		if (getStatement2() != null)
			this.getStatement2().setProgram(program);
	}

	@Override
	public void setSubFunction(Function function) {
		this.getCondition().setFunction(function);
		this.getStatement1().setFunction(function);
		if (getStatement2() != null)
			this.getStatement2().setFunction(function);
	}
	
	@Override
	public void complete() {
		this.getStatement1().complete();
		if (getStatement2() != null)
			this.getStatement2().complete();
	}

	@Override
	public Object returnValue() throws BreakException, TimerException {
		if ((boolean)this.getCondition().evaluate() == true) {
			if (! (this.getStatement1() instanceof Returnable))
				throw new IllegalArgumentException("No Returnable in function");
			return ((Returnable)this.getStatement1()).returnValue();
			}
		else {
			if (! (this.getStatement2() instanceof Returnable))
				throw new IllegalArgumentException("No Returnable in function");
			return ((Returnable)this.getStatement2()).returnValue();
			}
	}

}
