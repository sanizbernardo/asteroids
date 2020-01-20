package asteroids.model;

import java.util.List;
import java.util.Stack;

import asteroids.part3.programs.SourceLocation;
import asteroids.statements.*;

public class Function {

	public Function(String functionName, Statement body, SourceLocation sourceLocation) {
		this.name = functionName;
		this.body = body;
		this.getBody().setFunction(this);
	}
	
	private String name;
	
	private Statement body;
	
	public String getName() {
		return this.name;
	}
	
	public Statement getBody() {
		return this.body;
	}
	
	private Program program;

	public Program getProgram() {
		return this.program;
	}
	
	public void setProgram(Program program){
		this.program = program;
		this.getBody().setProgram(program);
	}
	
	public Stack<List<Object>> parameterdata = new Stack<List<Object>>();
	
	public Stack<List<Object>> getParameterData() {
		return parameterdata;
	}
	
	public Object run() throws BreakException, TimerException {
		if (! (this.getBody() instanceof Returnable))
			throw new IllegalArgumentException("No Returnable in function");
		return ((Returnable)this.getBody()).returnValue();
	}
	
}
