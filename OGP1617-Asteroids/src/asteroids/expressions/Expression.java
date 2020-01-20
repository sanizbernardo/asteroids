package asteroids.expressions;
import asteroids.model.*;


import asteroids.part3.programs.SourceLocation;
import asteroids.statements.BreakException;
import asteroids.statements.TimerException;
import be.kuleuven.cs.som.annotate.Basic;

public abstract class Expression<T> {
	
	private SourceLocation location;
	
	@Basic
	public SourceLocation getLocation() {
		return this.location;
	}
	
	public void setLocation(SourceLocation location) {
		this.location = location;
	}
	
	private Program program;

	public Program getProgram() {
		return this.program;
	}
	
	public void setProgram(Program program) {
		this.program = program;
		this.setSubProgram();
	}
	
	public abstract void setSubProgram();
	
	protected Function function;
	
	public Function getFunction() {
		return this.function;
	}
	
	public void setFunction(Function function) {
		this.function = function;
		this.setSubFunction();
	}
	
	public abstract void setSubFunction();
	
	public abstract T evaluate() throws BreakException, TimerException;
	
}