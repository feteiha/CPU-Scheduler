import java.awt.Color;
import java.util.ArrayList;

/*
 * 
 * 
 * Process Name
 Process Color(Graphical Representation)
 Process Arrival Time
 Process Burst Time
 Process Priority Number*/

public class Process {
	public Color color;
	public String processName;
	public double arrivalTime, burstTime, waitingTime, turnArroundTime , quantum , AGFactor;
	public int priority;
	public class time {
		double start,end;
		public time (double start, double end) {
			this.start = start;
			this.end = end;
		}
	}
	public ArrayList<time> timeFrames;
	public Process(String processName, Color color, double arrivalTime, double burstTime, int priority) {
		this.processName = processName;
		this.color = color;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.priority = priority;
		timeFrames = new ArrayList<Process.time>();
	}
	public Process( Process p ) {
		this.processName = p.processName;
		this.color = p.color;
		this.arrivalTime = p.arrivalTime;
		this.burstTime = p.burstTime;
		this.priority = p.priority;
		this.AGFactor = p.AGFactor;
		this.quantum = p.quantum;
		timeFrames = new ArrayList<Process.time>();
	}
	
	public Process(String processName, Color color, double arrivalTime, double burstTime, int priority , double _quantum) {
		this.processName = processName;
		this.color = color;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.priority = priority;
		timeFrames = new ArrayList<Process.time>();
		this.quantum = _quantum;
		this.AGFactor = arrivalTime + burstTime + priority;
	}
	
	public void calcAGFactor() {
		AGFactor = arrivalTime + burstTime + priority;
	}
	
	public Process() {}
	
	public void addTime(double start, double end) {
		timeFrames.add(new time(start,end));
	}
	public void addTime(double start) {
		timeFrames.add(new time(start,start + this.burstTime));
	}
	
	public void calculate() {
		turnArroundTime = timeFrames.get(timeFrames.size()-1).end - arrivalTime;
		waitingTime = turnArroundTime - burstTime;
	}
}
