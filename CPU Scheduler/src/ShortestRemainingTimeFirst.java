import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShortestRemainingTimeFirst {
	ArrayList<Process> process;
	ArrayList<Process> temp;
	private double currentTime = 0;
	public double contextSwitching = 0;
	public ShortestRemainingTimeFirst (ArrayList<Process> p) {
		process = p;
		temp = new ArrayList<Process>();
		for (int i=0 ; i<p.size() ; i++) {
			temp.add(new Process(p.get(i)));
		}
	}
	// arrival then burst based on currentTime
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
		
		currentTime = process.get(0).arrivalTime + contextSwitching;
		for(int i=1 ; i<process.size(); i++) {
			if (currentTime > process.get(i).arrivalTime)
				currentTime = process.get(i).arrivalTime;
		}

		sortArray();
		Process check = temp.get(0);
		double start = currentTime;
		
		while (!temp.isEmpty()) {
			sortArray();
			if (temp.get(0).arrivalTime > currentTime){
				currentTime = temp.get(0).arrivalTime + contextSwitching;
            	start = currentTime;
            }
			if (temp.get(0).burstTime == 0 ) {
				for (int j=0 ; j<process.size() ; j++) {
					if (process.get(j).processName.equals(temp.get(0).processName)){
						process.get(j).addTime(start,currentTime);
						process.get(j).calculate();
						break;
					}
				}
				temp.remove(0);
				currentTime += contextSwitching;
				start = currentTime;
				if (!temp.isEmpty())
					check = temp.get(0);
				continue;
			}
			
			temp.get(0).burstTime--; 
			
			if (!check.processName.equals(temp.get(0).processName)) {
				for (int j=0 ; j<process.size() ; j++) {
					if (process.get(j).processName.equals(check.processName)){
						process.get(j).addTime(start,currentTime );
						process.get(j).calculate();
						break;
					}
				}
				check = temp.get(0);
				currentTime += contextSwitching;
				start = currentTime;
			}
			currentTime += (1);
		}
		
		for (int i=0 ; i<process.size() ; i++) {
			System.out.print(process.get(i).processName + ": ");
			for (int j = 0 ; j < process.get(i).timeFrames.size() ; j++)
				System.out.print(process.get(i).timeFrames.get(j).start + "," + process.get(i).timeFrames.get(j).end + " ");
			System.out.println(" ");
		}
		
	}
}
