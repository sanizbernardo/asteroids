package asteroids.model;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import asteroids.statements.BreakException;
import asteroids.statements.Statement;
import asteroids.statements.TimerException;
 
public class Program { 
 
	public Program(List<Function> functions, Statement main ) {
		if (main == null)
			throw new NullPointerException("main null");
		this.main = main;
		this.getMain().setProgram(this);
		for (Function function : functions)
			function.setProgram(this);
		this.functions = this.functionMap(functions);
		this.setTimer(new Timer(0));
	}
	
	private Map<String, Function> functions;
	
	public Map<String, Function> getFunctions() {
		return this.functions;
	}
	
	public Map<String, Function> functionMap(List<Function> functions) {
		Map<String, Function> funmap = new HashMap<String, Function>();
		for (Function f : functions)
			funmap.put(f.getName(), f);
		return funmap;
	}
	
	public Stack<HashMap<String, Object>> variabledata =  new Stack<HashMap<String, Object>>();
	
	public Stack<HashMap<String, Object>> getVariableData() {
		return variabledata;
	}

	private Statement main;
	
	public Statement getMain() {
		return this.main;
	}
	
	private Ship ship;

	public Ship getShip() {
		return this.ship;
	}
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	
	private Timer timer;

	public Timer getTimer() {
		return this.timer;
	}
	
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	public void run() throws BreakException, TimerException {
		this.getVariableData().push(new HashMap<String, Object>());
		main.evaluate();
	}
	
}