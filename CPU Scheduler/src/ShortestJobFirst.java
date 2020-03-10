import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShortestJobFirst {
	ArrayList<Process> process;
	ArrayList<Process> temp;
	private double currentTime = 0;
	public double contextSwitching = 0;
	public ShortestJobFirst(ArrayList<Process> p) {
		process = p;
		temp = new ArrayList<Process>();
		for (int i=0 ; i<p.size() ; i++) {
			temp.add(new Process(p.get(i)));
		}
	}
	
	public void sortArray() {
		Collections.sort(temp,new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				// TODO Auto-generated method stub
				if (o1.arrivalTime > currentTime) {
					return 1;
				}
				else if (o2.arrivalTime > currentTime) {
					return -1;
				}
				if (o1.burstTime < o2.burstTime) {
			           return -1;
			    } else if (o1.burstTime > o2.burstTime){
		           return 1;
		        } else {
		    	   return 0;
		    	   
		       }
			}
		});
	}
	public void process() {
		
		currentTime = process.get(0).arrivalTime;
		for(int i=1 ; i<process.size(); i++) {
			if (currentTime > process.get(i).arrivalTime)
				currentTime = process.get(i).arrivalTime;
		}
		
		for (int i=0 ; i<process.size() ; i++) {
			sortArray();
			
			for (int j=0 ; j<process.size() ; j++) {
				if (process.get(j).processName.equals(temp.get(0).processName)){
					if (process.get(j).arrivalTime > currentTime)
                        currentTime = process.get(j).arrivalTime;
					process.get(j).addTime(currentTime+contextSwitching);
					process.get(j).calculate();
					System.out.println(process.get(j).processName + " Start: " + process.get(j).timeFrames.get(0).start + " End: " +process.get(j).timeFrames.get(0).end + " " + process.get(j).turnArroundTime);
				}
			}
			currentTime += (temp.get(0).burstTime + contextSwitching);
			System.out.println(this.temp.get(0).processName);
			
			this.temp.remove(0);
		}
		System.out.println(process.size() + " " + temp.size());
	}
}
