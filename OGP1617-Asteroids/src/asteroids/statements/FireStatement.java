package asteroids.statements;

import asteroids.model.Timer;
import asteroids.part3.programs.SourceLocation;

public class FireStatement extends ActionStatement {

	public FireStatement(SourceLocation location) {
		this.setExecuted(false);
		this.setLocation(location);
	}
	
	@Override
	public void evaluate() throws TimerException {
		if (this.wasExecuted())
			return;
		if (this.getFunction() != null)
			throw new IllegalArgumentException("ActionStatement in function");
		else {
			Timer timer = this.getProgram().getTimer();
			if (timer.getTime() < 0.2)
				throw new TimerException();
			this.getProgram().getShip().fireBullet();
			timer.setTime(timer.getTime() - 0.2);
			this.setExecuted(true);
		}
	}

}
