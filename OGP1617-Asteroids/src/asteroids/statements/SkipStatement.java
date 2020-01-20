package asteroids.statements;

import asteroids.model.Timer;
import asteroids.part3.programs.SourceLocation;

public class SkipStatement extends ActionStatement{

	public SkipStatement(SourceLocation location) {
		this.setExecuted(false);
		this.setLocation(location);
	}
	
	@Override
	public void evaluate() throws TimerException {
		if (this.wasExecuted())
			return;
		if (this.getFunction() != null)
			throw new IllegalArgumentException("ActionStatement in function");
		Timer timer = this.getProgram().getTimer();
		if (timer.getTime() < 0.2)
			throw new TimerException();
		timer.setTime(timer.getTime() - 0.2);
		this.setExecuted(true);
	}
	
}
