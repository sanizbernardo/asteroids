package asteroids.statements;

import java.util.List;

import asteroids.model.Function;
import asteroids.model.Program;
import asteroids.part3.programs.SourceLocation;

public class BlockStatement extends Statement implements Returnable {
	
	public BlockStatement(List<Statement> statements, SourceLocation location) {
		this.statements = statements;
		this.setLocation(location);
	}
	
	private List<Statement> statements;
	
	public List<Statement> getStatements() {
		return this.statements;
	}

	@Override
	public void evaluate() throws BreakException, TimerException {
		for (Statement statement : statements)
			statement.evaluate();
	}

	@Override
	public void setSubProgram(Program program) {
		for (Statement s : this.getStatements())
			s.setProgram(program);		
	}

	@Override
	public void setSubFunction(Function function) {
		for (Statement s : this.getStatements())
			s.setFunction(function);
	}
	
	@Override
	public void complete() {
		for (Statement s : this.getStatements())
			s.complete();
	}

	@Override
	public Object returnValue() throws BreakException, TimerException {
		statements = this.getStatements();
		for (int i = 0; i < statements.size() - 1; i++)
			statements.get(i).evaluate();
		if (! (statements.get(statements.size() - 1) instanceof ReturnStatement))
			throw new IllegalArgumentException("Function does not end in return");
		else
			return ((ReturnStatement)statements.get(statements.size() - 1)).returnValue();
	}

}
