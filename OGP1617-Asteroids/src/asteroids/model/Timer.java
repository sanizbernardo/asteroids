package asteroids.model;

public class Timer {
	
	public Timer(double time) {
		setTime(time);
	}
	
	private double time;

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}
	
	public void addTime(double time) {
		this.setTime(getTime() + time);
	}
	
}
