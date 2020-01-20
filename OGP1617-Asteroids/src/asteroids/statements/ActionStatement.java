package asteroids.statements;

import asteroids.model.Function;
import asteroids.model.Program;

public abstract class ActionStatement extends Statement{

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
