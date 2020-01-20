package asteroids.expressions;

public abstract class GetterExpression<D> extends Expression<Double> {
	
	private Expression<?> entityex;
	
	public Expression<?> getEntityEx() {
		return this.entityex;
	}
	
	public void setEntity(Expression<?> entityexpression) {
		this.entityex = entityexpression;
	}
	
	public boolean canHaveSubExpression(Expression<?> expression) {
		return expression instanceof EntityExpression;
	}
	
}