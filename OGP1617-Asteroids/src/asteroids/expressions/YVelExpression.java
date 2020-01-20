package asteroids.expressions;

import asteroids.model.Entity;
import asteroids.part3.programs.SourceLocation;
import asteroids.statements.BreakException;
import asteroids.statements.TimerException;

public class YVelExpression<D> extends GetterExpression<Double> {

	public YVelExpression(Expression<?> entity, SourceLocation location) {
		this.setEntity(entity);
		this.setLocation(location);
	}
	
	@Override
	public Double evaluate() throws BreakException, TimerException {
		if (canHaveSubExpression(getEntityEx()))
			return ((Entity)this.getEntityEx().evaluate()).getVel().getY();
		else
			throw new IllegalArgumentException("Incorrect GetterExpression");
	}
	
	@Override
	public void setSubProgram() {
		getEntityEx().setProgram(getProgram());
	}

	@Override
	public void setSubFunction() {
		getEntityEx().setFunction(this.getFunction());
	}

}