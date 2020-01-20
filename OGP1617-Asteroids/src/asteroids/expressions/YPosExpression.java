package asteroids.expressions;

import asteroids.model.Entity;
import asteroids.part3.programs.SourceLocation;
import asteroids.statements.BreakException;
import asteroids.statements.TimerException;

public class YPosExpression<D> extends GetterExpression<Double> {

	public YPosExpression(Expression<?> entity, SourceLocation location) {
		this.setEntity(entity);
		this.setLocation(location);
	}
	
	@Override
	public Double evaluate() throws BreakException, TimerException {
		if (canHaveSubExpression(getEntityEx()))
			return ((Entity)this.getEntityEx().evaluate()).getPos().getY();
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