package asteroids.statements;

import asteroids.model.Function;
import asteroids.model.Program;
import asteroids.part3.programs.SourceLocation;

public class BreakStatement extends Statement {
	
	public BreakStatement(SourceLocation location) {
		this.setLocation(location);
	}

	private Statement superStatement;
	
	public Statement getSuperStatement(){
		return this.superStatement;
	}
	
	public void setSuperstatement(Statement statement){
		this.superStatement = statement;
	}
	
	
	@Override
	public void evaluate() throws BreakException {
		throw new BreakException();
	}

	@Override
	public void setSubProgram(Program program) {
	}

	@Override
	public void setSubFunction(Function function) {
	}
	
	@Override
	public void complete() {
		this.setExecuted(false);
	}

}
