package asteroids.statements;

import asteroids.expressions.DoubleExpression;
import asteroids.expressions.Expression;
import asteroids.expressions.GetterExpression;
import asteroids.model.Timer;
import asteroids.part3.programs.SourceLocation;

public class TurnStatement extends ActionStatement{

	public TurnStatement(Expression<?> angle, SourceLocation location) {
		this.setExecuted(false);
		this.setLocation(location);
		this.setAngle(angle);
	}
	
	private Expression<?> angle;
	
	public Expression<?> getAngle() {
		return this.angle;
	}
	
	public void setAngle(Expression<?> angle) {
		this.angle = angle;
	}
	
	public boolean canHaveAsAngle(Expression<?> ex) throws BreakException, TimerException {
		boolean correctexpression = (ex instanceof DoubleExpression || ex instanceof GetterExpression);
		boolean correctvalue = this.getProgram().getShip().isValidTurn((double)ex.evaluate());
		return correctexpression && correctvalue;
	}
	
	@Override
	public void evaluate() throws BreakException, TimerException {
		if (this.wasExecuted())
			return;
		if (this.getFunction() != null)
			throw new IllegalArgumentException("ActionStatement in function");
		if (! canHaveAsAngle(angle)) 
			throw new IllegalArgumentException("Incorrect TurnStatement");
		else {
			Timer timer = this.getProgram().getTimer();
			if (timer.getTime() < 0.2)
				throw new TimerException();
			this.getProgram().getShip().turn((double)this.getAngle().evaluate());
			timer.setTime(timer.getTime() - 0.2);
			this.setExecuted(true);
		}
	}

}