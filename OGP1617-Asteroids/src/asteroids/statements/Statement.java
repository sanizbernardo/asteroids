package asteroids.statements;

import asteroids.model.Function;
import asteroids.model.Program;
import asteroids.part3.programs.SourceLocation;
import be.kuleuven.cs.som.annotate.Basic;

public abstract class Statement {
	
	private SourceLocation location;
	
	@Basic
	public SourceLocation getLocation() {
		return this.location;
	}
	
	public void setLocation(SourceLocation location) {
		this.location = location;
	}
	
	private boolean executed;
	
	public boolean wasExecuted() {
		return executed;
	}
	
	public void setExecuted(boolean b) {
		this.executed = b;
	}
	
	public abstract void complete();

	public abstract void evaluate() throws BreakException, TimerException ;
	
	protected Program program;

	public Program getProgram() {
		return this.program;
	}
	
	public void setProgram(Program program) {
		this.program = program;
		this.setSubProgram(program);
	}
	
	public abstract void setSubProgram(Program program);
	
	protected Function function;
	
	public Function getFunction() {
		return this.function;
	}
	
	public void setFunction(Function function) {
		this.function = function;
		this.setSubFunction(function);
	}
	
	public abstract void setSubFunction(Function function);
	
}
